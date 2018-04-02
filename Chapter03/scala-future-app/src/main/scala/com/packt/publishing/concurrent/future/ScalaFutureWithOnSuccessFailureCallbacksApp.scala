package com.packt.publishing.concurrent.future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object ScalaFutureWithOnSuccessFailureCallbacksApp extends App{


  val arthemeticFuture = Future {
    100/0
  }

  arthemeticFuture onSuccess {
    case value => println(s"Arthimetic Future completed successfully with $value.")
  }

  arthemeticFuture onFailure {
    case error => println(s"Arthimetic Future completed with error: ${error}.")
  }

  val helloWorldFuture = Future {
    "Hello World"
  }

  helloWorldFuture onSuccess {
    case value => println(s"HelloWorld Future completed successfully with $value.")
  }

  helloWorldFuture onFailure {
    case error => println(s"HelloWorld Future completed with error: ${error}.")
  }


  Thread.sleep(1000)

}
