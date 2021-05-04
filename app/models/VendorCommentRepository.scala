package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}



@Singleton
class VendorCommentRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                         val vendorRepository: VendorRepository, val clientRepository: ClientRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class VendorCommentTable(tag: Tag) extends Table[VendorComment](tag, "buyInfo") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def comment_body = column[String]("comment_body")
    def vendor = column[Int]("vendor")
    def vendor_fk = foreignKey("vendor_fk",vendor, ven)(_.id)
    def client = column[Int]("client")
    def client_fk = foreignKey("client_fk",client, cli)(_.id)
    def * = (id, comment_body, vendor, client) <> ((VendorComment.apply _).tupled, VendorComment.unapply)
  }

  import vendorRepository.VendorTable

  val ven = TableQuery[VendorTable]

  import clientRepository.ClientTable

  protected val cli = TableQuery[ClientTable]

  protected val vendorComment = TableQuery[VendorCommentTable]

  def create(comment_body: String, vendor: Int, client: Int): Future[VendorComment] = db.run {
    (vendorComment.map(c => (c.comment_body, c.vendor, c.client))
      returning vendorComment.map(_.id)
      into {case ((comment_body, vendor, client), id) => VendorComment(id, comment_body, vendor, client) }
      ) += (comment_body, vendor, client)
  }

  def list(): Future[Seq[VendorComment]] = db.run {
    vendorComment.result
  }
}

