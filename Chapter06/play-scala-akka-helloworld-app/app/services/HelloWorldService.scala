package services

import actors.HelloWorldActorController

trait HelloWorldService {
  def helloWorldController: HelloWorldActorController

  def helloWorldWithDI = helloWorldController.helloWorldWithDI

  def helloWorldWithoutDI = helloWorldController.helloWorldWithoutDI
}

object HelloWorldService extends HelloWorldService {
  val helloWorldController: HelloWorldActorController = HelloWorldActorController
}
