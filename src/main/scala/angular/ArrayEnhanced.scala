package angular

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

@JSExportTopLevel("ArrayEnhanced")
@JSExportAll
final class ArrayEnhanced {

  /** Returns the js array with distinct elements. */
  def distinct[A](elements: js.Array[A]): js.Array[A] = elements.distinct

  /** Returns the js array with elements distinct under the function `f`. */
  def distinctBy[A, B](elements: js.Array[A],
                       f: js.Function1[A, B]): js.Array[A] =
    elements.distinctBy(f)

}
