package controllers

import models.{BuyInfo, BuyInfoRepository, Category}
import play.api.data.Form
import play.api.data.Forms.{longNumber, mapping, nonEmptyText, number}
import play.api.libs.json.Json

import javax.inject._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class BuyInfoController @Inject()(buyInfoRepository: BuyInfoRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc)  {

  val form: Form[CreateBuyInfoForm] = Form {
    mapping(
      "date" -> nonEmptyText,
      "address" -> nonEmptyText,
      "total_price" -> number,
    )(CreateBuyInfoForm.apply)(CreateBuyInfoForm.unapply)
  }

  val updateForm: Form[UpdateBuyInfoForm] = Form {
    mapping(
      "id" -> number,
      "date" -> nonEmptyText,
      "address" -> nonEmptyText,
      "total_price" -> number,
    )(UpdateBuyInfoForm.apply)(UpdateBuyInfoForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val buyinfo = buyInfoRepository.list()

    buyinfo.map (c => Ok(views.html.buyinfoadd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.buyinfoadd(errorForm))
        )
      },
      obj => {
        buyInfoRepository.create(obj.address, obj.date, obj.totalPrice).map { _ =>
          Redirect(routes.BuyInfoController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def addJSON(date: String, address: String, totalPrice: Int): Action[AnyContent] = Action.async { implicit request =>
    buyInfoRepository.create(date, address, totalPrice).map {
      res => Ok(Json.toJson(res))
    }
  }

  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    buyInfoRepository.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Int, date: String, address: String, totalPrice: Int): Action[AnyContent] = Action.async { implicit request =>
    buyInfoRepository.update(id, new BuyInfo(id, date, address, totalPrice)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def getJSON() = Action.async { implicit request =>
    buyInfoRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def get() = Action.async { implicit request =>
    buyInfoRepository.list().map { result =>
      Ok(views.html.buyinfos(result))
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

    val result = buyInfoRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateBuyInfoForm(obj.id, obj.date, obj.address, obj.total_price))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.buyinfoupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.buyinfoupdate(errorForm))
        )
      },
      obj => {
        buyInfoRepository.update(obj.id, BuyInfo(obj.id, obj.date, obj.address, obj.totalPrice)).map { _ =>
          Redirect(routes.BuyInfoController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    buyInfoRepository.delete(id)
    Redirect("/buyInfo")
  }

}
case class CreateBuyInfoForm(date: String, address: String, totalPrice: Int)
case class UpdateBuyInfoForm(id: Int, date: String, address: String, totalPrice: Int)
