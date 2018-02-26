package controllers

import javax.inject._
import play.api.mvc.{Controller,Action}
import services.HelloWorldService

@Singleton
class HelloWorldDIController @Inject()  extends Controller {

  val helloWorldService:HelloWorldService = HelloWorldService

  def helloWorld = Action {
    helloWorldService.helloWorldWithDI
    Ok(views.html.helloWorld("Hello World With DI."))
  }

}
