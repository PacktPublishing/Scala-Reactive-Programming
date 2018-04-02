package com.packt.publishing.cassandra.app

import akka.actor.ActorSystem
import akka.persistence.query.PersistenceQuery
import akka.persistence.cassandra.query.scaladsl.CassandraReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.packt.publishing.cassandra.util.WFLogger._

object WFJournalReaderApp2 extends App {

  implicit val system = ActorSystem("WFSystem")
  implicit val mat = ActorMaterializer()(system)

  val queries = PersistenceQuery(system).readJournalFor[CassandraReadJournal](CassandraReadJournal.Identifier)

  queries.eventsByPersistenceId("Vja",1,10).map(e => log(system, e.persistenceId, e.event)).to(Sink.ignore).run()

  Thread.sleep(10000)
  system.terminate()
}
