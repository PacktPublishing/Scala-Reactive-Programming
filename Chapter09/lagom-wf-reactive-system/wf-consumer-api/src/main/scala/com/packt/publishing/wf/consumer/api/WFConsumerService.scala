package com.packt.publishing.wf.consumer.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting

trait WFConsumerService extends Service {

  override def descriptor: Descriptor = {
    import Service._

    named("wfconsumer").withCalls(
      restCall(Method.GET, "/api/wf", findTopTenWFData _),
      restCall(Method.GET, "/api/wf/one", findOneWFData _),
      restCall(Method.GET, "/api/wflatest", latestWF _)
    ).withAutoAcl(true)
  }

  def findTopTenWFData(): ServiceCall[NotUsed, Seq[WeatherForcasting]]

  def findOneWFData(): ServiceCall[NotUsed, WeatherForcasting]

  def latestWF(): ServiceCall[NotUsed, WeatherForcasting]

}


