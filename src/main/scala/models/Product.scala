package com.sogeti.reactive.models

case class Product(id: Option[Long] = None,
                   name: String,
                   description: String,
                   category: String,
                   price: Double,
                   total: Int)
