package com.packt.publishing.recursion

object TailRecursionApp extends App {

  val list = List(1,2,3,4,5,6,7,8,9)
  val list2 = tailRecursion(list)

  println("Original List = " + list)
  println("After Tail Recursion of List = " + list2)

  def tailRecursion[A](listOfElements: List[A]): List[A] = {
    def reverse(updatedList: List[A], originalList: List[A]): List[A] = originalList match {
      case Nil => updatedList
      case head :: tail => reverse(head :: updatedList, tail)
    }

    reverse(Nil, listOfElements)
  }

}