package com.packt.publishing.lagomscalahelloservicestream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.packt.publishing.lagomscalahelloservicestream.api.LagomscalahelloserviceStreamService
import com.packt.publishing.lagomscalahelloservice.api.LagomscalahelloserviceService

import scala.concurrent.Future

/**
  * Implementation of the LagomscalahelloserviceStreamService.
  */
class LagomscalahelloserviceStreamServiceImpl(lagomscalahelloserviceService: LagomscalahelloserviceService) extends LagomscalahelloserviceStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(lagomscalahelloserviceService.hello(_).invoke()))
  }
}
