package models

import play.api.libs.json._

case class ItemComment(id: Int, comment_body: String, product: Long, client: Int)

object ItemComment {
  implicit val categoryFormat = Json.format[ItemComment]
}

