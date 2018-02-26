package com.packt.publishing.wf.impl

import play.api.libs.json.{Format, Json}

case class WFState(city:String, temperature: String, timestamp: String)

object WFState {
  implicit val format: Format[WFState] = Json.format[WFState]
}
