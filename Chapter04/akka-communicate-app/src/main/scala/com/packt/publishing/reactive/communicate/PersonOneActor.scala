package com.packt.publishing.reactive.communicate

import akka.actor.Actor

class PersonOneActor extends Actor {

  override def receive = {
    case GoodMorning(name:String) =>
      println(s"PersonOneActor Received: Good Morning ${name}.")
      sender ! GoodMorning(sender.path.name)
    case HowDoYouDo =>
      println(s"PersonOneActor Received: How do you do?.")
      sender ! GoodBye
    case GoodBye =>
      println(s"PersonOneActor Received: GoodBye.")
  }
}
