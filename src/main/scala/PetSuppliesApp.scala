package com.sogeti.reactive

import actors.Customer.InitSignal
import actors.Database.Products
import actors.Products.GetProducts
import actors.{Products, OrderProcessor, Customer}
import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.Uri.Path.Segment
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import scala.concurrent.duration._

import scala.concurrent.Await

case class Product(name: String, description: String, price: Double, picture: Option[String], category: String)

object PetSuppliesApp extends App {

  // Initialize the ActorSystem
  implicit val system = ActorSystem("PetSuppliesSystem")
  implicit val materializer = ActorMaterializer()
  val config = ConfigFactory.load()

  // Create Actors
  val customer= system.actorOf(Props[Customer], "customerActor")
  val orderProcessor= system.actorOf(Props(new OrderProcessor(customer)), "orderProcessorActor")
  val products= system.actorOf(Props[Products], "productsActor")

  val product = path("products") & parameters('category ?, 'name ?, 'description ?)
  val routes: Route =
     product { (category, name, description)  =>
      get {
        complete {
          implicit val timeout = Timeout(5 seconds)
          val future = products ? GetProducts(category, name, description)
          val result = Await.result(future, timeout.duration).asInstanceOf[String]
          println(result)
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
  val binding = Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

  //send a message to the customer
  customer ! InitSignal

  //Let's wait for a couple of seconds before we shut down the system
 // Thread.sleep(2000)

  //Shut down the ActorSystem.
 // system.shutdown()

  //import system.dispatcher

  //binding.flatMap(_.unbind()).onComplete(_ => system.shutdown())
 // println("Server is down...")



}





