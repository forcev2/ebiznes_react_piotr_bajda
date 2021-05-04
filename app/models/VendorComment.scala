package models

import play.api.libs.json._

case class VendorComment(id: Int, comment_body: String, vendor: Int, client: Int)

object VendorComment {
  implicit val categoryFormat = Json.format[VendorComment]
}

