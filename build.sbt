lazy val commonSettings = Seq(
  organization := "io.annotat.swissbib",
  version := "0.1.0",
  scalaVersion := "2.12.2"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "swissbib-query",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "0.10.2.1"
    )
  )
