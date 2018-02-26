package actors

import akka.actor.Actor

case object HelloWorldWithDI
case object HelloWorldWithoutDI

class HelloWorldActor extends Actor{

  override def receive: Receive = {
    case HelloWorldWithDI =>
      println("Hello World With DI")
    case HelloWorldWithoutDI =>
      println("Hello World Without DI")
    case _ =>
      println("Unknown Message")
  }

}
