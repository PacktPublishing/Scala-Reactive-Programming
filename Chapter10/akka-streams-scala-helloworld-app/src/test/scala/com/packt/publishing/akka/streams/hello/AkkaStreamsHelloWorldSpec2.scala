package com.packt.publishing.akka.streams.hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.stream.testkit.scaladsl.{TestSink, TestSource}
import akka.testkit.{TestKit}
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

class AkkaStreamsHelloWorldSpec2 extends TestKit(ActorSystem("HelloWorldSystem"))
             with WordSpecLike with BeforeAndAfterAll with MustMatchers {

  override def afterAll: Unit = {
    system.terminate()
  }

  implicit val materializer = ActorMaterializer()

  "Akka Streams Components" should {

    "HelloWorld test Source" in {
      val helloWorldSource = Source.single("Akka Streams Hello World")

      helloWorldSource.runWith(TestSink.probe[String](system))
          .requestNext() mustBe "Akka Streams Hello World"

    }

    "HelloWorld test Sink'" in {
      val sink = Sink.cancelled

      TestSource.probe[String]
        .toMat(sink)(Keep.left)
        .run()
        .expectCancellation()

    }

  }

}
