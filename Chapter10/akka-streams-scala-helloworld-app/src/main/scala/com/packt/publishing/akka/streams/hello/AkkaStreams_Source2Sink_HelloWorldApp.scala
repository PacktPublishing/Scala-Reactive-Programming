package com.packt.publishing.akka.streams.hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

object AkkaStreams_Source2Sink_HelloWorldApp extends App{

  implicit val actorSystem = ActorSystem("HelloWorldSystem")
  implicit val materializer = ActorMaterializer()

  val helloWorldSource = Source.single("Akka Streams Hello World")
  val helloWorldSink = Sink.foreach(print)

  val helloWorldGraph =  helloWorldSource.to(helloWorldSink)

  helloWorldGraph.run

  actorSystem.terminate
}
