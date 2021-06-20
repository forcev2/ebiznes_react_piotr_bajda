package controllers

import models.{TransactionInfo, TransactionInfoRepository}
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
class TransactionInfoController @Inject()(transactionInfoRepository: TransactionInfoRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateTransactionInfoForm] = Form {
    mapping(
      "date" -> nonEmptyText,
      "product" -> longNumber,
      "client" -> number,
      "buy_info" -> number,
    )(CreateTransactionInfoForm.apply)(CreateTransactionInfoForm.unapply)
  }

  val updateForm: Form[UpdateTransactionInfoForm] = Form {
    mapping(
      "id" -> number,
      "date" -> nonEmptyText,
      "product" -> longNumber,
      "client" -> number,
      "buy_info" -> number,
    )(UpdateTransactionInfoForm.apply)(UpdateTransactionInfoForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val result = transactionInfoRepository.list()

    result.map (c => Ok(views.html.transactioninfoadd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.transactioninfoadd(errorForm))
        )
      },
      obj => {
        transactionInfoRepository.create(obj.date, obj.product, obj.client, obj.buyInfo).map { _ =>
          Redirect(routes.TransactionInfoController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def addJSON(date: String, product: Long, client: Int, buyInfo: Int): Action[AnyContent] = Action.async { implicit request =>
    transactionInfoRepository.create(date, product, client, buyInfo).map {
      res => Ok(Json.toJson(res))
    }
  }

  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    transactionInfoRepository.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Int, date: String, product: Long, client: Int, buyInfo: Int): Action[AnyContent] = Action.async { implicit request =>
    transactionInfoRepository.update(id, new TransactionInfo(id, date, product, client, buyInfo)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def getJSON() = Action.async { implicit request =>
    transactionInfoRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def get() = Action.async { implicit request =>
    transactionInfoRepository.list().map { result =>
      Ok(views.html.transactioninfos(result))
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

    val result = transactionInfoRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateTransactionInfoForm(obj.id, obj.date, obj.product, obj.client, obj.buy_info))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.transactioninfoupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.transactioninfoupdate(errorForm))
        )
      },
      obj => {
        transactionInfoRepository.update(obj.id, TransactionInfo(obj.id, obj.date, obj.product, obj.client, obj.buyInfo)).map { _ =>
          Redirect(routes.TransactionInfoController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    transactionInfoRepository.delete(id)
    Redirect("/transactionInfo")
  }

}


case class CreateTransactionInfoForm(date: String, product: Long, client: Int, buyInfo: Int)
case class UpdateTransactionInfoForm(id: Int, date: String, product: Long, client: Int, buyInfo: Int)