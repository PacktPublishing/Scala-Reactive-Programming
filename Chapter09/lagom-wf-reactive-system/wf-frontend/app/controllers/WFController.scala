package controllers

import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import scala.concurrent.{ExecutionContext, Future}
import com.packt.publishing.wf.api.WFService
import com.packt.publishing.wf.api.model.WFMessage
import com.packt.publishing.wf.consumer.api.WFConsumerService
import models.WFForm._

class WFController @Inject()(val messagesApi: MessagesApi, wfService: WFService, wfConsumerService: WFConsumerService)(implicit ec: ExecutionContext)
  extends WFAbstractController(messagesApi, wfService, wfConsumerService) with I18nSupport {

  def show: Action[AnyContent] = Action.async { implicit request =>
    Future(Ok(views.html.wf(wfForm)))
  }

  def changeWF(): Action[AnyContent] = Action.async { implicit request =>
    wfForm.bindFromRequest.fold(
      badForm => Future {
        BadRequest(views.html.wf(badForm))
      },
      validForm => {
        for {
          result <- wfService.wfTemperature(validForm.city, validForm.temperature)
                             .invoke(WFMessage(validForm.city, validForm.temperature))
        }
          yield {
            Ok("WeatherForecast saved to data store successfully.")
          }
      }
    )
  }

}

