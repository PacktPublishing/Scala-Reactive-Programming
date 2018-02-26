name := """play-scala-login-form-app"""
organization := "com.packt.publishing"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice
