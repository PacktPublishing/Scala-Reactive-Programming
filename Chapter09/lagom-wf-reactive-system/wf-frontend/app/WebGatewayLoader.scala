
import javax.inject.Inject
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{ServiceAcl, ServiceInfo}
import com.lightbend.lagom.scaladsl.client.LagomServiceClientComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.packt.publishing.wf.api.WFService
import com.packt.publishing.wf.consumer.api.WFConsumerService
import com.softwaremill.macwire._
import controllers.{Assets, WFConsumerController, WFController}
import play.api.ApplicationLoader.Context
import play.api.i18n.I18nComponents
import play.api.libs.ws.ahc.AhcWSComponents
import play.api._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import router.Routes

import scala.collection.immutable
import scala.concurrent.ExecutionContext

abstract class WebGateway @Inject()(context: Context) extends BuiltInComponentsFromContext(context)
  with I18nComponents
  with AhcWSComponents
  with LagomServiceClientComponents{

  override lazy val serviceInfo: ServiceInfo = ServiceInfo(
    "wf-frontend",
    Map(
      "wf-frontend" -> immutable.Seq(ServiceAcl.forPathRegex("(?!/api/).*"))
    )
  )
  override implicit lazy val executionContext: ExecutionContext = actorSystem.dispatcher
  override lazy val router = {
    val prefix = "/"
    wire[Routes]
  }

  lazy val wfService = serviceClient.implement[WFService]
  lazy val wfConsumerService = serviceClient.implement[WFConsumerService]
  lazy val wfController = wire[WFController]
  lazy val wfConsumerController = wire[WFConsumerController]
  lazy val assets = wire[Assets]
}

class WebGatewayLoader extends ApplicationLoader {
  override def load(context: Context): Application = context.environment.mode match {
    case Mode.Dev =>
      new WebGateway(context) with LagomDevModeComponents {}.application
    case _ =>
      new WebGateway(context) {
        override def serviceLocator = NoServiceLocator
      }.application
  }
}
