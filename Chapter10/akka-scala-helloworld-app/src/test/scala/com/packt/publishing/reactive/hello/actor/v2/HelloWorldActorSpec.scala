package com.packt.publishing.reactive.hello.actor.v2

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import com.packt.publishing.reactive.hello.model.HelloWorld
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.concurrent.duration._

class HelloWorldActorSpec(actorSystem: ActorSystem) extends TestKit(actorSystem)
             with Matchers with WordSpecLike with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaHelloWorld"))

  "HelloWorld Actor" should {
      "pass on a HelloWorld message" in {
        val testProbe = TestProbe()
        val helloWorldActor = system.actorOf(Props(new HelloWorldActor(testProbe.ref)), "HelloWorldActor")
        helloWorldActor ! HelloWorld
        testProbe.expectMsg(500 millis, HelloWorld)
      }
  }
  override def afterAll: Unit = {
    shutdown(system)
  }

}