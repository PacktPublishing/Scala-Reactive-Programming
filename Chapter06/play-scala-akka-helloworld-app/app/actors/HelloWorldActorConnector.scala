package actors

import akka.actor.{ActorRef, ActorSystem, Props}

trait HelloWorldActorConnector {

  protected def system: ActorSystem
  protected def helloWorldActor: ActorRef

  def helloWorldWithDI = {
    implicit val dispatcher = system.dispatcher
    helloWorldActor ! HelloWorldWithDI
  }

  def helloWorldWithoutDI = {
    implicit val dispatcher = system.dispatcher
    helloWorldActor ! HelloWorldWithoutDI
  }
}

object HelloWorldActorConnector extends HelloWorldActorConnector {
  val system = ActorSystem("HelloWorldSystem")
  val helloWorldActor = system.actorOf(Props[HelloWorldActor],"HelloWorldActor")
}
