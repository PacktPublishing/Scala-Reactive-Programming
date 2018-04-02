package com.packt.publishing.rxscala.hello

import rx.lang.scala.Observable
import scala.language.{implicitConversions, postfixOps}

object RxScalaHelloWorld_With_Observable_Subscription extends App {
  val helloWorld: String = "Hello RxScala World!"
  val observable = Observable.from(helloWorld)

  observable.subscribe { msg =>
    print(s"$msg")
  }
}
