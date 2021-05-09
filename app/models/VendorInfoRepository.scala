package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}




@Singleton
class VendorInfoRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class VendorInfoTable(tag: Tag) extends Table[VendorInfo](tag, "vendorInfo") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def description = column[String]("description")
    def * = (id, description) <> ((VendorInfo.apply _).tupled, VendorInfo.unapply)
  }

  val vendorInfo = TableQuery[VendorInfoTable]

  def create(description: String): Future[VendorInfo] = db.run {
    (vendorInfo.map(c => (c.description))
      returning vendorInfo.map(_.id)
      into ((description, id) => VendorInfo(id, description))
      ) += (description)
  }

  def list(): Future[Seq[VendorInfo]] = db.run {
    vendorInfo.result
  }

  def getById(id: Int): Future[VendorInfo] = db.run {
    vendorInfo.filter(_.id === id).result.head
  }

  def update(id: Int, arg2: VendorInfo): Future[Unit] = {
    val toUpdate: VendorInfo = arg2.copy(id)
    db.run(vendorInfo.filter(_.id === id).update(toUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(vendorInfo.filter(_.id === id).delete).map(_ => ())
}

