package com.packt.publishing.concurrent.future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ScalaFutureBasicsApp extends App{

  val addFuture = add(4,4)

  println(addFuture)

  def add(x:Int, y:Int): Future[Int] = Future(x + y)

}
