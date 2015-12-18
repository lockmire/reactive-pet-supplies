package actors

import akka.actor.{Actor, ActorLogging}
import scala.concurrent.ExecutionContext.Implicits.global


object Products{
  case class GetProducts(category : Option[String] , name : Option[String] , description : Option[String])

}

  import actors.Products._

  class Products extends Actor with ActorLogging {

    def receive = {
      case GetProducts(category, name, description) => {
        for {
          input <- Database.findProducts
        } yield {
           val response = input
          sender ! response
        }

        log.info("Found Product")

      }
    }

}