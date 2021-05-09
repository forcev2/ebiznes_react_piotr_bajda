package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
//import slick.lifted.{TableQuery, Tag}
//import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}




@Singleton
class ClientRepository @Inject() (val dbConfigProvider: DatabaseConfigProvider, val userRepository: UserRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class ClientTable(tag: Tag) extends Table[Client](tag, "client") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def surname = column[String]("surname")
    def user = column[Int]("user")
    def user_fk = foreignKey("user_fk",user, us)(_.id)
    def * = (id, name, surname, user) <> ((Client.apply _).tupled, Client.unapply)
  }

  import userRepository.UserTable

  protected val us = TableQuery[UserTable]

  val client = TableQuery[ClientTable]

  def create(name: String, surname: String, user: Int): Future[Client] = db.run {
    (client.map(c => (c.name, c.surname, c.user))
      returning client.map(_.id)
      into {case ((name, surname, user), id )=> Client(id, name, surname, user)}
      ) += (name, surname, user)
  }

  def list(): Future[Seq[Client]] = db.run {
    client.result
  }

  def getById(id: Int): Future[Client] = db.run {
    client.filter(_.id === id).result.head
  }

  def update(id: Int, arg2: Client): Future[Unit] = {
    val toUpdate: Client = arg2.copy(id)
    db.run(client.filter(_.id === id).update(toUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(client.filter(_.id === id).delete).map(_ => ())
}

