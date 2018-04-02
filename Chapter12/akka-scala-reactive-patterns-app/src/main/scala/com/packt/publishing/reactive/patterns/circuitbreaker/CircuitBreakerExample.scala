package com.packt.publishing.reactive.patterns.circuitbreaker

import scala.concurrent.duration._
import akka.pattern.CircuitBreaker
import akka.pattern.pipe
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import scala.concurrent.Future
import akka.util.Timeout
import akka.pattern.ask

case class MessageOne(msg:String = "is my middle name")
case class MessageTwo(msg:String = "block for me")

class DangerousActor extends Actor with ActorLogging {

  import context.dispatcher

  val breaker =
    new CircuitBreaker(
      context.system.scheduler,
      maxFailures = 5,
      callTimeout = 10.seconds,
      resetTimeout = 1.minute).onOpen(notifyMeOnOpen())

  def notifyMeOnOpen(): Unit =
    log.warning("My CircuitBreaker is now open, and will not close for one minute")

  def dangerousCall: String = "This really isn't that dangerous of a call after all"

  def receive = {
    case MessageOne ⇒
      breaker.withCircuitBreaker(Future(dangerousCall)) pipeTo sender()
    case MessageTwo ⇒
      sender() ! breaker.withSyncCircuitBreaker(dangerousCall)
  }

}

object CircuitBreakerExample extends App {
  implicit val timeout: Timeout = 2.seconds
  val system = ActorSystem("WFSystem")
  val wfTeller = system.actorOf(Props[DangerousActor], "DangerousActor")

  val future1 = wfTeller ? MessageTwo
  val future2 = wfTeller ? MessageTwo
  val future3 = wfTeller ? MessageTwo
  val future4 = wfTeller ? MessageTwo
  val future5 = wfTeller ? MessageTwo
  val future6 = wfTeller ? MessageTwo

  import scala.concurrent.ExecutionContext.Implicits.global
  future1.onComplete { value =>
    println("************ value1 = "  + value)
  }
  future2.onComplete { value =>
    println("************ value2 = "  + value)
  }
  future3.onComplete { value =>
    println("************ value3 = "  + value)
  }
  future4.onComplete { value =>
    println("************ value4 = "  + value)
  }
  future5.onComplete { value =>
    println("************ value5 = "  + value)
  }
  future6.onComplete { value =>
    println("************ value6 = "  + value)
  }
  Thread.sleep(10000)

  system.terminate()
}