package com.packt.publishing.wfsystem

import akka.actor.{Actor, Props}

sealed trait WFMessage
case object  WFAdminLogin      extends WFMessage
case object  WFAdminFileUpload extends WFMessage
case object  WFAdminWFUI       extends WFMessage
case object  WFAdminWFResults  extends WFMessage
case object  WFAdminSignout    extends WFMessage

class WFAdminSystem extends Actor{

  override def receive: Receive = {
    case WFAdminLogin      => println("WF Admin: Login")
    case WFAdminFileUpload =>
      println("WF Admin: FileUpload")
      val wfDataStoreSystem1 = context.actorOf(Props[WFDataSystem], "WFAdminData1")
      wfDataStoreSystem1 ! WFStoreData
      context.stop(wfDataStoreSystem1)

    case WFAdminWFUI       =>
      println("WF Admin: WFUI")
      val wfDataStoreSystem2 = context.actorOf(Props[WFDataSystem], "WFAdminData2")
      wfDataStoreSystem2 ! WFRetrieveData
      context.stop(wfDataStoreSystem2)

    case WFAdminWFResults  => println("WF Admin: WFUI Results")
    case WFAdminSignout    => println("WF Admin: Singout")
  }

}
