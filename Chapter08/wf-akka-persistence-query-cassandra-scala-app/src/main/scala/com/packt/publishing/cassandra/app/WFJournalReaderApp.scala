package com.packt.publishing.cassandra.app

import akka.actor.ActorSystem
import akka.persistence.query.PersistenceQuery
import akka.persistence.cassandra.query.scaladsl.CassandraReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink

object WFJournalReaderApp extends App {

  implicit val system = ActorSystem("WFSystem")
  implicit val mat = ActorMaterializer()(system)

  val queries = PersistenceQuery(system).readJournalFor[CassandraReadJournal](CassandraReadJournal.Identifier)

  queries.persistenceIds().map(id => system.log.info(s"Id received [$id]")).to(Sink.ignore).run()

  Thread.sleep(2000)
  system.terminate()
}
