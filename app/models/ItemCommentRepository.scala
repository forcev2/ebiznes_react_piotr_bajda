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

  class ItemCommentTable(tag: Tag) extends Table[ItemComment](tag, "item_comment") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def commentBody = column[String]("comment_body")
    def product = column[Long]("product")
    def productFK = foreignKey("product_fk",product, prod)(_.id)
    def client = column[Int]("client")
    def clientFk = foreignKey("client_fk",client, cli)(_.id)
    def * = (id, commentBody, product, client) <> ((ItemComment.apply _).tupled, ItemComment.unapply)
  }

  import productRepository.ProductTable
  import clientRepository.ClientTable

  protected val prod = TableQuery[ProductTable]
  protected val cli = TableQuery[ClientTable]

  val itemComment = TableQuery[ItemCommentTable]

  def create(commentBody: String, product: Long, client: Int): Future[ItemComment] = db.run {
    (itemComment.map(c => (c.commentBody, c.product, c.client))
      returning itemComment.map(_.id)
      into { case ((commentBody, product, client), id) => ItemComment(id, commentBody, product, client)}
      ) += (commentBody, product, client)
  }

  def list(): Future[Seq[ItemComment]] = db.run {
    itemComment.result
  }

  def list(product: Long): Future[Seq[ItemComment]] = db.run {
    itemComment.filter(_.product === product).result
  }

  def getById(id: Int): Future[ItemComment] = db.run {
    itemComment.filter(_.id === id).result.head
  }

  def update(id: Int, arg2: ItemComment): Future[Unit] = {
    val toUpdate: ItemComment = arg2.copy(id)
    db.run(itemComment.filter(_.id === id).update(toUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(itemComment.filter(_.id === id).delete).map(_ => ())
}

