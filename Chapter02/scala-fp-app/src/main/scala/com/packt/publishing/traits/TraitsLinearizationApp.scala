package com.packt.publishing.traits

trait BaseStep {
  def name: String = "BaseStep"
}

trait Step1 extends BaseStep {
  override def name: String = "Step1 >> " + super.name
}
trait Step2 extends BaseStep {
  override def name: String = "Step2 >> " + super.name
}
trait Step3 extends Step1 {
  override def name: String = "Step3 >> " + super.name
}

class FinalStep1 extends Step1 with Step2 {
  override def name = "FinalStep1 >> " + super.name
}
class FinalStep2 extends Step2 with Step1{
  override def name = "FinalStep2 >> " + super.name
}
class FinalStep3 extends Step3 with Step2 with Step1{
  override def name = "FinalStep3 >> " + super.name
}
class FinalStep4 extends Step2 with Step3  with Step1{
  override def name = "FinalStep4 >> " + super.name
}

object TraitsLinearizationApp extends App {
  println(new FinalStep1().name)
  println(new FinalStep2().name)
  println(new FinalStep3().name)
  println(new FinalStep4().name)
}