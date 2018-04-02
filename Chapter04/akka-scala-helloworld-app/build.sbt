name := "akka-scala-helloworld-app"

version := "1.0.0"

scalaVersion := "2.12.4"

lazy val akkaVersion = "2.5.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
)
