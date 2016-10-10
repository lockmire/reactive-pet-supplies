package com.sogeti.reactive

import com.sogeti.reactive.database.{DatabaseService, ProductsTable}
import com.sogeti.reactive.models.Product
import slick.driver.H2Driver.api._
import scala.concurrent.{Await, Future}



class ProductInventory(val databaseService: DatabaseService)extends ProductsTable {

  import databaseService._



  def getProducts(): Future[Seq[Product]] = db.run(products.result)


/*
  try {
    val setupAction: DBIO[Unit] = DBIO.seq(
      (products.schema).create,

      // Insert some products  (id, name, description, category, price, total)
      products +=(1, "Robo Bug", "Build a mechanical walking bug!", "Robot Pets", 12.50, 5),
      products +=(2, "Bo", "Electronic walking cat, with light, sounds and cool moves!", "Robot Pets", 80, 2),
      products +=(3, "Arduino UNO", "The UNO is the best board to get started with electronics and coding", "Electronics", 7.25, 15)

    )

    val setupFuture: Future[Unit] = db.run(setupAction)

    Await.result(f, Duration.Inf)



  } finally db.close


}
*/

}