package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ Future, ExecutionContext }




@Singleton
class ItemCommentRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                       val productRepository: ProductRepository, val clientRepository: ClientRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class ItemCommentTable(tag: Tag) extends Table[ItemComment](tag, "itemComment") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def comment_body = column[String]("comment_body")
    def product = column[Long]("product")
    def product_fk = foreignKey("product_fk",product, prod)(_.id)
    def client = column[Int]("client")
    def client_fk = foreignKey("client_fk",client, cli)(_.id)
    def * = (id, comment_body, product, client) <> ((ItemComment.apply _).tupled, ItemComment.unapply)
  }

  import productRepository.ProductTable
  import clientRepository.ClientTable

  protected val prod = TableQuery[ProductTable]
  protected val cli = TableQuery[ClientTable]

  val itemComment = TableQuery[ItemCommentTable]

  def create(comment_body: String, product: Long, client: Int): Future[ItemComment] = db.run {
    (itemComment.map(c => (c.comment_body, c.product, c.client))
      returning itemComment.map(_.id)
      into { case ((comment_body, product, client), id) => ItemComment(id, comment_body, product, client)}
      ) += (comment_body, product, client)
  }

  def list(): Future[Seq[ItemComment]] = db.run {
    itemComment.result
  }
}

