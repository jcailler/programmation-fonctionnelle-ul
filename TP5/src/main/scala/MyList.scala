package poly

final class EmptyListException extends Exception(f"Empty list")

enum MyList[+A]:
  case Nil
  case Cons(x: A, xs: MyList[A])

  def isEmpty: Boolean = this match
    case Nil => true
    case _   => false

  def head: A = this match
    case Nil        => throw EmptyListException()
    case Cons(x, _) => x

  def tail: MyList[A] = this match
    case Nil         => throw EmptyListException()
    case Cons(_, xs) => xs


object MyList:
  def from[A](it: Iterable[A]): MyList[A] =
    it.foldRight(Nil: MyList[A])(Cons(_, _))

enum IntList:
  case IntNil
  case IntCons(x: Int, xs: IntList)


  def map(l: IntList)(f: Int => Int): IntList =
    l match
      case IntNil         => IntNil
      case IntCons(x, xs) => IntCons(f(x), map(xs)(f))

