package withzio

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("TaskCancelledError")
final class TaskCancelledError extends js.Error("Cancelled by user")
