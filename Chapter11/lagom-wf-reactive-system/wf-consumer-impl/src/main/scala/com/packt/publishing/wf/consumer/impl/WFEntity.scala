package com.packt.publishing.wf.consumer.impl

import java.time.LocalDateTime

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting

class WFEntity extends PersistentEntity {

  override type Command = WFCommand[_]
  override type Event = WFEvent
  override type State = WFState

  override def initialState = WFState("None","0", LocalDateTime.now().toString)

  override def behavior: Behavior = {
    case WFState(city, temperature, _) => Actions().onCommand[SaveNewWF, Done] {
      case (SaveNewWF(city, temperature), ctx, state) =>
        println(s"New WF message came to Lagom Kafka server: ${city} ${temperature}")
        val wf = WeatherForcasting(city,temperature)
        ctx.thenPersist(WFSaved(wf)) { msgSaved: WFSaved =>
          ctx.reply(Done)
        }
    }.onEvent {
      case (WFSaved(wf), state) =>
        println(s"FYI, New WF change event fired, which is converted into a Message.")
        WFState(city, temperature, LocalDateTime.now().toString)
    }
  }
}
