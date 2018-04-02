package com.packt.publishing.cassandra.app

import akka.actor.ActorSystem
import akka.persistence.Recovery
import com.packt.publishing.cassandra.actor.WFActor
import com.packt.publishing.cassandra.commands.{AddWF, PrintWF, RemoveWF, SnapshotWF}
import com.packt.publishing.cassandra.model.WeatherForecasting

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object WFApp extends App {
  val system = ActorSystem("WFSystem")

  val hyd = system.actorOf(WFActor.props("Hyd", Recovery()))
  val vja = system.actorOf(WFActor.props("Vja", Recovery()))

  hyd ! AddWF(WeatherForecasting("Hyd","12"))
  hyd ! AddWF(WeatherForecasting("Hyd", "19"))

  vja ! AddWF(WeatherForecasting("Vja", "21"))
  vja ! AddWF(WeatherForecasting("Vja", "18"))

  system.scheduler.scheduleOnce(5 second, vja, AddWF(WeatherForecasting("Vja","15")))
  system.scheduler.scheduleOnce(10 second, vja, RemoveWF(WeatherForecasting("Vja","21")))

  vja ! SnapshotWF

  hyd ! PrintWF
  vja ! PrintWF

  Thread.sleep(50000)
  system.terminate()
}
