package com.sogeti.reactive.database

import com.sogeti.reactive.models.Product
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

trait ProductsTable {

    protected val databaseService: DatabaseService

    class Products(tag: Tag)
      extends Table[Product](tag, "PRODUCTS") {

        def id: Rep[Option[Long]] = column[Option[Long]]("PRODUCT_ID", O.PrimaryKey, O.AutoInc)

        def name: Rep[String] = column[String]("PRODUCT_NAME")

        def description: Rep[String] = column[String]("PRODUCT_DESC")

        def category: Rep[String] = column[String]("CATEGORY")

        def price: Rep[Double] = column[Double]("PRICE")

        def total: Rep[Int] = column[Int]("TOTAL")

        def *  = (id, name, description,category, price, total) <> ((Product.apply _).tupled, Product.unapply)

    }

    protected val products: TableQuery[Products] = TableQuery[Products]
  }