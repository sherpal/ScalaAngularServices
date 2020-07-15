package withzio

import typings.rxjs.internalObservableMod.Observable
import typings.rxjs.mod.{AsyncSubject, BehaviorSubject, Subject}
import typings.rxjs.operatorsMod.{map, scan}
import zio._
import zio.clock.Clock
import zio.duration._
import zio.random.Random

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.JSConverters._

@JSExportTopLevel("ZIOService")
final class ZIOService {

  /**
    * Transform an Rx Observable emitting once into a ZIO effect.
    * @param obs observable to wait for completion
    * @tparam U return type of the observable
    * @return ZIO effect representing the effect of the observable
    */
  def zioFromRxObservable[U](obs: Observable[U]): ZIO[Any, js.Error, U] =
    ZIO.effectAsync { callback =>
      obs.subscribe(
        (u: U) => callback(UIO(u)), // observable succeeds with a U
        (err: js.Any) => // observable fails, probably with a js.Error but we can't know for sure
          callback(err match {
            case err: js.Error => ZIO.fail(err)
            case _             => ZIO.fail(new js.Error(err.toString))
          })
      )
    }

  /** Will retry the given number of times with jittered exponential back-off */
  def retryPolicy(
    numberOfRetries: Int
  ): Schedule[Clock with Random, Any, (Duration, Int)] =
    Schedule.exponential(1.second).jittered && Schedule.recurs(numberOfRetries)

  /**
    * This is the pure ZIO version for `foreachParN` below.
    */
  def execution[T, U](parallelism: Int, retries: Int)(ts: Iterable[T])(
    program: T => ZIO[Any, js.Error, U],
    nextProgress: ZIO[Any, Nothing, Unit],
    complete: List[U] => ZIO[Any, Nothing, Unit],
    fail: js.Error => ZIO[Any, Nothing, Unit]
  ) = {
    val policy = retryPolicy(retries)
    ZIO
      .foreachParN(parallelism)(ts)(program(_).retry(policy) <* nextProgress)
      .flatMap(complete)
      .catchAll(fail)
  }

  /**
    * Execute the asynchronous `program` for each element of the array `ts`.
    * Programs are executed concurrently, with a maximum of `parallelism` concurrent programs.
    *
    * If a program fails, it is retried twice with jittered exponential backoff.
    *
    * @return an instance of [[CompletionState]] where
    *         - `progress` member is an observable emitting completion percentages
    *         - `result` is an observable emitting once the results of the programs in an array, or
    *           fails with an error in the event of one of the events fail
    *         - `cancel` is a callable for cancelling the execution, failing the `result` observable with
    *           a [[TaskCancelledError]]
    */
  @JSExport
  def foreachParN[T, U](ts: js.Array[T],
                        program: js.Function1[T, Observable[U]],
                        retries: Int = 2,
                        parallelism: Int = 5): CompletionState[U] = {
    val out = new AsyncSubject[js.Array[U]] // observable for final output
    val progressOut = new Subject[Int] // subject to receive progress pings

    def closeWithError(err: js.Error): Unit = { // closing output with error
      if (!out.closed) {
        out.error(err)
        out.complete()
      }
    }

    val zioProgram = execution(parallelism, retries)(ts)(
      t => zioFromRxObservable(program(t)), // lifting Rx observable to ZIO
      ZIO.effectTotal(progressOut.next(1)), // pinging the progressOut subject
      (us: List[U]) => // succeeding and completing the out observable
        ZIO.effectTotal {
          out.next(us.toJSArray)
          out.complete()
      },
      err =>
        ZIO.effectTotal { closeWithError(err) } // failing and completing the out observable
    )

    // actually running the program
    val future = zio.Runtime.default.unsafeRunToFuture(
      zioProgram.provideLayer(clock.Clock.live ++ random.Random.live)
    )

    // creating the function to cancel
    val cancel = () => {
      Future { future.cancel() }
      closeWithError(new TaskCancelledError)
    }

    // folding the progressOut observable to get percentage
    val tsCount = ts.length.toDouble
    val progressOutEvents = progressOut.pipe(
      scan((acc: Int, next: Int, _: Double) => acc + next, 0),
      map[Int, Double] { (completedCount: Int, _: Double) =>
        completedCount * 100 / tsCount
      }
    )
    CompletionState(progressOutEvents, out, cancel)
  }

}
