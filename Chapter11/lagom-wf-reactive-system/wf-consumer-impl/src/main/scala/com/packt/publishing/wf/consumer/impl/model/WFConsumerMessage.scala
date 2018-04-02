package com.packt.publishing.wf.consumer.impl.model

import play.api.libs.json.{Format, Json}

case class WeatherForcasting(city:String, temperature:String)

object WeatherForcasting {
  implicit val format: Format[WeatherForcasting] = Json.format[WeatherForcasting]
}
