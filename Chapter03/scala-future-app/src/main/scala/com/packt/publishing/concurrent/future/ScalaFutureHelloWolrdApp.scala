package com.packt.publishing.concurrent.future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ScalaFutureHelloWorldApp extends App{

  val helloWordlFuture = Future("Hello World")

  val helloWordlFuture2 = Future {
    "Hello World"
  }

  val helloWordlFuture3 = Future {
    throw new Throwable("Hello World")
  }

  println(helloWordlFuture)
  println(helloWordlFuture2)
  println(helloWordlFuture3)

}
