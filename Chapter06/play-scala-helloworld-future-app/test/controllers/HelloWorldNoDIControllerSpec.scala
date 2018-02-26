package controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._


class HelloWorldNoDIControllerSpec extends PlaySpec with OneAppPerTest {


  "HelloWorldeNoDIController" should {

    "render the helloWorld page" in {
      val home = route(app, FakeRequest(GET, "/helloWorld")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Hello World Without DI")
    }

  }

}
