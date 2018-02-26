package controllers

import javax.inject.Singleton
import com.google.inject.Inject
import forms.UserData
import play.api._
import play.api.mvc._
import play.api.i18n.Messages.Implicits._
import play.api.Play.current
import services.LoginService

import scala.concurrent.ExecutionContext

@Singleton
class LoginController @Inject()(loginService:LoginService)(cc:MessagesControllerComponents)
                               (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {

  def showLoginPage: Action[AnyContent] = Action {
    implicit  request =>
      Ok(views.html.loginPage(UserData.form))
  }

  def submitLogin = Action {
    implicit request =>
      UserData.form.bindFromRequest().fold(
        formWithErrors => {
          BadRequest(views.html.loginPage(formWithErrors))
        },
        user => {
          val isValidUser = loginService.checkLogin(user)
          if(isValidUser)
            Redirect(routes.LoginController.homePage).flashing("loginStatus" -> "User logged-in successfully.")
          else
            Ok(views.html.loginPage(UserData.form))
        }
      )
  }

  def homePage = Action{
    implicit request =>
      Ok(views.html.homePage())
  }
}
