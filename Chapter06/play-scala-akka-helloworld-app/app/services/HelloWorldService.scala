package services

import actors.HelloWorldActorConnector

trait HelloWorldService {
  def helloWorldController: HelloWorldActorConnector

  def helloWorldWithDI = helloWorldController.helloWorldWithDI

  def helloWorldWithoutDI = helloWorldController.helloWorldWithoutDI
}

object HelloWorldService extends HelloWorldService {
  val helloWorldController: HelloWorldActorConnector = HelloWorldActorConnector
}
