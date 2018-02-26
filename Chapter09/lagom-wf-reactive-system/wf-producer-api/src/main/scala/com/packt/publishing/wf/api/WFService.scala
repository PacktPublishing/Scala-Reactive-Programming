package com.packt.publishing.wf.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.packt.publishing.wf.api.model.WFMessage

trait WFService extends Service{

  def wfTemperature(city: String, temperature:String): ServiceCall[WFMessage, Done]

  override final def descriptor: Descriptor = {
    import Service._
    named("wfproducer").withCalls(
      pathCall("/wf/:city/:temperature", wfTemperature _)
    ).withTopics(
          topic(WFService.TOPIC_NAME, wfTopic)
     ).withAutoAcl(true)
  }

  def wfTopic(): Topic[WFMessage]
}

object WFService  {
  val TOPIC_NAME = "wfTemparature"
}

