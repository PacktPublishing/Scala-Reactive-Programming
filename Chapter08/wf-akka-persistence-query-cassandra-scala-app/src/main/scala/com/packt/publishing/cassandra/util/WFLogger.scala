package com.packt.publishing.cassandra.util

import akka.actor.ActorSystem

object WFLogger {

  def log(system: ActorSystem, eventid: String, event: Any) = {
    system.log.info(s"Id [$eventid] Event [$event]")
  }

}
