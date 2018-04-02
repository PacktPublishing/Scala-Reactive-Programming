package com.packt.publishing.cassandra.state

import com.packt.publishing.cassandra.events.{WFAdded, WFEvent, WFRemoved}
import com.packt.publishing.cassandra.model.WeatherForecasting

case class WFState(wfs: Vector[WeatherForecasting] = Vector.empty[WeatherForecasting]) {
  def update(evt: WFEvent) = evt match {
    case WFAdded(wf) => copy(wfs :+ wf)
    case WFRemoved(wf) => copy(wfs.filterNot(_ == wf))
  }
  override def toString = wfs.mkString(",")
}