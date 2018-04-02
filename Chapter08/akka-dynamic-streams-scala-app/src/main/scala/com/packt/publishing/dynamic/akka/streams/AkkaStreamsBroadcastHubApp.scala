package com.packt.publishing.dynamic.akka.streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{BroadcastHub, Keep, RunnableGraph, Source}
import scala.concurrent.duration._

object AkkaStreamsBroadcastHubApp extends App{

    implicit val actorSystem = ActorSystem("BroadcastHubSystem")
    implicit val materializer = ActorMaterializer()

    val producer = Source.tick(1.second, 1.second, "New message")

    val runnableGraph: RunnableGraph[Source[String, NotUsed]] =
      producer.toMat(BroadcastHub.sink(bufferSize = 256))(Keep.right)

    val fromProducer: Source[String, NotUsed] = runnableGraph.run()
    fromProducer.runForeach(msg ⇒ println("consumer1: " + msg))
    fromProducer.runForeach(msg ⇒ println("consumer2: " + msg))

    Thread.sleep(5000)
    actorSystem.terminate

}
