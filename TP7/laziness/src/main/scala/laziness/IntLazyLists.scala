package laziness

import MyLazyList.*

object IntLazyLists:

  def from(x: Int): MyLazyList[Int] =
    ???

  def range(x: Int, y: Int): MyLazyList[Int] =
    ???

  val anonymList: MyLazyList[Int] = cons(1, anonymList)

  lazy val infiniteTwoes: MyLazyList[Int] =
    ???

  val naturalNumbers1: MyLazyList[Int] = from(0)

  lazy val naturalNumbers2: MyLazyList[Int] =
    cons(0, ???)

  lazy val primeNumbers: MyLazyList[Int] =
    import MyLazyListState.*
    def sieve(s: MyLazyList[Int]): MyLazyList[Int] =
    ???
    sieve(???)

  lazy val fib: MyLazyList[Int] =
    cons(0, cons(1, ???))
