package controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TransactionInfoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def get = Action {
    Ok("STRING GET")
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
