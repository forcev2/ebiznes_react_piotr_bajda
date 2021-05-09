package controllers

import models.{Client, ClientRepository}
import play.api.data.Form
import play.api.data.Forms.{mapping, nonEmptyText, number}
import play.api.libs.json.Json

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ClientController @Inject()(clientRepository: ClientRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateClientForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "user" -> number,
    )(CreateClientForm.apply)(CreateClientForm.unapply)
  }

  val updateForm: Form[UpdateClientForm] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "surname" -> nonEmptyText,
      "user" -> number,
    )(UpdateClientForm.apply)(UpdateClientForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val result = clientRepository.list()

    result.map (c => Ok(views.html.clientadd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.clientadd(errorForm))
        )
      },
      obj => {
        clientRepository.create(obj.name, obj.surname, obj.user).map { _ =>
          Redirect(routes.ClientController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def getJSON() = Action.async { implicit request =>
    clientRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def get() = Action.async { implicit request =>
    clientRepository.list().map { result =>
      Ok(views.html.clients(result))
    }
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

  def update(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>

    val result = clientRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateClientForm(obj.id, obj.name, obj.surname, obj.user))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.clientupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.clientupdate(errorForm))
        )
      },
      obj => {
        clientRepository.update(obj.id, Client(obj.id, obj.name, obj.surname, obj.user)).map { _ =>
          Redirect(routes.ClientController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    clientRepository.delete(id)
    Redirect("/client")
  }

}

case class CreateClientForm( name: String, surname: String, user: Int)
case class UpdateClientForm(id: Int, name: String, surname: String, user: Int)
