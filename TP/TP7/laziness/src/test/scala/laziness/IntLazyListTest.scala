package laziness

import MyLazyList.*
import IntLazyLists.*

class IntLazyListTest extends munit.FunSuite:

  def sum(xs: MyLazyList[Int]): Int =
    var sum = 0
    xs.foreach(x => sum += x)
    sum

  test("from"):
    assertEquals(from(0).take(3).size, 3)
    assertEquals(from(0).take(3).head, 0)
    assertEquals(from(0).take(3).tail.head, 1)

    assertEquals(sum(from(0).take(10)), 45)

  test("range"):
    assertEquals(range(50, 100).size, 50)
    assertEquals(range(50, 100).head, 50)
    assertEquals(range(50, 100).tail.head, 51)

    assertEquals(sum(range(50, 100)), 3725)

  test("anonymList"):
    assertEquals(anonymList.head, 1)
    assertEquals(sum(anonymList.take(100)), 100)

  test("infiniteTwoes"):
    assertEquals(infiniteTwoes.head, 2)
    assertEquals(sum(infiniteTwoes.take(100)), 200)

  test("naturalNumbers1"):
    assertEquals(naturalNumbers1.head, 0)
    assertEquals(sum(naturalNumbers1.take(100)), 4950)

  test("naturalNumbers2"):
    assertEquals(naturalNumbers2.head, 0)
    assertEquals(sum(naturalNumbers2.take(100)), 4950)

  test("primeNumbers"):
    assertEquals(primeNumbers.head, 2)
    assertEquals(primeNumbers.tail.head, 3)
    assertEquals(primeNumbers.tail.tail.head, 5)
    assertEquals(primeNumbers.drop(10).head, 31)
    assertEquals(sum(primeNumbers.take(20)), 639)

  test("fib"):
    assertEquals(fib.head, 0)
    assertEquals(fib.tail.head, 1)
    assertEquals(fib.tail.tail.head, 1)
    assertEquals(fib.tail.tail.tail.head, 2)
    assertEquals(fib.drop(10).head, 55)
    assertEquals(sum(fib.take(10)), 88)
