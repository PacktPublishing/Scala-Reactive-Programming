package com.packt.publishing.reactive.hello.actor

import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, TestKit, TestProbe}
import com.packt.publishing.reactive.hello.actor.HelloWorldActorSpec._
import com.packt.publishing.reactive.hello.model.HelloWorld
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class HelloWorldActorSpec extends TestKit(system)
           with Matchers with WordSpecLike with BeforeAndAfterAll {

  "HelloWorld Actor" should {
      "pass on a HelloWorld message" in {
        val testProbe = TestProbe()
        val helloWorldActor = system.actorOf(Props[HelloWorldActor], "HelloWorldActor")
        EventFilter.info(message = "Hello World", occurrences = 1)
          .intercept(helloWorldActor ! HelloWorld)
      }
  }

  override def afterAll: Unit = {
    shutdown(system)
  }

}

object HelloWorldActorSpec {
  val system = {
    val loggerConfig = ConfigFactory.parseString("akka.loggers = [akka.testkit.TestEventListener]")
    ActorSystem("AkkaHelloWorld", loggerConfig)
  }
}