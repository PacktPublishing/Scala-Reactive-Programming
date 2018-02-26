
package controllers

import javax.inject.Inject

import com.packt.publishing.wf.api.WFService
import com.packt.publishing.wf.consumer.api.WFConsumerService
import play.api.i18n.MessagesApi
import play.api.mvc.Controller

import scala.concurrent.ExecutionContext

abstract class WFAbstractController @Inject()(messagesApi: MessagesApi, wfService: WFService, wfConsumerService:WFConsumerService)
                                           (implicit ec: ExecutionContext) extends Controller {

}







