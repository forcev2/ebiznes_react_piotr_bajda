package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class BuyInfoRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider, val productRepository: ProductRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class BuyInfoTable(tag: Tag) extends Table[BuyInfo](tag, "buyInfo") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def date = column[String]("date")
    def address = column[String]("address")
    def total_price = column[Int]("total_price")
    def * = (id, date, address, total_price) <> ((BuyInfo.apply _).tupled, BuyInfo.unapply)
  }

  import productRepository.ProductTable

  protected val prod = TableQuery[ProductTable]

  val buyInfo = TableQuery[BuyInfoTable]

  def create(date: String, address: String, total_price: Int): Future[BuyInfo] = db.run {
    (buyInfo.map(c => (c.date,  c.address, c.total_price))
      returning buyInfo.map(_.id)
      into {case ((date, address, total_price), id) => BuyInfo(id, date, address, total_price) }
      ) += (date, address, total_price)
  }

  def list(): Future[Seq[BuyInfo]] = db.run {
    buyInfo.result
  }

  def delete(id: Int): Future[Unit] = db.run(buyInfo.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, new_buyInfo: BuyInfo): Future[Unit] = {
    val toUpdate: BuyInfo = new_buyInfo.copy(id)
    db.run(buyInfo.filter(_.id === id).update(toUpdate)).map(_ => ())
  }

  def getById(id: Int): Future[BuyInfo] = db.run {
    buyInfo.filter(_.id === id).result.head
  }
}

