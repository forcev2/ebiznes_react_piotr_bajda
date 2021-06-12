package models

import play.api.libs.json._

case class Vendor(id: Int, company_name: String,  user: Long, vendor_info: Int)

object Vendor {
  implicit val categoryFormat = Json.format[Vendor]
}

