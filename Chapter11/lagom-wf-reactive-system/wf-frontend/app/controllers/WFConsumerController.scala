package controllers

import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import com.packt.publishing.wf.api.WFService
import com.packt.publishing.wf.consumer.api.WFConsumerService
import com.packt.publishing.wf.consumer.api.models.WeatherForcasting
import play.api.libs.json.{Format, Json}
import scala.concurrent.ExecutionContext

class WFConsumerController @Inject()(val messagesApi: MessagesApi, wfService: WFService, wfConsumerService: WFConsumerService)(implicit ec: ExecutionContext)
  extends WFAbstractController(messagesApi, wfService, wfConsumerService) with I18nSupport {

  def weather(): Action[AnyContent] = Action.async { implicit request =>
    wfConsumerService.findTopTenWFData().invoke().map {
      (result: Seq[WeatherForcasting]) => Ok(Json.toJson(result))
    }
  }

  def weatherOne(): Action[AnyContent] = Action.async { implicit request =>
    wfConsumerService.findOneWFData().invoke().map {
      (result: WeatherForcasting) => Ok(Json.toJson(result))
    }
  }

  def latestWeather(): Action[AnyContent] = Action.async { implicit request =>
    wfConsumerService.latestWF().invoke().map {
      (result:WeatherForcasting) => Ok(Json.toJson(result))
    }
  }

}

