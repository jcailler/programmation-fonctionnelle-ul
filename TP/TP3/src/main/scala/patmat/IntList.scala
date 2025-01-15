package patmat

final class EmptyListException extends Exception(f"Empty list")
final class InvalidOperatorException(operatorNumber: Int) extends Exception(f"Invalid operator number $operatorNumber")

abstract class IntList:
  def isEmpty: Boolean
  def head: Int
  def tail: IntList

final case class IntNil() extends IntList:
  def isEmpty: Boolean = true
  def head: Int = throw EmptyListException()
  def tail: IntList = throw EmptyListException()

final case class IntCons(head: Int, tail: IntList) extends IntList:
  def isEmpty: Boolean = false

enum IntIntList:
  case IntIntNil
  case IntIntCons(xy: (Int, Int), xs: IntIntList)
