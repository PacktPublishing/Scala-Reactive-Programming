organization in ThisBuild := "com.packt.publishing"
version in ThisBuild := "1.0.0"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `lagom-scala-hello-service` = (project in file("."))
  .aggregate(`lagom-scala-hello-service-api`, `lagom-scala-hello-service-impl`, `lagom-scala-hello-service-stream-api`, `lagom-scala-hello-service-stream-impl`)

lazy val `lagom-scala-hello-service-api` = (project in file("lagom-scala-hello-service-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagom-scala-hello-service-impl` = (project in file("lagom-scala-hello-service-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`lagom-scala-hello-service-api`)

lazy val `lagom-scala-hello-service-stream-api` = (project in file("lagom-scala-hello-service-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `lagom-scala-hello-service-stream-impl` = (project in file("lagom-scala-hello-service-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`lagom-scala-hello-service-stream-api`, `lagom-scala-hello-service-api`)
