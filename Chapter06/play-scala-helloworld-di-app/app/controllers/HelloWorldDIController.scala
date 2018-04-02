package controllers

import javax.inject.Inject
import javax.inject.Singleton
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class HelloWorldDIController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def helloWorld = Action {
    Ok(views.html.helloWorld("Hello World With DI."))
  }

}
