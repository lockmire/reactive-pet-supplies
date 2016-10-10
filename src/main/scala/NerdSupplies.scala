package com.sogeti.reactive

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import database.DatabaseService

object NerdSupplies extends App  {

    implicit val system = ActorSystem()
    implicit val executor = system.dispatcher
    implicit val materializer = ActorMaterializer()
    val config = ConfigFactory.load()


  val databaseService = new DatabaseService()

  val productInventory = new ProductInventory(databaseService)
  //val orderService = new OrderService(databaseService)(productInventory)

  val httpService = new http.HttpService(productInventory)

  Http().bindAndHandle(httpService.routes, config.getString("http.interface"), config.getInt("http.port"))



}





