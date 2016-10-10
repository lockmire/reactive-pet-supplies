import sbt.Keys._

name := "NerdSupplies"

version := "1.0"
scalaVersion := "2.11.7"


resolvers ++= Seq(
  "Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.jcenterRepo
)

libraryDependencies ++= {
  val akkaVersion = "2.4.4"
  val akkaStreamVersion = "1.0"
  val scalaTestVersion = "2.2.5"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental" % akkaStreamVersion,
    "org.scalatest"     %% "scalatest" % scalaTestVersion % "test",
    "com.typesafe.slick" %% "slick" % "3.1.1",
    "com.h2database" % "h2" % "1.3.175",
    "org.slf4j" % "slf4j-nop" % "1.6.4"



  )
}