package com.sogeti.reactive

import actors.Customer.InitSignal
import actors.{OrderProcessor, Customer}
import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.Uri.Path.Segment
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.server.Directives._

case class Product(name: String, description: String, price: Double, picture: Option[String], category: String)

object PetSuppliesApp extends App {

  // Initialize the ActorSystem
  implicit val system = ActorSystem("PetSuppliesSystem")
  implicit val materializer = ActorMaterializer()
  val config = ConfigFactory.load()

  // Create Actors
  val customer= system.actorOf(Props[Customer], "customerActor")
  val orderProcessor= system.actorOf(Props(new OrderProcessor(customer)), "orderProcessorActor")

  val product = path("products") & parameters('category ?, 'name ?, 'description ?)
  val routes: Route =
     product { (category, name, description)  =>
      get {
        complete {
          "Received GET request for products " + category + name + description
        }
      } ~
        put {
          complete {
            "Received PUT request for products " + category + name + description
          }
        }
    }


  // Start the server on the specified interface and port.
  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

  //send a message to the customer
  customer ! InitSignal

  //Let's wait for a couple of seconds before we shut down the system
 // Thread.sleep(2000)

  //Shut down the ActorSystem.
 // system.shutdown()

}





