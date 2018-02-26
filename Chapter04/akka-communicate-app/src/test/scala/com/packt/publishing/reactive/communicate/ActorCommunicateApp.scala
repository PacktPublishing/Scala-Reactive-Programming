package com.packt.publishing.reactive.communicate

import akka.actor.{ActorSystem, Props}

object ActorCommunicateApp extends App{

  val actorSystem = ActorSystem("ActorCommunicate")

  val personOneActor = actorSystem.actorOf(Props[PersonOneActor], "PersonOne")
  val personTwoActor = actorSystem.actorOf(Props[PersonTwoActor], "PersonTwo")

  personOneActor.tell(GoodMorning(personOneActor.path.name),personTwoActor)

  actorSystem.terminate()
}

