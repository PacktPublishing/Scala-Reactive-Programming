package com.packt.publishing.wf.impl

import java.time.LocalDateTime
import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity

class WFEntity extends PersistentEntity {

  override type Command = WFCommand[_]
  override type Event = WFEvent
  override type State = WFState

  override def initialState: WFState = WFState("None", "0", LocalDateTime.now.toString)

  override def behavior: Behavior = {
    case WFState(city, temperature, _) => Actions().onCommand[UseWFMessage, Done] {

      case (UseWFMessage(city,temperature), ctx, state) =>
        ctx.thenPersist(
          WFChanged(city,temperature)
        ) { _ =>
          ctx.reply(Done)
        }

    }.onReadOnlyCommand[WF, String] {

      case (WF(city, temperature), ctx, state) =>
        ctx.reply(s"$city, $temperature!")

    }.onEvent {

      case (WFChanged(city,temperature), state) =>
        WFState(city,temperature, LocalDateTime.now().toString)

    }
  }
}
