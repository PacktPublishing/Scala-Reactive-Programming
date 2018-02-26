name := "akka-scala-reactive-patterns-app"

version := "1.0"

scalaVersion := "2.12.4"

lazy val akkaVersion = "2.5.8"
lazy val scalaTestVersion = "3.0.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)
