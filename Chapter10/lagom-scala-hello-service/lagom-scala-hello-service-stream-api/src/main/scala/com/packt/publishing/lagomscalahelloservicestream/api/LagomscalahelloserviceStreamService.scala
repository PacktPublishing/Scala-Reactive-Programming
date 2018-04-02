package com.packt.publishing.lagomscalahelloservicestream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The lagom-scala-hello-service stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and
  * consume the LagomscalahelloserviceStream service.
  */
trait LagomscalahelloserviceStreamService extends Service {

  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  override final def descriptor = {
    import Service._

    named("lagom-scala-hello-service-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }
}

