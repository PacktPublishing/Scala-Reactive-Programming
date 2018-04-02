package com.packt.publishing.cassandra.actor

import akka.actor.{ActorLogging, Props}
import akka.persistence.{PersistentActor, Recovery, RecoveryCompleted, SnapshotOffer}
import com.packt.publishing.cassandra.commands.{AddWF, PrintWF, RemoveWF, SnapshotWF}
import com.packt.publishing.cassandra.events.{WFAdded, WFEvent, WFRemoved}
import com.packt.publishing.cassandra.state.WFState

object WFActor {
  def props(id: String, recovery: Recovery) = Props(new WFActor(id, recovery))
}

class WFActor(id: String, rec: Recovery) extends PersistentActor with ActorLogging {

  override val persistenceId = id
  override val recovery = rec

  var state = WFState()

  def updateState(event: WFEvent) = state = state.update(event)

   val receiveRecover: Receive = {
    case evt: WFEvent =>  log.info(s"Replaying event: $evt")
                              updateState(evt)
    case SnapshotOffer(_, recoveredState : WFState) =>
                              log.info(s"Snapshot offered: $recoveredState")
                              state = recoveredState
    case RecoveryCompleted => log.info(s"Recovery completed. Current state: $state")
   }

   val receiveCommand: Receive = {
      case AddWF(wf) => persist(WFAdded(wf))(updateState)
      case RemoveWF(wf) => persist(WFRemoved(wf))(updateState)
      case SnapshotWF =>  saveSnapshot(state)
      case PrintWF => log.info(s"Current state: $state")
   }
}
