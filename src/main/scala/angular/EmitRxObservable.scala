package angular

import typings.rxjs.internalObservableMod.Observable
import typings.rxjs.mod.BehaviorSubject

import scala.scalajs.js.timers.setInterval
import scala.concurrent.duration._
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

@JSExportTopLevel("EmitRxObservable")
@JSExportAll
final class EmitRxObservable {

  private val subject = new BehaviorSubject[Int](0)

  /** Returns an observable emitting all natural numbers, starting from 0, and updating every second. */
  @inline def naturalNumbers: Observable[Int] = subject

  setInterval(1.second) {
    subject.next(subject.getValue() + 1)
  }

}
