package com.packt.publishing.akka.streams.hello

import java.nio.file.Paths

import akka.stream.IOResult
import akka.stream.scaladsl.{FileIO, Sink, Source}
import akka.util.ByteString

import scala.concurrent.Future

object AkkaStreamsBabyStepsApp extends App{
   val numbersSource = Source.single(1 to 100)
   val fileSource:Source[ByteString, Future[IOResult]] = FileIO.fromPath(Paths.get("sample.txt"))

   val sampleSink = Sink.foreach(print)
}
