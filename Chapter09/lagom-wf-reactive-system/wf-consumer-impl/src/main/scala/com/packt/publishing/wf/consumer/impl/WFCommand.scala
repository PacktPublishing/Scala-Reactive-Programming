package com.packt.publishing.wf.consumer.impl

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.Json
import akka.Done
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import scala.collection.immutable.Seq

sealed trait WFCommand [T] extends ReplyType[T]

case class SaveNewWF(city:String, temperature: String) extends WFCommand[Done]

object SaveNewWF {
  implicit val formatter = Json.format[SaveNewWF]
}

object WFConsumerSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[SaveNewWF],
    JsonSerializer[WFSaved],
    JsonSerializer[WFState]
  )
}
