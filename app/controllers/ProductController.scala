package controllers

import models.ProductRepository
import play.api.libs.json.Json

import javax.inject._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ProductController @Inject()(cc: ControllerComponents, productRepo: ProductRepository) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def get: Action[AnyContent] = Action.async { implicit request =>
    val produkty = productRepo.list()
    //produkty.map( products => Json.toJson(products))
    //Ok(produkty)
    produkty.map(i => Ok("Got result: " + i))
  }

  def create = Action {
    Ok("STRING Create")
  }

  def update = Action {
    Ok("STRING Create")
  }

  def delete = Action {
    Ok("STRING Create")
  }

}
