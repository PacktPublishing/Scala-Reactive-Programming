package com.packt.publishing.wf.impl

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

sealed trait WFEvent extends AggregateEvent[WFEvent] {
  override def aggregateTag: AggregateEventTagger[WFEvent] = {
    WFEventTag.instance
  }
}

case class WFChanged(city:String, temperature: String) extends WFEvent

object WFChanged {
  implicit val format: Format[WFChanged] = Json.format[WFChanged]
}
