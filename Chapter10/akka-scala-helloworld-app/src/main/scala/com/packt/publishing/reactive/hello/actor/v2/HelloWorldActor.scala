package com.packt.publishing.reactive.hello.actor.v2

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.packt.publishing.reactive.hello.model.HelloWorld

class HelloWorldActor(actorRef: ActorRef) extends Actor with ActorLogging{

  override def receive: PartialFunction[Any, Unit] = {
    case HelloWorld => log.info("Hello World")
                       actorRef ! HelloWorld
    case _          => log.info("Unknown World")
  }

}
