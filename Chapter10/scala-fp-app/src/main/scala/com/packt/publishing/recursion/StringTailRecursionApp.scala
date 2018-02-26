package com.packt.publishing.recursion

object StringTailRecursionApp extends App {

  val emptyString = ""
  val helloWorldd = "Hello World"
  val revHelloWorldd = tailRecursion(helloWorldd)

  println("Original HelloWorld = " + helloWorldd)
  println("After Tail Recursion of HelloWorld = " + revHelloWorldd)

  def tailRecursion(originalString: String): String = {
    @scala.annotation.tailrec
    def reverse(originalString: String, reversedString: String): String = {
      if (originalString == null) return null
      if (originalString.tail.isEmpty) return originalString.head + reversedString
      reverse(originalString.tail, originalString.head + reversedString)
    }

    reverse(originalString, emptyString)
  }

}