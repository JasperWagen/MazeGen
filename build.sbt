name := """mazeSite"""
organization := "HMRC"
maintainer := "jasperwagen01@gmail.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += guice

libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.0" % "test"

libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.6.1-P28-B4"

mainClass in assembly := Some("play.core.server.ProdServerStart")
fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}

PlayKeys.devSettings := Seq("play.server.http.port" -> "80")



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "HMRC.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "HMRC.binders._"
