package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HelloWorldDIController @Inject() extends Controller {

  def helloWorld = Action {
    Ok(views.html.helloWorld("Hello World With DI."))
  }

}
