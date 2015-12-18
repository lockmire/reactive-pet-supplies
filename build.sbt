import sbt.Keys._

name := "PetSupplies"

version := "1.0"

scalaVersion := "2.11.7"


resolvers ++= Seq(
  "Typesafe" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= {
  val akkaVersion = "2.3.12"
  val akkaStreamVersion = "1.0"
  val sprayVersion = "1.3.3"
  val scalaTestVersion = "2.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    //    "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    //    "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamVersion,
    //    "com.typesafe.akka" %% "akka-http-experimental" % akkaStreamVersion,
    //    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion,
    //    "com.typesafe.akka" %% "akka-http-testkit-experimental" % akkaStreamVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "org.reactivemongo" %% "reactivemongo" % "0.11.7",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "org.specs2" %% "specs2" % "2.3.13" % "test"
  )
}