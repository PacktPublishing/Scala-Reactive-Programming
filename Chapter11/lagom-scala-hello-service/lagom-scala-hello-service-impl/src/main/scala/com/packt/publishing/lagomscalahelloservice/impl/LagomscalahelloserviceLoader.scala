package com.packt.publishing.lagomscalahelloservice.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.packt.publishing.lagomscalahelloservice.api.LagomscalahelloserviceService
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.softwaremill.macwire._
import com.typesafe.conductr.bundlelib.lagom.scaladsl.ConductRApplicationComponents

class LagomscalahelloserviceLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new LagomscalahelloserviceApplication(context) with ConductRApplicationComponents

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new LagomscalahelloserviceApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[LagomscalahelloserviceService])
}

abstract class LagomscalahelloserviceApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[LagomscalahelloserviceService](wire[LagomscalahelloserviceServiceImpl])

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = LagomscalahelloserviceSerializerRegistry

  // Register the lagom-scala-hello-service persistent entity
  persistentEntityRegistry.register(wire[LagomscalahelloserviceEntity])
}
