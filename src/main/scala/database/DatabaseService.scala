package com.sogeti.reactive.database

import slick.driver.H2Driver.api._
import com.sogeti.reactive.database.{ ProductsTable}

class DatabaseService() {
  val db = Database.forConfig("h2nerdsupplies")

}

