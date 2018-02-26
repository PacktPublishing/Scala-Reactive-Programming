package com.packt.publishing.rxscala.hello

import rx.lang.scala.{Observable, Observer}

import scala.language.{implicitConversions, postfixOps}

object RxScalaNums extends App {
  val observable = Observable.just(1,2,3,4,5)

  val observer = new Observer[Int] {
    override def onNext(num: Int): Unit = {
      println(num)
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
