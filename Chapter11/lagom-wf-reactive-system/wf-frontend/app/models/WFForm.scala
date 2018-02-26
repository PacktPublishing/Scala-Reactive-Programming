package models

import play.api.data.Form
import play.api.data.Forms._

case class WFForm(city: String, temperature: String)

object WFForm {
  val wfForm = Form(mapping(
    "city" -> nonEmptyText,
    "temperature" -> nonEmptyText
  )(WFForm.apply)(WFForm.unapply))
}