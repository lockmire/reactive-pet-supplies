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
  val scalaTestVersion = "2.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental" % akkaStreamVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    "org.reactivemongo" %% "reactivemongo" % "0.11.7"
  )
}