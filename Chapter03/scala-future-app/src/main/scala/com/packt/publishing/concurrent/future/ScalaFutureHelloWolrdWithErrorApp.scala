package com.packt.publishing.concurrent.future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ScalaFutureHelloWorldWithErrorApp extends App{

  val helloWordlFuture1 = Future {
    "Hello World"
  }

  val helloWordlFuture2 = Future {
    throw new Throwable("Hello World")
  }

  println(helloWordlFuture1)
  println(helloWordlFuture2)

}
