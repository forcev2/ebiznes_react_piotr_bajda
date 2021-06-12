package controllers

import com.mohiva.play.silhouette.api.LoginInfo
import models.{User, UserRepository}
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText, number}
import play.api.libs.json.Json

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class UserController @Inject()(userRepository: UserRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateUserForm] = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "email" -> nonEmptyText,
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  val updateForm: Form[UpdateUserForm] = Form {
    mapping(
      "id" -> longNumber,
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "email" -> nonEmptyText,
    )(UpdateUserForm.apply)(UpdateUserForm.unapply)
  }

  def add: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    //val result =

    Ok(views.html.useradd(form ))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.useradd(errorForm))
        )
      },
      obj => {
        userRepository.create(obj.username, obj.password, obj.email).map { _ =>
          Redirect(routes.UserController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def addJSON(username: String, password: String, email: String): Action[AnyContent] = Action.async { implicit request =>
    userRepository.create(username, password, email).map {
      res => Ok(Json.toJson(res))
    }
  }

  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    userRepository.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Int, username: String, password: String, email: String): Action[AnyContent] = Action.async { implicit request =>
    userRepository.update(id, new User(id, new LoginInfo(username, password), email)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def getJSON() = Action { implicit request =>
    Ok("STRING Create")
  }

  def get() = Action { implicit request =>
    Ok("STRING Create")
  }

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def create = Action {
    Ok("STRING Create")
  }

  def update(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val result = userRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateUserForm(1, "", "", obj.email))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.userupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.userupdate(errorForm))
        )
      },
      obj => {
        userRepository.update(obj.id, new User(obj.id, new LoginInfo(obj.username, obj.password), obj.email)).map { _ =>
          Redirect(routes.UserController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Long): Action[AnyContent] = Action {
    userRepository.delete(id)
    Redirect("/user")
  }

}

case class CreateUserForm(username: String, password: String, email: String)
case class UpdateUserForm(id: Long, username: String, password: String, email: String)
