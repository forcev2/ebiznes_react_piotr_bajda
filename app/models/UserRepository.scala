package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}



@Singleton
class UserRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "user") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")
    def email = column[String]("email")
    def * = (id, username, password, email) <> ((User.apply _).tupled, User.unapply)
  }

  val user = TableQuery[UserTable]

  def create(username: String, password: String, email: String): Future[User] = db.run {
    (user.map(c => (c.username, c.password, c.email))
      returning user.map(_.id)
      into {case ((username, password, email), id )=> User(id, username, password, email)}
      ) += (username, password, email)
  }

  def list(): Future[Seq[User]] = db.run {
    user.result
  }

  def getById(id: Int): Future[User] = db.run {
    user.filter(_.id === id).result.head
  }

  def update(id: Int, arg2: User): Future[Unit] = {
    val toUpdate: User = arg2.copy(id)
    db.run(user.filter(_.id === id).update(toUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(user.filter(_.id === id).delete).map(_ => ())
}

