package com.sogeti.reactive.http

import com.sogeti.reactive.ProductInventory
import com.sogeti.reactive.http.routes.ProductsRoute
import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext


class HttpService(productInventory: ProductInventory
                 )(implicit executionContext: ExecutionContext) {

  val productsRouter = new ProductsRoute(productInventory)
  val routes =
    pathPrefix("nl") {
      productsRouter.routes}
}