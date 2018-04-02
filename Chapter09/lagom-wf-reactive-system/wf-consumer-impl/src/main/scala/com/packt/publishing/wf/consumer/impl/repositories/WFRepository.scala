package com.packt.publishing.wf.consumer.impl.repositories

import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success

private[impl] class WFRepository(cassandraSession: CassandraSession)(implicit ec: ExecutionContext) {

  def fetchWFData(limit: Int): Future[Seq[WeatherForcasting]] = {
    val wfQuery = "SELECT city,temperature FROM weatherforecast LIMIT ?"

   cassandraSession
     .selectAll(wfQuery, limit.asInstanceOf[AnyRef])
     .map { rows =>
              rows.map(row =>
               WeatherForcasting(row.getString("city"), row.getString("temperature")))
      }
  }

  def fetchOneWFData: Future[WeatherForcasting] = {
    val wfQuery = "SELECT city,temperature FROM weatherforecast"

    cassandraSession
      .selectOne(wfQuery)
      .map { row =>
          row match {
            case Some(wf) => WeatherForcasting(wf.getString("city"), wf.getString("temperature"))
            case None     => WeatherForcasting("None", "0")
          }
      }
  }

}
