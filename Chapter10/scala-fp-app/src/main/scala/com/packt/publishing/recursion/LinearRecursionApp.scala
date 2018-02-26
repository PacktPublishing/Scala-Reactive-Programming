package com.packt.publishing.recursion

object LinearRecursionApp extends App {

  val list = List(1,2,3,4,5,6,7,8,9)

  val list2 = linearRecursion(list)

  println("Original List = " + list)
  println("After Linear Recursion of List = " + list2)

  def linearRecursion[A](l: List[A]): List[A] = l match {
    case h :: tail => linearRecursion(tail) ::: List(h)
    case Nil => Nil
  }

}