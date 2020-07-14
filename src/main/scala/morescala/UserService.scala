package morescala

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}
import scala.scalajs.js.JSConverters._

@JSExportTopLevel("UserService")
@JSExportAll
final class UserService {

  /**
    * Returns a list of couples of the form (year, number of users born that year)
    * We ignore users for which we don't know the date of birth
    */
  def usersByDateOfBirth(users: js.Array[User]): js.Array[js.Tuple2[Int, Int]] =
    users
      .map(_.maybeDateOfBirth) // mapping to the date of birth
      .collect { // taking only those that are known, and we map to the year
        case Some(date) => date.getFullYear.toInt
      }
      .groupMapReduce(identity)(_ => 1)(_ + _) // counting how many by year
      .toJSArray // going to js world
      .map(js.Tuple2.fromScalaTuple2) // ending journey through js world

}
