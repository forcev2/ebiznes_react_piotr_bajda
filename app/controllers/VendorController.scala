package controllers

import models.{Vendor, VendorRepository}
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
class VendorController @Inject()(vendorRepository: VendorRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  val form: Form[CreateVendorForm] = Form {
    mapping(
      "company_name" -> nonEmptyText,
      "user" -> number,
      "vendor_info" -> number,
    )(CreateVendorForm.apply)(CreateVendorForm.unapply)
  }

  val updateForm: Form[UpdateVendorForm] = Form {
    mapping(
      "id" -> number,
      "company_name" -> nonEmptyText,
      "user" -> number,
      "vendor_info" -> number,
    )(UpdateVendorForm.apply)(UpdateVendorForm.unapply)
  }

  def add: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val result = vendorRepository.list()

    result.map (c => Ok(views.html.vendoradd(form )))
  }

  def addHandle = Action.async { implicit request =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.vendoradd(errorForm))
        )
      },
      obj => {
        vendorRepository.create(obj.company_name, obj.user, obj.vendor_info).map { _ =>
          Redirect(routes.VendorController.add).flashing("success" -> "created")
        }
      }
    )

  }

  def addJSON(company_name: String,  user: Int, vendor_info: Int): Action[AnyContent] = Action.async { implicit request =>
    vendorRepository.create(company_name, user, vendor_info).map {
      res => Ok(Json.toJson(res))
    }
  }

  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    vendorRepository.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Int, company_name: String,  user: Int, vendor_info: Int): Action[AnyContent] = Action.async { implicit request =>
    vendorRepository.update(id, new Vendor(id, company_name, user, vendor_info)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def getJSON() = Action.async { implicit request =>
    vendorRepository.list().map { result =>
      Ok(Json.toJson(result))
    }
  }

  def get() = Action.async { implicit request =>
    vendorRepository.list().map { result =>
      Ok(views.html.vendors(result))
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

    val result = vendorRepository.getById(id)
    result.map(obj => {
      val prodForm = updateForm.fill(UpdateVendorForm(obj.id, obj.company_name, obj.user, obj.vendor_info))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.vendorupdate(prodForm))
    })

  }

  def updateHandle = Action.async { implicit request =>

    updateForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.vendorupdate(errorForm))
        )
      },
      obj => {
        vendorRepository.update(obj.id, Vendor(obj.id, obj.company_name, obj.user, obj.vendor_info)).map { _ =>
          Redirect(routes.VendorController.update(obj.id)).flashing("success" -> " updated")
        }
      }
    )
  }

  def delete(id: Int): Action[AnyContent] = Action {
    vendorRepository.delete(id)
    Redirect("/vendor")
  }

}


case class CreateVendorForm(company_name: String,  user: Int, vendor_info: Int)
case class UpdateVendorForm(id: Int, company_name: String,  user: Int, vendor_info: Int)
