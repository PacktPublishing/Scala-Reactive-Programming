package com.packt.publishing.akka.streams.hello

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}
import scala.concurrent.Future

object AkkaStreamsHelloWorldApp_WithTypeAnnotations extends App{

  implicit val actorSystem = ActorSystem("HelloWorldSystem")
  implicit val materializer = ActorMaterializer()

  val helloWorldSource:Source[String,NotUsed] = Source.single("Akka Streams Hello World")
  val helloWorldSink: Sink[String,Future[Done]] = Sink.foreach(print)

  val strToUpperCase: String => String = str => str.toUpperCase
  val helloWorldFlow:Flow[String,String,NotUsed] = Flow[String].map(strToUpperCase)

  val helloWorldGraph:RunnableGraph[NotUsed] = helloWorldSource
                                                  .via(helloWorldFlow)
                                                  .to(helloWorldSink)


  helloWorldGraph.run

  actorSystem.terminate
}
