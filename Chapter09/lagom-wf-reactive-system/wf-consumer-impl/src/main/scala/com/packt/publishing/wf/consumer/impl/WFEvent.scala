package com.packt.publishing.wf.consumer.impl

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag}
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting
import play.api.libs.json.Json

object WFEventTag {
  val INSTANCE = AggregateEventTag[WFEvent]
}

sealed trait WFEvent extends AggregateEvent[WFEvent] {
  override def aggregateTag = WFEventTag.INSTANCE
}

case class WFSaved(wf: WeatherForcasting) extends WFEvent

object WFSaved {
  implicit val formatter = Json.format[WFSaved]
}
