package controllers

import models.{VendorComment, VendorCommentRepository}
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
class VendorCommentController @Inject()(vendorCommentRepository: VendorCommentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateVendorCommentForm] = Form {
    mapping(
      "comment_body" -> nonEmptyText,
      "vendor" -> number,
      "client" -> number,
    )(CreateVendorCommentForm.apply)(CreateVendorCommentForm.unapply)
  }

  val updateForm: Form[UpdateVendorCommentForm] = Form {
    mapping(
      "id" -> number,
      "comment_body" -> nonEmptyText,
      "vendor" -> number,
      "client" -> number,
    )(UpdateVendorCommentForm.apply)(UpdateVendorCommentForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val result = vendorCommentRepository.list()

    result.map (c => Ok(views.html.vendorcommentadd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.vendorcommentadd(errorForm))
        )
      },
      obj => {
        vendorCommentRepository.create(obj.commentBody, obj.vendor, obj.client).map { _ =>
          Redirect(routes.VendorCommentController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def addJSON( commentBody: String, vendor: Int, client: Int): Action[AnyContent] = Action.async { implicit request =>
    vendorCommentRepository.create(commentBody, vendor, client).map {
      res => Ok(Json.toJson(res))
    }
  }

  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    vendorCommentRepository.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Int, commentBody: String, vendor: Int, client: Int): Action[AnyContent] = Action.async { implicit request =>
    vendorCommentRepository.update(id, new VendorComment(id, commentBody, vendor, client)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def getJSON() = Action.async { implicit request =>
    vendorCommentRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def get() = Action.async { implicit request =>
    vendorCommentRepository.list().map { result =>
      Ok(views.html.vendorcomments(result))
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

    val result = vendorCommentRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateVendorCommentForm(obj.id, obj.commentBody, obj.vendor, obj.client))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.vendorcommentupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.vendorcommentupdate(errorForm))
        )
      },
      obj => {
        vendorCommentRepository.update(obj.id, VendorComment(obj.id, obj.commentBody, obj.vendor, obj.client)).map { _ =>
          Redirect(routes.VendorController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    vendorCommentRepository.delete(id)
    Redirect("/vendorComment")
  }

}

case class CreateVendorCommentForm(commentBody: String, vendor: Int, client: Int)
case class UpdateVendorCommentForm(id: Int, commentBody: String, vendor: Int, client: Int)
