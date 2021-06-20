package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}




@Singleton
class TransactionInfoRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                           val productRepository: ProductRepository, val clientRepository: ClientRepository,
                                           val buyInfoRepository: BuyInfoRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class TransactionInfoTable(tag: Tag) extends Table[TransactionInfo](tag, "transaction_info") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def date = column[String]("date")
    def product = column[Long]("product")
    def product_fk = foreignKey("product_fk",product, prod)(_.id)
    def client = column[Int]("client")
    def client_fk = foreignKey("client_fk",client, cli)(_.id)
    def buyInfo = column[Int]("buy_info")
    def buyInfoFK = foreignKey("buy_info_fk",buyInfo, binf)(_.id)
    def * = (id, date, product, client, buyInfo) <> ((TransactionInfo.apply _).tupled, TransactionInfo.unapply)
  }

  import productRepository.ProductTable
  import clientRepository.ClientTable
  import buyInfoRepository.BuyInfoTable

  protected val prod = TableQuery[ProductTable]
  protected val cli = TableQuery[ClientTable]
  protected val binf = TableQuery[BuyInfoTable]

  val transactionInfo = TableQuery[TransactionInfoTable]

  def create(date: String, product: Long, client: Int, buyInfo: Int): Future[TransactionInfo] = db.run {
    (transactionInfo.map(c => (c.date, c.product, c.client, c.buyInfo))
      returning transactionInfo.map(_.id)
      into { case ((date,product, client, buyInfo), id) => TransactionInfo(id, date, product, client, buyInfo) }
      ) += (date, product, client, buyInfo)
  }

  def list(): Future[Seq[TransactionInfo]] = db.run {
    transactionInfo.result
  }

  def getById(id: Int): Future[TransactionInfo] = db.run {
    transactionInfo.filter(_.id === id).result.head
  }

  def update(id: Int, arg2: TransactionInfo): Future[Unit] = {
    val toUpdate: TransactionInfo = arg2.copy(id)
    db.run(transactionInfo.filter(_.id === id).update(toUpdate)).map(_ => ())
  }


  def delete(id: Int): Future[Unit] = db.run(transactionInfo.filter(_.id === id).delete).map(_ => ())
}

