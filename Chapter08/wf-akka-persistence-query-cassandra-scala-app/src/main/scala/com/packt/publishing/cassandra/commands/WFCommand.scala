package com.packt.publishing.cassandra.commands

import com.packt.publishing.cassandra.model.WeatherForecasting

case class AddWF(wf: WeatherForecasting)
case class RemoveWF(wf: WeatherForecasting)

case object PrintWF
case object SnapshotWF
