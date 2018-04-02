package com.packt.publishing.dynamic.akka.streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{MergeHub, RunnableGraph, Sink, Source}

object AkkaStreamsMergeHubApp extends App{

  implicit val actorSystem = ActorSystem("MergeHubSystem")
  implicit val materializer = ActorMaterializer()

  val consumer = Sink.foreach(println)
  val mergeHub = MergeHub.source[String](perProducerBufferSize = 16)
  val runnableGraph: RunnableGraph[Sink[String, NotUsed]] = mergeHub.to(consumer)
  val toConsumer: Sink[String, NotUsed] = runnableGraph.run()

  Source.single("Hello!").runWith(toConsumer)
  Source.single("MergeHub!").runWith(toConsumer)
  Source.single("World!").runWith(toConsumer)
  Thread.sleep(500)
  actorSystem.terminate
}
