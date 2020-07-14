package morescala

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

@JSExportTopLevel("User")
@JSExportAll
final case class User(id: String,
                      name: String,
                      dateOrBirth: js.UndefOr[js.Date],
                      dateOfRegistration: js.Date) {

  def maybeDateOfBirth: Option[js.Date] = dateOrBirth.toOption

}
