package models

import play.api.libs.json._

case class User(id: Int, username: String, password: String, email: String)

object User {
  implicit val categoryFormat = Json.format[User]
}

