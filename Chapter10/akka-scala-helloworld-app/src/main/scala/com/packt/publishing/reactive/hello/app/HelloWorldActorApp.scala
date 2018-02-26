package com.packt.publishing.reactive.hello.app

import akka.actor.{ActorSystem, Props}
import com.packt.publishing.reactive.hello.actor.HelloWorldActor
import com.packt.publishing.reactive.hello.model.HelloWorld

object HelloWorldActorApp extends App{

       val actorSystem = ActorSystem("helloworld")

       val actor = actorSystem.actorOf(Props[HelloWorldActor], "HelloWorldActor")

       actor ! HelloWorld

       actorSystem.terminate()
}
