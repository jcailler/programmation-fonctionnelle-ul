package patmat

final class EmptyListException extends Exception(f"Empty list")
final class InvalidOperatorException(operatorNumber: Int) extends Exception(f"Invalid operator number $operatorNumber")
final class InvalidExpression extends RuntimeException


enum IntList:
  case IntNil
  case IntCons(x: Int, xs: IntList)

  def isEmpty: Boolean = this match
    case IntNil              => true
    case IntCons(head, tail) => false

  def head: Int = this match
    case IntCons(x, _) => x
    case _             => throw RuntimeException("head of empty")

  def tail: IntList = this match
    case IntCons(_, xs) => xs
    case _              => throw RuntimeException("tail of empty")

enum IntIntList:
  case IntIntNil
  case IntIntCons(xy: (Int, Int), xs: IntIntList)
import IntIntList.*

import IntList.*

def constructDestruct =
  IntCons(1, IntCons(2, IntNil)) match
    case IntCons(a, IntCons(b, IntNil)) =>
      println(f"Found $a and $b")
    case _ => throw Exception("Not possible!")
