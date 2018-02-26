package com.packt.publishing.reactive.patterns.ask

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

case object GetWeather
case class WeatherForecast(city:String, temperature:String)

class WFClient(wfTeller: ActorRef) extends Actor with ActorLogging {
  implicit val timeout: Timeout = 2.seconds

  def receive = {
    case GetWeather =>
      val wf = (wfTeller ? GetWeather)
      wf.onComplete{ wfValue =>
        log.info("Weather Forecast = " + wfValue)
      }
  }
}

class WFTeller extends Actor {
  def receive = {
    case GetWeather => sender() ! WeatherForecast("London", "12")
  }
}

object AskPatternApp extends App{
  val system = ActorSystem("WFSystem")
  val wfTeller = system.actorOf(Props[WFTeller], "WFTeller")
  val clientActor = system.actorOf(Props(new WFClient(wfTeller)), "WFClient")

  clientActor ! GetWeather
  Thread.sleep(1000)
  system.terminate()
}
