package com.packt.publishing.supervision

import akka.actor.{Actor,Props,ActorSystem}

sealed trait Work
case object DevWork extends Work
case object TestingWork extends Work

class LeadDeveloperActor extends Actor{
  def receive = {
    case DevWork =>
      val devActor = context.actorOf(Props[DevActor],"Dev")
      devActor ! DevWork
    case TestingWork =>
      val qaActor = context.actorOf(Props[QAActor],"QA")
      qaActor ! TestingWork
  }
}
class DevActor extends Actor{
  def receive = {
    case DevWork =>
      println("Should Develop the application.")
    case _ =>
      println("No work.")
  }
}

class QAActor extends Actor{
  def receive = {
    case TestingWork =>
      println("Should test the application.")
    case _ =>
      println("No work.")
  }
}

object ParentChildApp extends App{
  val projectLeadSystem = ActorSystem("ProjectSystem")
  val leadDevActor = projectLeadSystem.actorOf(Props[LeadDeveloperActor],"LeadDev")
  leadDevActor ! DevWork
  leadDevActor ! TestingWork
  projectLeadSystem.terminate
}
