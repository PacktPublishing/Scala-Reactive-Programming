package com.packt.publishing.concurrent.future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success,Failure}

object ScalaFutureWithRecoverCallbackApp extends App{


  val arthemeticFuture = Future {
    100/0
  } recover {
    case t => 0
  }

  arthemeticFuture onComplete {
    case Success(value) => println(s"Future completed successfully with ${value}.")
    case Failure(error) => println(s"Future completed with error: ${error}.")
  }

  Thread.sleep(1000)

}
