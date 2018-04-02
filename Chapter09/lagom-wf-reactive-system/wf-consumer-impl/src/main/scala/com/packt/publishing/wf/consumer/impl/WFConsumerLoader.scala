package com.packt.publishing.wf.consumer.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents
import com.packt.publishing.wf.api.WFService
import com.packt.publishing.wf.consumer.api.WFConsumerService
import com.packt.publishing.wf.consumer.impl.repositories.WFRepository

class WFConsumerLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new WFConsumerApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new WFConsumerApplication(context) with LagomDevModeComponents
}

abstract class WFConsumerApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with LagomKafkaComponents
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[WFConsumerService].to(wire[WFConsumerServiceImpl])
  )

  //Bind the WFService client
  lazy val wfService = serviceClient.implement[WFService]

  lazy val messageRepository = wire[WFRepository]

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = WFConsumerSerializerRegistry

  // Register the Message persistent entity
   persistentEntityRegistry.register(wire[WFEntity])

  readSide.register(wire[WFEventProcessor])
}

