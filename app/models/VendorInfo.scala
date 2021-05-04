package models

import play.api.libs.json._

case class VendorInfo(id: Int, description: String)

object VendorInfo {
  implicit val categoryFormat = Json.format[VendorInfo]
}

