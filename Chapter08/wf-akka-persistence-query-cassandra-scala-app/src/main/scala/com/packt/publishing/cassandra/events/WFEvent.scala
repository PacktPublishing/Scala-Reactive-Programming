package com.packt.publishing.cassandra.events

import com.packt.publishing.cassandra.model.WeatherForecasting

sealed trait WFEvent
case class WFAdded(wf: WeatherForecasting) extends WFEvent
case class WFRemoved(wf: WeatherForecasting) extends WFEvent
