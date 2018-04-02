package com.packt.publishing.akka.streams.hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

object AkkaStreamsNumbersApp extends App{

  implicit val actorSystem = ActorSystem("NumberSystem")
  implicit val materializer = ActorMaterializer()

  val numbersSource = Source(1 to 10)
  val sampleSink = Sink.foreach(println)

  val numberFlow = Flow[Int].map(num => num+1)

  val numberRunnableGraph = numbersSource.via(numberFlow).to(sampleSink)

  numberRunnableGraph.run
  actorSystem.terminate
}