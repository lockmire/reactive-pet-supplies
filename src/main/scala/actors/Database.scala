package actors

import play.api.libs.iteratee.Iteratee
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{Cursor, DefaultDB, MongoDriver}
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by mosvince on 13-11-2015.
  */

object Database {

  val PRODUCTS_COLLECTION = "products"
  val CUSTOMER_COLLECTION = "customers"

  val db = connect()

  /**
    * return a collection form the connected database
    * @param collectionName
    * @return
    */
  def getCollection(collectionName: String): BSONCollection = {
    db(collectionName)
  }

  /**
    * Create the database connection
    * @return
    */
  def connect(): DefaultDB = {
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    //gets a reference to the database
    connection("petsupplies")
  }

  def findProducts(): Future[List[BSONDocument]] = {
    val query = BSONDocument()
    val filter = BSONDocument("category" -> 1, "name" -> 1, "description" -> 1)

    // which results in a Future[List[BSONDocument]]
    Database.getCollection(PRODUCTS_COLLECTION)
      .find(query, filter)
      .cursor[BSONDocument]
      .collect[List]()
  }

  def findProduct(product: String): Future[Option[BSONDocument]] = {
    val query = BSONDocument("name" -> product)

    Database.getCollection(PRODUCTS_COLLECTION)
      .find(query)
      .one
  }

  def findCustomer(): Future[Option[BSONDocument]] = {
    val query = BSONDocument()
    val filter = BSONDocument("surname" -> 1)

      // which results in a Future[List[BSONDocument]]
      Database.getCollection(CUSTOMER_COLLECTION)
        .find(query)
        .one
      }

  def findCustomers(): Future[List[BSONDocument]] = {
    val query = BSONDocument()
    val filter = BSONDocument("role" -> 1, "firstname" -> 1, "surname" -> 1, "address" -> 1)

    // which results in a Future[List[BSONDocument]]
    Database.getCollection(CUSTOMER_COLLECTION)
      .find(query, filter)
      .cursor[BSONDocument]
      .collect[List]()
  }

  def streamingProducts(cursor: Cursor[BSONDocument]) =
    cursor.enumerate().apply(Iteratee.foreach { doc =>
      println(s"found document: ${BSONDocument pretty doc}")
    })
}

