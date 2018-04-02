package com.packt.publishing.lagomscalahelloservice.impl

import com.packt.publishing.lagomscalahelloservice.api
import com.packt.publishing.lagomscalahelloservice.api.{LagomscalahelloserviceService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}

/**
  * Implementation of the LagomscalahelloserviceService.
  */
class LagomscalahelloserviceServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends LagomscalahelloserviceService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the lagom-scala-hello-service entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LagomscalahelloserviceEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the lagom-scala-hello-service entity for the given ID.
    val ref = persistentEntityRegistry.refFor[LagomscalahelloserviceEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }


  override def greetingsTopic(): Topic[api.GreetingMessageChanged] =
    TopicProducer.singleStreamWithOffset {
      fromOffset =>
        persistentEntityRegistry.eventStream(LagomscalahelloserviceEvent.Tag, fromOffset)
          .map(ev => (convertEvent(ev), ev.offset))
    }

  private def convertEvent(helloEvent: EventStreamElement[LagomscalahelloserviceEvent]): api.GreetingMessageChanged = {
    helloEvent.event match {
      case GreetingMessageChanged(msg) => api.GreetingMessageChanged(helloEvent.entityId, msg)
    }
  }
}
