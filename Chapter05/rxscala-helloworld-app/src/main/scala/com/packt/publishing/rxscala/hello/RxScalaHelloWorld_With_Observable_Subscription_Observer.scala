package com.packt.publishing.rxscala.hello

import rx.lang.scala.Observable
import rx.lang.scala.Observer
import scala.language.{implicitConversions, postfixOps}

object RxScalaHelloWorld_With_Observable_Subscription_Observer extends App {

  val helloWorld: String = "Hello RxScala World!"

  val observable = Observable.from(helloWorld)

  val observer = new Observer[Char] {
    override def onNext(char: Char): Unit = {
      print(char)
      if(char == '!') println("")
    }

    override def onError(error: Throwable): Unit = {
      println("Observer: Executing onError...")
      error.printStackTrace()
    }

    override def onCompleted(): Unit = {
      println("Observer: Executing onCompleted...")
    }
  }

  observable.subscribe(observer)

}
