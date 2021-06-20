package models

import play.api.libs.json._

case class Vendor(id: Int, companyName: String,  user: Long, vendorInfo: Int)

object Vendor {
  implicit val categoryFormat = Json.format[Vendor]
}

