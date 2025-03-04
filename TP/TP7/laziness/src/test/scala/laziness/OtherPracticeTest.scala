package laziness

import MyLazyList.*
import OtherPractice.*

class OtherPracticeTest extends munit.FunSuite:

  test("nextLine"):
    assertEquals(nextLine(List(1)), List(1, 1))
    assertEquals(nextLine(List(1, 1)), List(2, 1))
    assertEquals(nextLine(List(1, 2)), List(1, 1, 1, 2))
    assertEquals(nextLine(List(1, 1, 2, 1)), List(2, 1, 1, 2, 1, 1))
    assertEquals(nextLine(List(4, 34, 2, 2, 1, 1, 2)), List(1, 4, 1, 34, 2, 2, 2, 1, 1, 2))

  test("funSeq"):
    assertEquals(funSeq.head, List(1))
    assertEquals(funSeq.drop(5).head, List(3, 1, 2, 2, 1, 1))

  test("codes"):
    assertEquals(codes.take(100).contains(""), false)
    assertEquals(codes.contains("0"), true)
    assertEquals(codes.contains("1"), true)
    assertEquals(codes.contains("00"), true)
    assertEquals(codes.contains("01"), true)
    assertEquals(codes.contains("101"), true)
    assertEquals(codes.contains("00101011"), true)

  test("palCodes"):
    val palCodesPart = palCodes.take(1000)

    assertEquals(palCodesPart.contains(""), false)

    assertEquals(palCodesPart.contains("0"), true)
    assertEquals(palCodesPart.contains("1"), true)

    assertEquals(palCodesPart.contains("10"), false)
    assertEquals(palCodesPart.contains("00"), true)
    assertEquals(palCodesPart.contains("11"), true)

    assertEquals(palCodesPart.contains("101"), true)
    assertEquals(palCodesPart.contains("010"), true)

    assertEquals(palCodesPart.contains("011101"), false)
    assertEquals(palCodesPart.contains("001100"), true)

  test("palCodes2"):
    val palCodes2Part = palCodes2.take(1000)

    assertEquals(palCodes2Part.contains(""), false)

    assertEquals(palCodes2Part.contains("0"), true)
    assertEquals(palCodes2Part.contains("1"), true)

    assertEquals(palCodes2Part.contains("10"), false)
    assertEquals(palCodes2Part.contains("00"), true)
    assertEquals(palCodes2Part.contains("11"), true)

    assertEquals(palCodes2Part.contains("101"), true)
    assertEquals(palCodes2Part.contains("010"), true)

    assertEquals(palCodes2Part.contains("011101"), false)
    assertEquals(palCodes2Part.contains("001100"), true)
