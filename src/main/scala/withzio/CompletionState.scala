package withzio

import typings.rxjs.internalObservableMod.Observable

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportStatic}

@JSExportAll
final case class CompletionState[U](progress: Observable[Double],
                                    result: Observable[js.Array[U]],
                                    cancel: js.Function0[Unit])
