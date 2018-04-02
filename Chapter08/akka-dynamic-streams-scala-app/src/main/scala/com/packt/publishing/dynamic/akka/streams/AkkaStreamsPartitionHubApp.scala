package com.packt.publishing.dynamic.akka.streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, PartitionHub, RunnableGraph, Source}

import scala.concurrent.duration._

object AkkaStreamsPartitionHubApp extends App {
    implicit val actorSystem = ActorSystem("PartitionHubSystem")
    implicit val materializer = ActorMaterializer()

    val producer = Source.tick(1.second, 1.second, "message")
                         .zipWith(Source(1 to 10))((a, b) ⇒ s"$a-$b")

    val runnableGraph: RunnableGraph[Source[String, NotUsed]] =
        producer.toMat(PartitionHub.sink(
            (size, elem) ⇒ math.abs(elem.hashCode) % size,
            startAfterNrOfConsumers = 2, bufferSize = 256))(Keep.right)

    val fromProducer: Source[String, NotUsed] = runnableGraph.run()

    fromProducer.runForeach(msg ⇒ println("consumer1: " + msg))
    fromProducer.runForeach(msg ⇒ println("consumer2: " + msg))

    Thread.sleep(5000)
    actorSystem.terminate
}
