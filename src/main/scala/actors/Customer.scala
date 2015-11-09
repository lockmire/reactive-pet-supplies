package actors

import actors.Customer.InitSignal
import akka.actor.{Actor, ActorLogging}

object Customer{
  case class InitSignal()

}

class Customer extends Actor with ActorLogging{
  def receive = {    case InitSignal => {
    log.info("Welcome customer")

  }
  }
}
