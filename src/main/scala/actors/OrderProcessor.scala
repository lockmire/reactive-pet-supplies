package actors

import actors.OrderProcessor.{OrderPlaced, OrderRemoved}
import akka.actor.{ActorRef, ActorLogging, Actor}

object OrderProcessor {

  case class OrderPlaced()
  case class OrderRemoved()

}
class OrderProcessor (customerActor : ActorRef) extends Actor with ActorLogging{
  def receive = {   case OrderPlaced => log.info ("Order Placed")
                    case OrderRemoved => log.info ("Order Removed")
  }

}
