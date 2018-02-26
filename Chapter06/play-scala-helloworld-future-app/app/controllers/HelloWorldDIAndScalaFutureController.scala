package controllers

import javax.inject._
import models.HelloWorld
import play.api.mvc._
import services.THelloWorldService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HelloWorldDIAndScalaFutureController @Inject()(helloWorldService: THelloWorldService)(cc: ControllerComponents) extends AbstractController(cc) {

  def helloWorld = Action.async {
    val helloMsg: Future[HelloWorld] = helloWorldService.hello
    helloMsg.map{ msg =>
      Ok(views.html.helloWorldExtended(msg))
    }
  }

}
