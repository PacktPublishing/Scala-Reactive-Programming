package com.packt.publishing.lagomscalahelloservicestream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.packt.publishing.lagomscalahelloservicestream.api.LagomscalahelloserviceStreamService
import com.packt.publishing.lagomscalahelloservice.api.LagomscalahelloserviceService
import com.softwaremill.macwire._
import com.typesafe.conductr.bundlelib.lagom.scaladsl.ConductRApplicationComponents

class LagomscalahelloserviceStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagomscalahelloserviceStreamApplication(context) with ConductRApplicationComponents

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagomscalahelloserviceStreamApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[LagomscalahelloserviceStreamService])
}

abstract class LagomscalahelloserviceStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LagomscalahelloserviceStreamService](wire[LagomscalahelloserviceStreamServiceImpl])

  // Bind the LagomscalahelloserviceService client
  lazy val lagomscalahelloserviceService = serviceClient.implement[LagomscalahelloserviceService]
}
