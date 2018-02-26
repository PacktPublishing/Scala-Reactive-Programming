package com.packt.publishing.wf.impl

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import com.packt.publishing.wf.api.model.WFMessage
import com.packt.publishing.wf.api.WFService

class WFServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends WFService {

  override def wfTemperature(city: String, temperature:String): ServiceCall[WFMessage, Done] = ServiceCall { request =>
    val ref = persistentEntityRegistry.refFor[WFEntity](city)
    ref.ask(UseWFMessage(request.city, request.temperature))
  }

  override def wfTopic(): Topic[WFMessage] = {
    TopicProducer.singleStreamWithOffset {
      offset =>
        persistentEntityRegistry.eventStream(WFEventTag.instance, offset)
          .map(ev => (convertEvent(ev), offset))
    }
  }

  private def convertEvent(wfEvent: EventStreamElement[WFEvent]): WFMessage = {
    wfEvent.event match {
      case WFChanged(city, temperature) => WFMessage(city, temperature)
    }
  }

}