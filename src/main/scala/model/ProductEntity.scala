package model

import org.mindrot.jbcrypt.BCrypt

import scala.reflect.internal.pickling.PickleBuffer

/**
  * Created by mosvince on 6-12-2015.
  */

case class ProductEntity(name: String, description: String, price: Double, picture: Option[String], category: String)

case class ProductEntityUpdate(name: String, description: String, price: Double, picture: Option[String], category: String) {
    def merge(product: ProductEntity): ProductEntity = {
      ProductEntity(product.name, product.description, product.price, product.picture, product.category )
    }
  }
