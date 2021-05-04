package models

import play.api.libs.json._

case class TransactionInfo(id: Int, date: String, product: Long, client: Int, buy_info: Int)

object TransactionInfo {
  implicit val categoryFormat = Json.format[TransactionInfo]
}