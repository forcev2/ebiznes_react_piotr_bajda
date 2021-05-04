package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}



@Singleton
class VendorRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider,
                                  val userRepository: UserRepository, val vendorInfoRepository: VendorInfoRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class VendorTable(tag: Tag) extends Table[Vendor](tag, "vendor") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def company_name = column[String]("company_name")
    def user = column[Int]("user")
    def user_fk = foreignKey("user_fk",user, us)(_.id)
    def vendor_info = column[Int]("vendor_info")
    def vendor_info_fk = foreignKey("vendor_info_fk",vendor_info, ven)(_.id)
    def * = (id, company_name, user, vendor_info) <> ((Vendor.apply _).tupled, Vendor.unapply)
  }

  import userRepository.UserTable

  val us = TableQuery[UserTable]

  import vendorInfoRepository.VendorInfoTable

  protected val ven = TableQuery[VendorInfoTable]

  protected val vendor = TableQuery[VendorTable]

  def create(company_name: String, user: Int, vendor_info: Int): Future[Vendor] = db.run {
    (vendor.map(c => (c.company_name, c.user, c.vendor_info))
      returning vendor.map(_.id)
      into {case ((company_name, user, vendor_info), id) => Vendor(id, company_name, user, vendor_info) }
      ) += (company_name, user, vendor_info)
  }

  def list(): Future[Seq[Vendor]] = db.run {
    vendor.result
  }
}

