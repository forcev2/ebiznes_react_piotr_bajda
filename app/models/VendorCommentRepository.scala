package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}



@Singleton
class VendorCommentRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                         val vendorRepository: VendorRepository, val clientRepository: ClientRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class VendorCommentTable(tag: Tag) extends Table[VendorComment](tag, "vendor_comment") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def commentBody = column[String]("comment_body")
    def vendor = column[Int]("vendor")
    def vendorFK = foreignKey("vendor_fk",vendor, ven)(_.id)
    def client = column[Int]("client")
    def clientFK = foreignKey("client_fk",client, cli)(_.id)
    def * = (id, commentBody, vendor, client) <> ((VendorComment.apply _).tupled, VendorComment.unapply)
  }

  import vendorRepository.VendorTable

  val ven = TableQuery[VendorTable]

  import clientRepository.ClientTable

  protected val cli = TableQuery[ClientTable]

  protected val vendorComment = TableQuery[VendorCommentTable]

  def create(commentBody: String, vendor: Int, client: Int): Future[VendorComment] = db.run {
    (vendorComment.map(c => (c.commentBody, c.vendor, c.client))
      returning vendorComment.map(_.id)
      into {case ((commentBody, vendor, client), id) => VendorComment(id, commentBody, vendor, client) }
      ) += (commentBody, vendor, client)
  }

  def list(): Future[Seq[VendorComment]] = db.run {
    vendorComment.result
  }

  def getById(id: Int): Future[VendorComment] = db.run {
    vendorComment.filter(_.id === id).result.head
  }

  def update(id: Int, arg2: VendorComment): Future[Unit] = {
    val toUpdate: VendorComment = arg2.copy(id)
    db.run(vendorComment.filter(_.id === id).update(toUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(vendorComment.filter(_.id === id).delete).map(_ => ())
}

