package com.packt.publishing.wf.consumer.impl

import akka.Done
import com.datastax.driver.core.{BoundStatement, PreparedStatement}
import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor.ReadSideHandler
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, EventStreamElement, ReadSideProcessor}
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting

import scala.concurrent.{ExecutionContext, Future}

class WFEventProcessor(cassandraReadSide: CassandraReadSide, cassandraSession: CassandraSession)
                      (implicit ec: ExecutionContext) extends ReadSideProcessor[WFEvent] {

  private var insertWFStmt: PreparedStatement = _

  override def buildHandler(): ReadSideHandler[WFEvent] = {
    cassandraReadSide.builder[WFEvent]("message_offset")
      .setGlobalPrepare(createTable)
      .setPrepare { _ => prepareStatements }
      .setEventHandler[WFSaved](e => insertWFCreater(e.event.wf))
      .build()
  }

  override def aggregateTags: Set[AggregateEventTag[WFEvent]] = Set(WFEventTag.INSTANCE)

  private def createTable(): Future[Done] = {
    for {
      _ <- cassandraSession.executeCreateTable(
        """        CREATE TABLE IF NOT EXISTS weatherforecast (
                      city text,
                      temperature text,
                      insertion_time timestamp,
                      PRIMARY KEY (city, insertion_time)
                    ) WITH CLUSTERING ORDER BY (insertion_time DESC)
        """)
    } yield Done
  }

  private def prepareStatements(): Future[Done] = {
    for {
      insert <- cassandraSession.prepare(
        """insert into weatherforecast (city, temperature ,insertion_time) values(?, ? ,toTimestamp(now())) """)
    } yield {
      insertWFStmt = insert
      Done
    }
  }

  private def insertWFCreater(wf: WeatherForcasting) = {
    Future.successful(List(
      insertWF(wf)
    ))
  }

  private def insertWF(wf: WeatherForcasting) = {
    insertWFStmt.bind(wf.city,wf.temperature)
  }

}
