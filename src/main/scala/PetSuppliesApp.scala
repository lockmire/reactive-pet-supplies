package com.sogeti.reactive

import actors.CustomerActor.{GetCustomers, InitSignal}
import actors.Products.GetProducts
import actors.{Database, CustomerActor, OrderProcessor, Products}
import akka.actor.FSM.Failure
import akka.actor.Status.Success
import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{ResponseEntity, Uri, HttpResponse, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import model.{ProductEntity, CustomerEntity}
import play.api.libs.json.Json
import reactivemongo.bson.BSONDocument
import spray.json.DefaultJsonProtocol

import scala.concurrent.{ExecutionContextExecutor, Future, Await}
import scala.concurrent.duration._

object PetSuppliesApp extends App  {

  // Initialize the ActorSystem
  implicit val system = ActorSystem("PetSuppliesSystem")
  implicit val materializer = ActorMaterializer()
   val config = ConfigFactory.load()
   implicit val executor = system.dispatcher

  // Create Actors
  val customerActor= system.actorOf(Props[CustomerActor], "customerActor")
  val orderProcessor= system.actorOf(Props(new OrderProcessor(customerActor)), "orderProcessorActor")
  val products= system.actorOf(Props[Products], "productsActor")

  val product = path("products") & parameters('category ?, 'name ?, 'description ?)
  val customer = path("customers") & parameters('role ?, 'firstname ?, 'surname ?, 'address ?)

  trait Protocols extends DefaultJsonProtocol {
    implicit val productFormat = jsonFormat5(ProductEntity)
    implicit val customerFormat = jsonFormat7(CustomerEntity)
  }

  val routes: Route =
     product { (category, name, description)  =>
      get {
        complete {
          implicit val timeout = Timeout(5 seconds)
          val future = products ? GetProducts(category, name, description)
//          val result = Await.result(future, timeout.duration).asInstanceOf[String]
//          println(result)
//          "Received GET request for products " + category + name + description
//          HttpResponse(entity = result)
          future.map(_.toJson)
          }
        } ~
        put {
          complete {
            "Received PUT request for products " + category + name + description
          }
        }
    }~
       customer { (role , firstname , surname, address ) =>
         get {
           complete {
             println("Start Method get customer")
             implicit val timeout = Timeout(5 seconds)
//             val future = customerActor ? GetCustomers(role , firstname , surname, address)
             println("WE GOT THE FUTURE")
//             val result = Await.result(future, timeout.duration).asInstanceOf[String]
             for {
                      input <- customerActor ? GetCustomers(role , firstname , surname, address)
                     } yield {
                       HttpResponse(entity = input.asInstanceOf[String])
                     }
//             println("Received GET request for products " + role + firstname + surname + address)
//             HttpResponse(entity = result)
//             future.onComplete() match  {
//               case Success(x) =>   HttpResponse(entity = x)
//               case Failure(error) =>  HttpResponse(entity = error)
//             }
           }
         }

  }


  // Start the server on the specified interface and port.
//  val binding = Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
  val binding = Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))

//  binding.connections.foreach { connection =>
//    connection.handleWith(Flow[HttpRequest].mapAsync(asyncHandler))
//  }
  //send a message to the customer
  customerActor ! InitSignal

  //Let's wait for a couple of seconds before we shut down the system
 // Thread.sleep(2000)

  //Shut down the ActorSystem.
 // system.shutdown()

  //import system.dispatcher

  //binding.flatMap(_.unbind()).onComplete(_ => system.shutdown())
 // println("Server is down...")

  // With an async handler, we use futures. Threads aren't blocked.
//  def asyncHandler(request: HttpRequest): Future[HttpResponse] = {
//
//    // we match the request, and some simple path checking
//    request match {
//
//      // match specific path. Returns all the avaiable tickers
//      case HttpRequest(GET, Uri.Path("/getAllTickers"), _, _, _) => {
//
//        // make a db call, which returns a future.
//        // use for comprehension to flatmap this into
//        // a Future[HttpResponse]
//        for {
//          input <- Database.findAllTickers
//        } yield {
//          HttpResponse(entity = convertToString(input))
//        }
//      }
//
//      // match GET pat. Return a single ticker
//      case HttpRequest(GET, Uri.Path("/get"), _, _, _) => {
//
//        // next we match on the query paramter
//        request.uri.query.get("ticker") match {
//
//          // if we find the query parameter
//          case Some(queryParameter) => {
//
//            // query the database
//            val ticker = Database.findTicker(queryParameter)
//
//            // use a simple for comprehension, to make
//            // working with futures easier.
//            for {
//              t <- ticker
//            } yield  {
//              t match {
//                case Some(bson) => HttpResponse(entity = convertToString(bson))
//                case None => HttpResponse(status = StatusCodes.OK)
//              }
//            }
//          }
//
//          // if the query parameter isn't there
//          case None => Future(HttpResponse(status = StatusCodes.OK))
//        }
//      }
//
//      // Simple case that matches everything, just return a not found
//      case HttpRequest(_, _, _, _, _) => {
//        Future[HttpResponse] {
//          HttpResponse(status = StatusCodes.NotFound)
//        }
//      }
//    }
//  }

}





