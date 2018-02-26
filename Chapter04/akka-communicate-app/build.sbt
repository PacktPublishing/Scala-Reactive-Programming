name := "akka-communicate-app"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.2",
  "com.satori" % "satori-rtm-sdk" % "1.0.3"
)

libraryDependencies += "joda-time" % "joda-time" % "2.8.1"
