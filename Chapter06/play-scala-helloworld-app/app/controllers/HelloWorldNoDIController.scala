package controllers

import play.api.mvc.{Action, Controller}

class HelloWorldNoDIController extends Controller {

  def helloWorld = Action {
    Ok(views.html.helloWorld("Hello World Without DI."))
  }

}

