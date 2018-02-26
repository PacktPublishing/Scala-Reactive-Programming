package com.packt.publishing.wfsystem

import akka.actor.{ActorSystem, Props}

object WFUserSystemApp extends App {
  val wfSystem = ActorSystem("WFSystem")
  val wfUser = wfSystem.actorOf(Props[WFUserSystem],"WFUser")

  wfUser ! WFUserLogin
  wfUser ! WFUSerInputs
  Thread.sleep(1000)

  wfUser ! WFUserResults
  wfUser ! WFUserSignout

  wfSystem.terminate
}
