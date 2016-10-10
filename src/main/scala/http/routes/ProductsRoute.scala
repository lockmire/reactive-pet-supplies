package com.sogeti.reactive.http.routes

import akka.http.javadsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.Segment
import akka.http.scaladsl.server._
import com.sogeti.reactive.ProductInventory
import com.sogeti.reactive.models.Product
import spray.json.DefaultJsonProtocol

import scala.concurrent.ExecutionContext


class ProductsRoute (productInventory: ProductInventory)
(implicit executionContext: ExecutionContext)extends SprayJsonSupport with DefaultJsonProtocol {

  //import StatusCodes._
  import productInventory._

  implicit val productsFormat = jsonFormat6(Product.apply)
  //var products = List(Product("Zoomer", "Remarkably puppy-like robot dog barks, talks, scoots around and follows the owner’s movements with his rubber tail wagging.", 100,  "Robot Dogs"),
  //  Product("Bo", "Electronic walking cat, with light, sounds and cool moves.", 80, "Robot Cats"))

  val routes: Route = logRequestResult("NerdSuppliesApi") {
    pathPrefix("products") {
      get {
        complete(getProducts())
      }  ~ delete {
        path(Segment) { name =>
         // products = products.filter { _.name != name }
          complete(s"product $name verwijderd")
        }
      }
    } ~ path("") {
      complete("products request completed")
    }
  }


}
