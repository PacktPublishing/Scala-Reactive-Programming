package com.packt.publishing.akka.streams.hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

import scala.concurrent.duration._

class AkkaStreamsHelloWorldSpec extends TestKit(ActorSystem("HelloWorldSystem"))
             with WordSpecLike with BeforeAndAfterAll with MustMatchers {

  override def afterAll: Unit = {
    system.terminate()
  }

  implicit val materializer = ActorMaterializer()

  "Akka Streams Components" should {

    "HelloWorld with 'Source >> Sink'" in {
      val helloWorldSource = Source.single("Akka Streams Hello World")
      val testProbe = TestProbe()
      val sink = Sink.actorRef(testProbe.ref, "Sent")

      val helloWorldGraph =  helloWorldSource.to(sink)
      helloWorldGraph.run()

      testProbe.expectMsg(500 millis, "Akka Streams Hello World")
      testProbe.expectMsg("Sent")
    }

    "HelloWorld with 'Source >> Flow >> Sink'" in {
      val helloWorldSource = Source.single("Akka Streams Hello World")
      val helloWorldFlow = Flow[String].map(str => str.toUpperCase)

      val testProbe = TestProbe()
      val helloWorldSink = Sink.actorRef(testProbe.ref, "Sent")

      val helloWorldGraph =  helloWorldSource.via(helloWorldFlow).to(helloWorldSink)
      helloWorldGraph.run()

      testProbe.expectMsg(500 millis, "AKKA STREAMS HELLO WORLD")
      testProbe.expectMsg("Sent")

    }

  }

}
