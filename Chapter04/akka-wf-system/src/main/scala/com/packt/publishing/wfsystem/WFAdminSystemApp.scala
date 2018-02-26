package com.packt.publishing.wfsystem

import akka.actor.{ActorSystem, Props}

object WFAdminSystemApp extends App {
  val wfSystem = ActorSystem("WFSystem")
  val wfAdmin = wfSystem.actorOf(Props[WFAdminSystem],"WFAdmin")

  wfAdmin ! WFAdminLogin
  wfAdmin ! WFAdminFileUpload
  Thread.sleep(1000)

  wfAdmin ! WFAdminWFUI
  Thread.sleep(1000)

  wfAdmin ! WFAdminWFResults
  wfAdmin ! WFAdminSignout

  wfSystem.terminate
}
