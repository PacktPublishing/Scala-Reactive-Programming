package com.packt.publishing.wfsystem

import akka.actor.Actor

sealed class WFDataMessage
case object WFStoreData extends WFDataMessage
case object WFRetrieveData extends WFDataMessage

class WFDataSystem extends Actor {
  override def receive: Receive = {
    case WFStoreData     => println("WF DataStore: Storing Data")
    case WFRetrieveData  => println("WF DataStore: Retrieving Data")
  }
}
