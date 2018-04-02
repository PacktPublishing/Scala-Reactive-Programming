package com.packt.publishing.akka.streams.hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object AkkaStreams_Source2Flow2Sink_HelloWorldApp extends App{

  implicit val actorSystem = ActorSystem("HelloWorldSystem")
  implicit val materializer = ActorMaterializer()

  val helloWorldSource = Source.single("Akka Streams Hello World")
  val helloWorldSink = Sink.foreach(print)
  val helloWorldFlow = Flow[String].map(str => str.toUpperCase)

  val helloWorldGraph =  helloWorldSource.via(helloWorldFlow).to(helloWorldSink)

  helloWorldGraph.run

  actorSystem.terminate
}
