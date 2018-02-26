package com.packt.publishing.wfsystem

import akka.actor.{Actor, Props}

sealed class WFUserMessage
case object  WFUserLogin    extends WFUserMessage
case object  WFUSerInputs   extends WFUserMessage
case object  WFUserResults  extends WFUserMessage
case object  WFUserSignout  extends WFUserMessage

class WFUserSystem extends Actor {
  override def receive: Receive = {
    case WFUserLogin   => println("WF User: Login")
    case WFUSerInputs  =>
      println("WF User: Inputs Date or City or Postcode")
      val wfDataStoreSystem = context.actorOf(Props[WFDataSystem], "WFUserData")
      wfDataStoreSystem ! WFRetrieveData
      context.stop(wfDataStoreSystem)

    case WFUserResults => println("WF User: Results")
    case WFUserSignout => println("WF User: Singout")
  }
}
