package com.packt.publishing.wf.consumer.impl

import akka.stream.scaladsl.Flow
import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.packt.publishing.wf.api.WFService
import com.packt.publishing.wf.api.model.WFMessage
import com.packt.publishing.wf.consumer.api.WFConsumerService
import com.packt.publishing.wf.consumer.impl.repositories.WFRepository
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting

class WFConsumerServiceImpl(registry: PersistentEntityRegistry, wfService: WFService, wfRepository: WFRepository)
  extends WFConsumerService {

  wfService.wfTopic
    .subscribe
    .atLeastOnce(
      Flow[WFMessage].map { wf =>
        putWFMessage(wf)
        Done
      }
    )

  var lastObservedMessage: WeatherForcasting = _

  private def putWFMessage(wfMessage: WFMessage) = {
    entityRef(wfMessage.city.toString, wfMessage.temperature.toString).ask(SaveNewWF(wfMessage.city, wfMessage.temperature))
    lastObservedMessage = WeatherForcasting(wfMessage.city,wfMessage.temperature)
  }

  override def findTopTenWFData(): ServiceCall[NotUsed, Seq[WeatherForcasting]] = {
    ServiceCall {
      req => wfRepository.fetchWFData(10)
    }
  }

  override def findOneWFData(): ServiceCall[NotUsed, WeatherForcasting] = {
    ServiceCall {
      req => wfRepository.fetchOneWFData
    }
  }

  override def latestWF(): ServiceCall[NotUsed, WeatherForcasting] = {
    ServiceCall {
      req => scala.concurrent.Future.successful(lastObservedMessage)
    }
  }

  private def entityRef(city: String, temperature:String) = registry.refFor[WFEntity](city)
}
