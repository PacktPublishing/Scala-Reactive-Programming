package com.packt.publishing.wf.api.model

import play.api.libs.json.{Format, Json}

case class WFMessage(city: String, temperature: String)

object WFMessage {
  implicit val format: Format[WFMessage] = Json.format[WFMessage]
}
