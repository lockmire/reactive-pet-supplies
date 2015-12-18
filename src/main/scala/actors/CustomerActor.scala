package actors

import actors.CustomerActor.{GetCustomers, InitSignal}
import akka.actor.{Actor, ActorLogging}
import akka.stream.actor.{MaxInFlightRequestStrategy, RequestStrategy, ActorSubscriber}
import scala.concurrent.ExecutionContext.Implicits.global


object CustomerActor{
  case class InitSignal()
  case class GetCustomers(role : Option[String] , firstname : Option[String] , surname : Option[String], address: Option[String])

}

class CustomerActor extends Actor with ActorSubscriber with ActorLogging {

  private var inFlight = 0


  override protected def requestStrategy: RequestStrategy = new MaxInFlightRequestStrategy(10) {
    override def inFlightInternally: Int = 10
  }

  def receive = {
    case GetCustomers(role,firstname,surname, address) => {
      inFlight += 1
      for {
        input <- Database.findCustomers()
      } yield {
        val response = input
        sender ! response
      }
      inFlight -= 1
      log.info("Found Customer")

    }
    case InitSignal => {
      log.info("Welcome customer")

    }
  }


}
