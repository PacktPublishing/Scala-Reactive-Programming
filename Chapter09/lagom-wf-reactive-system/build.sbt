organization in ThisBuild := "com.packt.publishing"

version in ThisBuild := "1.0.0"

scalaVersion in ThisBuild := "2.11.11"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val cassandraApi = "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0"
val mockito = "org.mockito" % "mockito-all" % "1.10.19" % Test
val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "3.3"

lazy val `lagom-wf-reactive-system` = (project in file("."))
  .aggregate(`wf-producer-api`,
             `wf-producer-impl`,
             `wf-consumer-api`,
             `wf-consumer-impl`,
             `wf-forntned`)

lazy val `wf-producer-api` = (project in file("wf-producer-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val `wf-producer-impl` = (project in file("wf-producer-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      lagomScaladslKafkaClient,
      lagomScaladslBroker,
      cassandraApi,
      macwire,
      scalaTest,
      playJsonDerivedCodecs
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`wf-producer-api`)

lazy val `wf-consumer-api` = (project in file("wf-consumer-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val `wf-consumer-impl` = (project in file("wf-consumer-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      lagomScaladslKafkaClient,
      lagomScaladslBroker,
      cassandraApi,
      macwire,
      scalaTest,
      mockito,
      playJsonDerivedCodecs
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`wf-consumer-api`, `wf-producer-api`)


lazy val `wf-forntned` = (project in file("wf-frontend"))
  .enablePlugins(PlayScala && LagomPlay)
   .settings(
    libraryDependencies ++= Seq(
      lagomScaladslServer,
      macwire,
     "org.ocpsoft.prettytime" % "prettytime" % "3.2.7.Final",
      "org.webjars" % "foundation" % "6.2.3",
      "org.webjars" % "foundation-icon-fonts" % "d596a3cfb3",
      playJsonDerivedCodecs

    )
   )
  .dependsOn(`wf-producer-api`, `wf-consumer-api`)

lagomCassandraEnabled in ThisBuild := false

lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")

lagomKafkaEnabled in ThisBuild := false

lagomKafkaPort in ThisBuild := 9092
