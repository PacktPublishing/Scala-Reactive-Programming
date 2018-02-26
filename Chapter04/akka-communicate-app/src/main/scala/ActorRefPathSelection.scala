import akka.actor.{Actor, ActorPath, ActorRef, ActorSystem, PoisonPill, Props}

case object Shutdown

class SimpleActor extends Actor {
  def receive = {
    case Shutdown => context.stop(self)
  }
}

object ActorRefActorPathApp extends App {
  val actorSystem = ActorSystem("SimpleSystem")
  val actorRef1:ActorRef = actorSystem.actorOf(Props[SimpleActor],"SimpleActor")
  println(s"Actor Reference1 = ${actorRef1}")
  println(s"Actor Path1 = ${actorRef1.path}")
  val actorPath:ActorPath = actorSystem / "SimpleActor"
  println(s"Actor Path = ${actorPath}")

  actorRef1 ! Shutdown

  Thread.sleep(1000)

  val actorRef2:ActorRef = actorSystem.actorOf(Props[SimpleActor],"SimpleActor")
  println(s"Actor Reference2 = ${actorRef2}")
  println(s"Actor Path2 = ${actorRef2.path}")

  actorSystem.terminate
}

