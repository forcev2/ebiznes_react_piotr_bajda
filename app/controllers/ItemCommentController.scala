package controllers

import com.mohiva.play.silhouette.api.actions.SecuredRequest
import models.{ItemComment, ItemCommentRepository}
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
class ItemCommentController @Inject()(itemCommentRepository: ItemCommentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateItemCommentForm] = Form {
    mapping(
      "comment_body" -> nonEmptyText,
      "product" -> longNumber,
      "client" -> number,
    )(CreateItemCommentForm.apply)(CreateItemCommentForm.unapply)
  }

  val updateForm: Form[UpdateItemCommentForm] = Form {
    mapping(
      "id" -> number,
      "comment_body" -> nonEmptyText,
      "product" -> longNumber,
      "client" -> number,
    )(UpdateItemCommentForm.apply)(UpdateItemCommentForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val result = itemCommentRepository.list()

    result.map (c => Ok(views.html.itemcommentadd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.itemcommentadd(errorForm))
        )
      },
      obj => {
        itemCommentRepository.create(obj.commentBody, obj.product, obj.client).map { _ =>
          Redirect(routes.ItemCommentController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def addJSON( commentBody: String, product: Long, client: Int): Action[AnyContent] = Action.async { implicit request =>
    itemCommentRepository.create(commentBody, product, client).map {
      res => Ok(Json.toJson(res))
    }
  }


  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    itemCommentRepository.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Int, commentBody: String, product: Long, client: Int): Action[AnyContent] = Action.async { implicit request =>
    itemCommentRepository.update(id, new ItemComment(id,commentBody, product, client)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def getJSON() = Action.async { implicit request =>
    itemCommentRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def getSpecificJSON(product: Long) = Action.async { implicit request =>
    itemCommentRepository.list(product).map { result =>
      Ok(Json.toJson(result))
    }
  }


  def get() = Action.async { implicit request =>
    itemCommentRepository.list().map { result =>
      Ok(views.html.itemcomments(result))
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

    val result = itemCommentRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateItemCommentForm(obj.id, obj.comment_body, obj.product, obj.client))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.itemcommentupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.itemcommentupdate(errorForm))
        )
      },
      obj => {
        itemCommentRepository.update(obj.id, ItemComment(obj.id, obj.commentBody, obj.product, obj.client)).map { _ =>
          Redirect(routes.ItemCommentController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    itemCommentRepository.delete(id)
    Redirect("/itemComment")
  }

}

case class CreateItemCommentForm(commentBody: String, product: Long, client: Int)
case class UpdateItemCommentForm(id: Int, commentBody: String, product: Long, client: Int)
