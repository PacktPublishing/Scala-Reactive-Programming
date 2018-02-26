package services

import com.google.inject.ImplementedBy
import models.HelloWorld
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@ImplementedBy(classOf[HelloWorldService])
trait THelloWorldService {

  def hello: Future[HelloWorld]
}

class HelloWorldService extends THelloWorldService {

  override def hello = Future{
    HelloWorld("Hello Future!")
  }

}