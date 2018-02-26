package com.packt.publishing.actor.lifecycle

import akka.actor.{Actor,ActorSystem,Props}

class LifecycleActor2 extends Actor{

  override def preStart(): Unit = {
    println("LifecycleActor::preStart() invoked.")
  }

  override def receive: PartialFunction[Any, Unit] = {
    case _ =>
      println("LifecycleActor::receive() invoked.")
      throw new Exception
  }

  override def postStop(): Unit = {
    println("LifecycleActor::postStop() invoked.")
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("LifecycleActor::preRestart() invoked.")
  }

  override def postRestart(reason: Throwable): Unit = {
    println("LifecycleActor::postRestart() invoked.")
  }

}

object ActorLifecycleApp2 extends App {
  val actorSystem = ActorSystem("LifecycleSystem")
  val actor = actorSystem.actorOf(Props[LifecycleActor2], "LifecycleActor")
  actor ! 2017
  //actorSystem.terminate()
}
