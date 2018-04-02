package com.packt.publishing.rxscala.hello

import rx.lang.scala.Observable
import rx.lang.scala.Subscriber
import scala.language.{implicitConversions, postfixOps}

object RxScalaHelloWorld_With_Observable_Subscription_Subscriber extends App {

  val helloWorld: String = "Hello RxScala World!"

  val observable = Observable.from(helloWorld)

  val subscriber = new Subscriber[Char] {
    override def onNext(char: Char): Unit = {
      print(char)
      if(char == '!') println("")
    }

    override def onError(error: Throwable): Unit = {
      println("Subscriber: Executing onError...")
      error.printStackTrace()
    }

    override def onCompleted(): Unit = {
      println("Subscriber: Executing onCompleted...")
    }
  }

  observable.subscribe(subscriber)

}
