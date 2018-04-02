package filters

import javax.inject.Inject

import controllers.routes
import play.api.mvc.{EssentialAction, EssentialFilter, RequestHeader}

import scala.concurrent.ExecutionContext

class ContentSecurityPolicyFilter @Inject()(implicit ec: ExecutionContext) extends EssentialFilter {

  override def apply(next: EssentialAction): EssentialAction = EssentialAction { request: RequestHeader =>
    val webSocketUrl = routes.ChatController.chat().webSocketURL()(request)
    next(request).map { result =>
      result.withHeaders("Content-Security-Policy" -> s"connect-src 'self' $webSocketUrl")
    }
  }
}
