package com.packt.publishing.wf.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents
import com.packt.publishing.wf.api.WFService

class WFLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new WFApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new WFApplication(context) with LagomDevModeComponents
}

abstract class WFApplication(context: LagomApplicationContext) extends WFComponents(context)
  with LagomKafkaComponents {

}

abstract class WFComponents(context: LagomApplicationContext) extends LagomApplication(context)
  with CassandraPersistenceComponents
  with AhcWSComponents {

  override lazy val lagomServer = LagomServer.forServices(
    bindService[WFService].to(wire[WFServiceImpl])
  )

  override lazy val jsonSerializerRegistry = WFSerializerRegistry

  persistentEntityRegistry.register(wire[WFEntity])
}