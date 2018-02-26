import views.html.foundationFieldConstructor
import views.html.helper.FieldConstructor

package object controllers {
  implicit def fieldConstructor: FieldConstructor = FieldConstructor(foundationFieldConstructor.apply)
}
