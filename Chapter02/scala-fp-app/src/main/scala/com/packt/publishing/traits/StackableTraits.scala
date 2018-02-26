package com.packt.publishing.traits

trait TopLevel {
  def text: String
  def display(): String
}

class StackableTraits extends TopLevel {
  override def text: String = ""
  override def display(): String = text
}

trait One extends StackableTraits {
  override def text: String = super.text.concat("One")
  override def display(): String =  text
}

trait Two extends StackableTraits {
  override def text: String = super.text.concat("Two")
  override def display(): String = text
}

trait Three extends StackableTraits {
  override def text: String = super.text.concat("Three")
  override def display(): String =  text
}

object StackableTraitsApp extends App {
  val one = new StackableTraits with One
  println(one.display())

  val two = new StackableTraits with Two with One
  println(two.display())

  val three = new StackableTraits with Three with Two with One
  println(three.display())

}