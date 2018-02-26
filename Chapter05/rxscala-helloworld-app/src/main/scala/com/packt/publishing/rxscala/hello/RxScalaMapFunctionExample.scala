package com.packt.publishing.rxscala.hello

import rx.lang.scala.Observable

object RxScalaMapFunctionExample extends App {

  val observable = Observable.just(1)

  observable.map(x => x)
}
