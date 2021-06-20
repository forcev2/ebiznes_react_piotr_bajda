package models

import play.api.libs.json._

case class BuyInfo(id: Int, date: String, address: String, totalPrice: Int)

object BuyInfo {
  implicit val categoryFormat = Json.format[BuyInfo]
}