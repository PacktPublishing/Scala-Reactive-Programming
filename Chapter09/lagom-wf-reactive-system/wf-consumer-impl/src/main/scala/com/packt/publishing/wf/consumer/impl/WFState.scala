package com.packt.publishing.wf.consumer.impl

import play.api.libs.json.Json

case class WFState(city:String, temperature:String , timeStamp: String)

object WFState {
  implicit val formatter = Json.format[WFState]
}
