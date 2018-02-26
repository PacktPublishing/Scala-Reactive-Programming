package com.packt.publishing.reactive.hello.actor

import akka.actor.{Actor, ActorLogging}
import com.packt.publishing.reactive.hello.model.HelloWorld

class HelloWorldActor extends Actor with ActorLogging{

  override def receive: PartialFunction[Any, Unit] = {
    case HelloWorld => log.info("Hello World")
    case _          => log.info("Unknown World")
  }

}
