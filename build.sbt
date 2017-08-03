name := """github-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.8.7",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

