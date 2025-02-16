package hofs

abstract class IntPredicateList:
  def isEmpty: Boolean
  def head: Int => Boolean
  def tail: IntPredicateList

final case class IntPredicateNil() extends IntPredicateList:
  def isEmpty: Boolean = true
  def head: Int => Boolean = throw EmptyListException()
  def tail: IntPredicateList = throw EmptyListException()

final case class IntPredicateCons(head: Int => Boolean, tail: IntPredicateList) extends IntPredicateList:
  def isEmpty: Boolean = false
