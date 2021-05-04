package models

import play.api.libs.json._

case class Vendor(id: Int, company_name: String,  user: Int, vendor_info: Int)

object Vendor {
  implicit val categoryFormat = Json.format[Vendor]
}

