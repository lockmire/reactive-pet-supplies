package actors

import actors.Order.{RemoveProduct, AddProduct}
import actors.OrderProcessor.{OrderRemoved, OrderPlaced}
import akka.actor.{ActorLogging, Actor}


object Order{
  case class AddProduct()
  case class RemoveProduct()
}

class Order extends Actor with ActorLogging{
  def receive = {
    case AddProduct =>    { log.info ("Add 1 product")
                            sender ! OrderPlaced
    }
    case RemoveProduct =>  {  log.info ("Remove 1 product")
                              sender ! OrderRemoved
    }
  }
}
