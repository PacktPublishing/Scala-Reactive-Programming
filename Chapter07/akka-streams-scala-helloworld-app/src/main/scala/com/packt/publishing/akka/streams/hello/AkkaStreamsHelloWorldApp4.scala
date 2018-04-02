package com.packt.publishing.akka.streams.hello

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}

object AkkaStreamsHelloWorldApp4 extends App{

  implicit val actorSystem = ActorSystem("HelloWorldSystem")
  implicit val materializer = ActorMaterializer()

  val helloWorldSource = Source.single("Akka Streams Hello World")
  val helloWorldSink = Sink.foreach(print)
  val helloWorldFlow = Flow[String].map(str => str.toUpperCase)

  val helloWorldGraph:RunnableGraph[NotUsed] =  helloWorldSource.to(helloWorldSink)

  helloWorldGraph.run

  actorSystem.terminate
}
