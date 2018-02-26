package com.packt.publishing.wf.impl

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import play.api.libs.json.{Format, Json}
import scala.collection.immutable.Seq

sealed trait WFCommand[R] extends ReplyType[R]

case class UseWFMessage(city: String, temperature: String) extends WFCommand[Done]

object UseWFMessage {
  implicit val format: Format[UseWFMessage] = Json.format[UseWFMessage]
}

case class WF(city: String, temperature: String) extends WFCommand[String]

object WF {
  implicit val format: Format[WF] = Json.format[WF]
}

object WFSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[UseWFMessage],
    JsonSerializer[WF],
    JsonSerializer[WFChanged],
    JsonSerializer[WFState]
  )
}
