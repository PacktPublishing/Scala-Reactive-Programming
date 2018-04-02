package com.packt.publishing.akka.streams.hello

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object AkkaStreamsHelloWorldApp3 extends App{

  implicit val actorSystem = ActorSystem("HelloWorldSystem")
  implicit val materializer = ActorMaterializer()

  val helloWorldSource:Source[String,NotUsed] = Source.single("Akka Streams Hello World")
  val helloWorldSink: Sink[String,Future[Done]] = Sink.foreach(println)
  val helloWorldFlow:Flow[String,String,NotUsed] = Flow[String].map(str => str.toUpperCase)

  val helloWorldGraph:RunnableGraph[NotUsed] = helloWorldSource
                                                  .via(helloWorldFlow)
                                                  .to(helloWorldSink)

  val helloWorldGraph2:RunnableGraph[Future[Done]] = helloWorldSource
                                                  .via(helloWorldFlow)
                                                  .toMat(helloWorldSink)(Keep.right)

  helloWorldGraph.run

  val helloWorldMaterializedValue: Future[Done] = helloWorldGraph2.run
  helloWorldMaterializedValue.onComplete{
    case Success(Done) =>
      println("HelloWorld Stream ran succssfully.")
    case Failure(exception) =>
      println(s"HelloWorld Stream ran into an issue: ${exception}.")
  }

  actorSystem.terminate
}

