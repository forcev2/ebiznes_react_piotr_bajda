package controllers

import models.{VendorInfo, VendorInfoRepository}
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
class VendorInfoController @Inject()(vendorInfoRepository: VendorInfoRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateVendorInfoForm] = Form {
    mapping(
      "description" -> nonEmptyText,
    )(CreateVendorInfoForm.apply)(CreateVendorInfoForm.unapply)
  }

  val updateForm: Form[UpdateVendorInfoForm] = Form {
    mapping(
      "id" -> number,
      "description" -> nonEmptyText,
    )(UpdateVendorInfoForm.apply)(UpdateVendorInfoForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val result = vendorInfoRepository.list()

    result.map (c => Ok(views.html.vendorinfoadd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.vendorinfoadd(errorForm))
        )
      },
      obj => {
        vendorInfoRepository.create(obj.description).map { _ =>
          Redirect(routes.VendorInfoController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def getJSON() = Action.async { implicit request =>
    vendorInfoRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def get() = Action.async { implicit request =>
    vendorInfoRepository.list().map { result =>
      Ok(views.html.vendorinfos(result))
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

    val result = vendorInfoRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateVendorInfoForm(obj.id, obj.description))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.vendorinfoupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.vendorinfoupdate(errorForm))
        )
      },
      obj => {
        vendorInfoRepository.update(obj.id, VendorInfo(obj.id, obj.description)).map { _ =>
          Redirect(routes.VendorInfoController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    vendorInfoRepository.delete(id)
    Redirect("/vendorInfo")
  }

}


case class CreateVendorInfoForm( description: String)
case class UpdateVendorInfoForm(id: Int, description: String)
