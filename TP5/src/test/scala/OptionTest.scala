package poly

import scala.collection.immutable.List

class OptionTest extends munit.FunSuite:

  val twoNumbers: List[Int] = List(-3, -5)

  val fourNumbers: List[Int] = List(1, 2, -3, 4)

  test("findFirstEvenNumber: return None if there isn't one"):
    assertEquals(findFirstEvenNumber(List(1, 3)), None)

  test("findFirstEvenNumber: return None if there isn't one"):
    assertEquals(findFirstEvenNumber(fourNumbers), Some(2))

  test("parseStringToInt"):
    assertEquals(parseStringToInt("abc"), None)
    assertEquals(parseStringToInt("-123"), Some(-123))

  test("findSquareRoot"):
    assertEquals(findSquareRoot(4), Some(2.0))
    assertEquals(findSquareRoot(0), Some(0.0))
    assertEquals(findSquareRoot(-1), None)

  test("FindSquareRootFromString"):
    assertEquals(findSquareRootFromString("0"), Some(0.0))
    assertEquals(findSquareRootFromString("9"), Some(3.0))
    assertEquals(findSquareRootFromString("-1"), None)
    assertEquals(findSquareRootFromString("abc"), None)

  test("numbers: convert correctly from numberStrings"):
    assertEquals(numbers, List(1, 2, 4))
