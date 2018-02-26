package controllers

import play.api.mvc.{Action, Controller}
import services.HelloWorldService

class HelloWorldNoDIController extends Controller {

  val helloWorldService:HelloWorldService = HelloWorldService

  def helloWorldNoDI = Action {
    helloWorldService.helloWorldWithoutDI
    Ok(views.html.helloWorld("Hello World Without DI."))
  }

}

