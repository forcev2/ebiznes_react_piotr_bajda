package models

import play.api.libs.json._

case class Client(id: Int, name: String, surname: String, user: Int)

object Client {
  implicit val categoryFormat = Json.format[Client]
}

