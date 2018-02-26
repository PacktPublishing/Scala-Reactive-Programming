package com.packt.publishing.concurrent.future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success,Failure}

object ScalaFutureWithOnCompleteCallbackApp extends App{


  val arthemeticFuture = Future {
    100/0
  }

  arthemeticFuture onComplete {
    case Success(_) => println("Future completed successfully.")
    case Failure(error) => println(s"Future completed with error: ${error}.")
  }

  Thread.sleep(1000)

}
