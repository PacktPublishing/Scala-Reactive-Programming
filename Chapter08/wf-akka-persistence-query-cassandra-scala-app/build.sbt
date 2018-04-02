name := "wf-akka-persistence-query-cassandra-scala-app"

version := "1.0.0"

scalaVersion := "2.12.4"

lazy val akkaVersion = "2.5.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"                 % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit"               % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence"           % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query"     % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.80-RC3",
  "org.scalatest"     %% "scalatest"                  % "3.0.1"      % "test"
)
