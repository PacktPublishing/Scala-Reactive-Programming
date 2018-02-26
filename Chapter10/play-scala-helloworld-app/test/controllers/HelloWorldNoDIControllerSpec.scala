package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

class HelloWorldNoDIControllerSpec extends PlaySpec with OneAppPerTest {

  "HelloWorldNoDIController" should {

    "render the helloWorld page from Play Routings" in {
      val helloWorld = route(app, FakeRequest(GET, "/helloWorld")).get

      status(helloWorld) mustBe OK
      contentType(helloWorld) mustBe Some("text/html")
      contentAsString(helloWorld) must include ("Hello World Without DI")
    }

    "render the helloWorld page from Play application object" in {
      val controller = app.injector.instanceOf[HelloWorldNoDIController]
      val helloWorld = controller.helloWorld().apply(FakeRequest())

      status(helloWorld) mustBe OK
      contentType(helloWorld) mustBe Some("text/html")
      contentAsString(helloWorld) must include ("Hello World Without DI")
    }

    "render the helloWorld page by creating a new instance of controller" in {
      val controller = new HelloWorldNoDIController
      val helloWorld = controller.helloWorld().apply(FakeRequest())

      status(helloWorld) mustBe OK
      contentType(helloWorld) mustBe Some("text/html")
      contentAsString(helloWorld) must include ("Hello World Without DI")
    }

  }

}
