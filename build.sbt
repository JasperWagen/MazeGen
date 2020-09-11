name := """mazeSite"""
organization := "HMRC"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.0" % "test"

libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.6.1-P28-B4"



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "HMRC.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "HMRC.binders._"
