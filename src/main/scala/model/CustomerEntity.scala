package model

import org.mindrot.jbcrypt.BCrypt

/**
  * Created by mosvince on 6-12-2015.
  */

  case class CustomerEntity(id: Option[Long] = None, username: String, password: String, role: String, firstname : String, surname: String, address:Option[String]) {
    require(!username.isEmpty, "username.empty")
    require(!password.isEmpty, "password.empty")
    def withHashedPassword(): CustomerEntity = this.copy(password = BCrypt.hashpw(password, BCrypt.gensalt()))
  }
  case class CustomerEntityUpdate(username: Option[String] = None, password: Option[String] = None) {
    def merge(customer: CustomerEntity): CustomerEntity = {
      CustomerEntity(customer.id, username.getOrElse(customer.username), password.map(ps => BCrypt.hashpw(ps, BCrypt.gensalt())).getOrElse(customer.password), customer.role, customer.firstname, customer.surname, customer.address)
    }
  }
