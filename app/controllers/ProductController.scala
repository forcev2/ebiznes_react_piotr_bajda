
package controllers

import javax.inject._
import models.{Category, CategoryRepository, Product, ProductRepository}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProductController @Inject()(productsRepo: ProductRepository, categoryRepo: CategoryRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  val productForm: Form[CreateProductForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
    )(CreateProductForm.apply)(CreateProductForm.unapply)
  }

  val updateProductForm: Form[UpdateProductForm] = Form {
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
    )(UpdateProductForm.apply)(UpdateProductForm.unapply)
  }


  def addProduct: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val categories = categoryRepo.list()

    categories.map (cat => Ok(views.html.productadd(productForm, cat)))
  }

  def addProductHandle = Action.async { implicit request =>
    var categ:Seq[Category] = Seq[Category]()
    val categories = categoryRepo.list().onComplete{
      case Success(cat) => categ = cat
      case Failure(_) => print("fail")
    }

    productForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.productadd(errorForm, categ))
        )
      },
      product => {
        productsRepo.create(product.name, product.description, product.category, 0).map { _ =>
          Redirect(routes.ProductController.addProduct).flashing("success" -> "product.created")
        }
      }
    )

  }


  def addJSON( name: String, description: String, category: Int, vendor: Int): Action[AnyContent] = Action.async { implicit request =>
    productsRepo.create(name, description, category, vendor).map {
      res => Ok(Json.toJson(res))
    }
  }

  def deleteJSON(id: Int): Action[AnyContent] = Action.async { implicit request =>
    productsRepo.delete(id).map {
      res => Ok(Json.toJson(id))
    }
  }

  def updateJSON(id: Long, name: String, description: String, category: Int, vendor: Int): Action[AnyContent] = Action.async { implicit request =>
    productsRepo.update(id, new Product(id, name, description, category, vendor)).map {
      res => Ok(Json.toJson(id))
    }
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getProductsJSON() = Action.async { implicit request =>
    productsRepo.list().map { products =>
      Ok(Json.toJson(products))
    }
  }

  def getSpecificJSON(id: Long) = Action.async { implicit request =>
    print(id)
    productsRepo.list(id).map { products =>
      Ok(Json.toJson(products))
    }
  }

  def getProducts() = Action.async { implicit request =>
    productsRepo.list().map { products =>
      Ok(views.html.products(products))
    }
  }

  def update(id: Long): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    var categ:Seq[Category] = Seq[Category]()
    val categories = categoryRepo.list().onComplete{
      case Success(cat) => categ = cat
      case Failure(_) => print("fail")
    }

    val produkt = productsRepo.getById(id)
    produkt.map(product => {
      val prodForm = updateProductForm.fill(UpdateProductForm(product.id, product.name, product.description,product.category))
      //  id, product.name, product.description, product.category)
      //updateProductForm.fill(prodForm)
      Ok(views.html.productupdate(prodForm, categ))
    })
  }

  def updateProductHandle = Action.async { implicit request =>
    var categ:Seq[Category] = Seq[Category]()
    val categories = categoryRepo.list().onComplete{
      case Success(cat) => categ = cat
      case Failure(_) => print("fail")
    }

    updateProductForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.productupdate(errorForm, categ))
        )
      },
      product => {
        productsRepo.update(product.id, Product(product.id, product.name, product.description, product.category, 0)).map { _ =>
          Redirect(routes.ProductController.update(product.id)).flashing("success" -> "product updated")
        }
      }
    )

  }



  def delete(id: Long): Action[AnyContent] = Action {
    productsRepo.delete(id)
    Redirect("/products")
  }

}

case class CreateProductForm(name:String, description: String, category: Int)
case class UpdateProductForm(id: Long, name: String, description: String, category: Int)