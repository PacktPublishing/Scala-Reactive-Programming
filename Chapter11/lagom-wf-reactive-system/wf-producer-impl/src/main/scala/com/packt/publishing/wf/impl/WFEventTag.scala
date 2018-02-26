package com.packt.publishing.wf.impl

import com.lightbend.lagom.scaladsl.persistence.AggregateEventTag

object WFEventTag {
  val instance: AggregateEventTag[WFEvent] = AggregateEventTag[WFEvent]()
}
