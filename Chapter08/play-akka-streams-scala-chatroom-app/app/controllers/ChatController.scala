package controllers

import java.net.URI
import javax.inject._
import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.Materializer
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, MergeHub, Source}
import play.api.Logger
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChatController @Inject()(cc: ControllerComponents)
                              (implicit actorSystem: ActorSystem,
                               mat: Materializer,
                               executionContext: ExecutionContext,
                               webJarsUtil: org.webjars.play.WebJarsUtil) 
                               extends AbstractController(cc) with RequestMarkerContext {

  private type WSMessage = String

  private val logger = Logger(getClass)

  private implicit val logging = Logging(actorSystem.eventStream, logger.underlyingLogger.getName)

  private val (chatSink, chatSource) = {
    val source = MergeHub.source[WSMessage]
      .log("source")
      .recoverWithRetries(-1, { case _: Exception ⇒ Source.empty })

    val sink = BroadcastHub.sink[WSMessage]
    source.toMat(sink)(Keep.both).run()
  }

  private val userFlow: Flow[WSMessage, WSMessage, _] = {
     Flow.fromSinkAndSource(chatSink, chatSource)
  }

  def index: Action[AnyContent] = Action { implicit request: RequestHeader =>
    val webSocketUrl = routes.ChatController.chat().webSocketURL()
    logger.info(s"index: ")
    Ok(views.html.index(webSocketUrl))
  }

  def chat(): WebSocket = {
    WebSocket.acceptOrResult[WSMessage, WSMessage] {
      case rh if sameOriginCheck(rh) =>
        Future.successful(userFlow).map { flow =>
          Right(flow)
        }.recover {
          case e: Exception =>
            val msg = "Cannot create websocket"
            logger.error(msg, e)
            val result = InternalServerError(msg)
            Left(result)
        }

      case rejected =>
        logger.error(s"Request ${rejected} failed same origin check")
        Future.successful {
          Left(Forbidden("forbidden"))
        }
    }
  }

  private def sameOriginCheck(implicit rh: RequestHeader): Boolean = {
    logger.debug("Checking the ORIGIN ")
    
    rh.headers.get("Origin") match {
      case Some(originValue) if originMatches(originValue) =>
        logger.debug(s"originCheck: originValue = $originValue")
        true

      case Some(badOrigin) =>
        logger.error(s"originCheck: rejecting request because Origin header value ${badOrigin} is not in the same origin")
        false

      case None =>
        logger.error("originCheck: rejecting request because no Origin header found")
        false
    }
  }

  private def originMatches(origin: String): Boolean = {
    try {
      val url = new URI(origin)
      url.getHost == "localhost" &&
        (url.getPort match { case 9000 | 19001 => true; case _ => false })
    } catch {
      case e: Exception => false
    }
  }

}
