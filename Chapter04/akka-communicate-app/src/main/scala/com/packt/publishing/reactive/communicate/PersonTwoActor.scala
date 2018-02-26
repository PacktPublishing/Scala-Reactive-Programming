package com.packt.publishing.reactive.communicate

import akka.actor.Actor

class PersonTwoActor extends Actor {

  override def receive = {
    case GoodMorning(name:String) =>
      println(s"PersonTwoActor Received: Good Morning ${name}.")
      sender ! HowDoYouDo
    case HowDoYouDo =>
      println(s"PersonTwoActor Received: How do you do?.")
      sender ! GoodBye
    case GoodBye =>
      println(s"PersonTwoActor Received: GoodBye.")
      sender ! GoodBye
  }
}
