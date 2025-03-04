package laziness

import MyLazyList.*

class MyLazyListTest extends munit.FunSuite:

  val emptyInts: MyLazyList[Int] = empty

  val simple1 = cons(1, empty)

  val simple123 = cons(1, cons(2, cons(3, empty)))

  val infiniteOnes: MyLazyList[Int] = cons(1, infiniteOnes)

  val listWithException: MyLazyList[Int] = MyLazyList(() => throw RuntimeException("intentional exception"))

  test("isEmpty"):
    assertEquals(empty.isEmpty, true)
    assertEquals(simple1.isEmpty, false)

  test("head"):
    assertEquals(simple1.head, 1)
    intercept[RuntimeException] {
      empty.head
    }

  test("tail"):
    assertEquals(simple1.tail.isEmpty, true)
    assertEquals(simple123.tail.isEmpty, false)
    intercept[RuntimeException] {
      empty.tail
    }

  test("size"):
    assertEquals(empty.size, 0)
    assertEquals(simple1.size, 1)
    assertEquals(simple123.size, 3)

  test("foreach"):
    var sum = 0
    simple123.foreach(x => sum += x)
    assertEquals(sum, 6)

  test("contains"):
    assertEquals(emptyInts.contains(1), false)
    assertEquals(simple123.contains(1), true)
    assertEquals(simple123.contains(2), true)
    assertEquals(simple123.contains(3), true)
    assertEquals(simple123.contains(4), false)

  test("get"):
    assertEquals(simple123.get(0), 1)
    assertEquals(simple123.get(1), 2)
    assertEquals(simple123.get(2), 3)

    intercept[RuntimeException] {
      listWithException.get(0)
    }
    intercept[RuntimeException] {
      simple123.get(3)
    }
    intercept[RuntimeException] {
      simple123.get(-1)
    }
    intercept[RuntimeException] {
      emptyInts.get(0)
    }

  test("wrongTake"):
    assertEquals(empty.wrongTake(2).isEmpty, true)
    assertEquals(simple123.wrongTake(-1).isEmpty, true)
    assertEquals(simple123.wrongTake(0).isEmpty, true)
    assertEquals(simple123.wrongTake(1).size, 1)

    intercept[RuntimeException] {
      listWithException.wrongTake(1)
    }

  test("take"):
    assertEquals(empty.take(2).isEmpty, true)
    assertEquals(simple123.take(-1).isEmpty, true)
    assertEquals(simple123.take(0).isEmpty, true)
    assertEquals(simple123.take(1).size, 1)

    // expect no exception and termination
    listWithException.take(1)
    assertEquals(infiniteOnes.take(1).head, 1)

  test("drop"):
    assertEquals(empty.drop(2).isEmpty, true)
    assertEquals(simple123.drop(-1).isEmpty, false)
    assertEquals(simple123.drop(0).size, 3)
    assertEquals(simple123.drop(1).size, 2)

    // expect no exception and termination
    listWithException.drop(1)
    assertEquals(infiniteOnes.drop(1).head, 1)

  test("take with drop"):
    assertEquals(simple123.take(2).drop(1).size, 1)
    assertEquals(simple123.take(2).drop(1).head, 2)

    // expect no exception and termination
    listWithException.take(1).drop(1)
    assertEquals(infiniteOnes.take(10).drop(1).size, 9)

  test("map"):
    assertEquals(emptyInts.map(_ + 1).isEmpty, true)
    assertEquals(simple123.map(_ + 1).size, 3)
    assertEquals(simple123.map(_ + 1).head, 2)
    assertEquals(simple123.map(_ + 1).tail.head, 3)
    assertEquals(simple123.map(_ + 1).tail.tail.head, 4)

    // expect no exception and termination
    listWithException.map(_ + 1)
    assertEquals(infiniteOnes.map(_ * 3).head, 3)

  test("filter"):
    assertEquals(emptyInts.filter(_ > 0).isEmpty, true)

    assertEquals(simple123.filter(_ > 0).size, 3)

    assertEquals(simple123.filter(_ > 1).size, 2)
    assertEquals(simple123.filter(_ > 1).head, 2)
    assertEquals(simple123.filter(_ > 1).tail.head, 3)

    // expect no exception and termination
    listWithException.filter(_ > 0)
    infiniteOnes.filter(_ < 0)

  test("zip"):
    assertEquals(emptyInts.zip(emptyInts).isEmpty, true)
    assertEquals(emptyInts.zip(simple123).isEmpty, true)
    assertEquals(simple123.zip(emptyInts).isEmpty, true)

    assertEquals(simple123.zip(simple123).head, (1, 1))

    assertEquals(simple123.zip(simple123.map(_ + 1)).size, 3)
    assertEquals(simple123.zip(simple123.map(_ + 1)).tail.head, (2, 3))

    // expect no exception and termination
    listWithException.zip(simple123)
    simple123.zip(listWithException)
    assertEquals(infiniteOnes.zip(infiniteOnes).head, (1, 1))

  test("append"):
    assertEquals(emptyInts.append(emptyInts).isEmpty, true)
    assertEquals(emptyInts.append(simple123).size, 3)
    assertEquals(simple123.append(emptyInts).size, 3)

    assertEquals(simple123.append(simple123).size, 6)
    assertEquals(simple123.append(simple123).head, 1)
    assertEquals(simple123.append(simple123).tail.head, 2)
    assertEquals(simple123.append(simple123).drop(3).head, 1)

    // expect no exception and termination
    listWithException.append(emptyInts)
    assertEquals(infiniteOnes.append(infiniteOnes).head, 1)

  test("flatMap"):
    assertEquals(emptyInts.flatMap(x => cons(x, emptyInts)).isEmpty, true)
    assertEquals(simple123.flatMap(x => cons(x, emptyInts)).size, 3)

    assertEquals(simple123.flatMap(x => cons(x, simple123)).size, simple123.size * (1 + simple123.size))
    assertEquals(simple123.flatMap(x => cons(x, simple123)).head, 1)

    // expect no exception and termination
    listWithException.flatMap(x => cons(x, emptyInts))
    assertEquals(infiniteOnes.flatMap(x => cons(x, infiniteOnes)).head, 1)
