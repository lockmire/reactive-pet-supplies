package actors

import akka.actor.{ActorLogging, Actor}
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.bson._

import scala.util.parsing.json.JSON


object Products{
  case class GetProducts(category : Option[String] , name : Option[String] , description : Option[String])

}

object Database {

  val collection = connect()

  def connect(): BSONCollection = {

    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    val db = connection("petsupplies")
    db.collection("petsupplies")
  }

  def findProducts(): Future[List[BSONDocument]] = {
    val query = BSONDocument()
    val filter = BSONDocument("category" -> 1, "name" -> 1, "description" -> 1)

    // which results in a Future[List[BSONDocument]]
    Database.collection
      .find(query, filter)
      .cursor[BSONDocument]
      .collect[List]()
  }

  def findProduct(product: String): Future[Option[BSONDocument]] = {
    val query = BSONDocument("name" -> product)

    Database.collection
      .find(query)
      .one
  }

  import actors.Products._

  class Products extends Actor with ActorLogging {

    def receive = {
      case GetProducts(category, name, description) => {
        for {
          input <- Database.findProducts
        } yield {
           val response = input
          sender ! response
        }

        log.info("Found Product")

      }
    }
  }

}