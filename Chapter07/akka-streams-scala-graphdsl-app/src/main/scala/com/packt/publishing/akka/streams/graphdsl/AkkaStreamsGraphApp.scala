package com.packt.publishing.akka.streams.graphdsl

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}
import akka.stream.{ActorMaterializer, ClosedShape}

object AkkaStreamsGraphApp extends App{

  implicit val actorSystem = ActorSystem("NumberSystem")
  implicit val materializer = ActorMaterializer()

  val graph = RunnableGraph.fromGraph(GraphDSL.create() {
    implicit builder: GraphDSL.Builder[NotUsed] =>
    import GraphDSL.Implicits._
      val in = Source(1 to 10)
      val out = Sink.foreach(println)

      val bcast = builder.add(Broadcast[Int](2))
      val merge = builder.add(Merge[Int](2))


      val f1, f2, f3, f4 = Flow[Int].map(_ + 1)

      val f5 = Flow[Int].filter(x => x % 2 ==0)

      in ~> f1 ~> bcast ~> f2 ~> merge ~> f4  ~> f5 ~> out
                  bcast ~> f3 ~> merge
      ClosedShape
  })

  graph.run

  actorSystem.terminate
}
