package actors

import akka.actor.{ActorRef, ActorSystem, Props}

trait HelloWorldActorController {

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

object HelloWorldActorController extends HelloWorldActorController {
  val system = ActorSystem("HelloWorldSystem")
  val helloWorldActor = system.actorOf(Props[HelloWorldActor],"HelloWorldActor")
}
