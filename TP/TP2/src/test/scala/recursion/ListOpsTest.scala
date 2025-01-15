package recursion

class ListOpsTests extends munit.FunSuite:
  val threeNumbers: IntList =
    IntCons(2, IntCons(1, IntCons(0, IntNil())))

  val tenNumbers: IntList =
    IntCons(-9, IntCons(-8, IntCons(-7, IntCons(-6, IntCons(-5, IntCons(4, IntCons(3, threeNumbers)))))))

  val twentyNumbers: IntList =
    IntCons(
      -19,
      IntCons(
        -18,
        IntCons(
          -17,
          IntCons(-16, IntCons(-15, IntCons(14, IntCons(13, IntCons(12, IntCons(11, IntCons(10, tenNumbers)))))))
        )
      )
    )

  test("fac"):
    assertEquals(fac(6), 6*5*4*3*2*1)

  test("fac2"):
    assertEquals(fac2(8),8*7*6*5*4*3*2*1)

  test("length: on multiple lists"):
    assertEquals(length(IntNil()), 0)
    assertEquals(length(threeNumbers), 3)
    assertEquals(length(tenNumbers), 10)
    assertEquals(length(twentyNumbers), 20)

  test("allPositiveOrZero: on multiple lists"):
    assertEquals(allPositiveOrZero(IntNil()), true)
    assertEquals(allPositiveOrZero(threeNumbers), true)
    assertEquals(allPositiveOrZero(tenNumbers), false)
    assertEquals(allPositiveOrZero(twentyNumbers), false)

  test("countPositive: on multiple lists"):
    assertEquals(countPositive(IntNil()), 0)
    assertEquals(countPositive(threeNumbers), 2)
    assertEquals(countPositive(tenNumbers), 4)
    assertEquals(countPositive(twentyNumbers), 9)

  test("decrement: on multiple lists"):
    assertEquals(decrement(IntNil()), IntNil())
    val threeRes =
      IntCons(1, IntCons(0, IntCons(-1, IntNil())))
    assertEquals(decrement(threeNumbers), threeRes)
    val tenRes =
      IntCons(-10, IntCons(-9, IntCons(-8, IntCons(-7, IntCons(-6, IntCons(3, IntCons(2, threeRes)))))))
    assertEquals(decrement(tenNumbers), tenRes)
    val twentyRes =
      IntCons(
        -20,
        IntCons(
          -19,
          IntCons(
            -18,
            IntCons(-17, IntCons(-16, IntCons(13, IntCons(12, IntCons(11, IntCons(10, IntCons(9, tenRes)))))))
          )
        )
      )
    assertEquals(decrement(twentyNumbers), twentyRes)

  test("collectEven: on multiple lists"):
    assertEquals(collectEven(IntNil()), IntNil())
    val threeRes =
      IntCons(2, IntCons(0, IntNil()))
    assertEquals(collectEven(threeNumbers), threeRes)
    val tenRes =
      IntCons(-8, IntCons(-6, IntCons(4, threeRes)))
    assertEquals(collectEven(tenNumbers), tenRes)
    val twentyRes =
      IntCons(-18, IntCons(-16, IntCons(14, IntCons(12, IntCons(10, tenRes)))))
    assertEquals(collectEven(twentyNumbers), twentyRes)

  test("min: on multiple lists"):
    intercept[IllegalArgumentException]:
      min(IntNil())
    assertEquals(min(threeNumbers), 0)
    assertEquals(min(tenNumbers), -9)
    assertEquals(min(twentyNumbers), -19)

  test("increment: multiple lists"):
    assertEquals(increment(IntNil()), IntNil())
    val threeRes =
      IntCons(3, IntCons(2, IntCons(1, IntNil())))
    assertEquals(increment(threeNumbers), threeRes)
    val tenRes =
      IntCons(-8, IntCons(-7, IntCons(-6, IntCons(-5, IntCons(-4, IntCons(5, IntCons(4, threeRes)))))))
    assertEquals(increment(tenNumbers), tenRes)
    val twentyRes =
      IntCons(
        -18,
        IntCons(
          -17,
          IntCons(
            -16,
            IntCons(-15, IntCons(-14, IntCons(15, IntCons(14, IntCons(13, IntCons(12, IntCons(11, tenRes)))))))
          )
        )
      )
    assertEquals(increment(twentyNumbers), twentyRes)

  test("subtract: on multiple lists"):
    intercept[IllegalArgumentException]:
      subtract(IntNil())
    assertEquals(subtract(threeNumbers), 1)
    assertEquals(subtract(tenNumbers), -9)
    assertEquals(subtract(twentyNumbers), -38)

  test("removeOdd: on multiple lists"):
    assertEquals(removeOdd(IntNil()), IntNil())
    val threeRes =
      IntCons(2, IntCons(0, IntNil()))
    assertEquals(removeOdd(threeNumbers), threeRes)
    val tenRes =
      IntCons(-8, IntCons(-6, IntCons(4, threeRes)))
    assertEquals(removeOdd(tenNumbers), tenRes)
    val twentyRes =
      IntCons(-18, IntCons(-16, IntCons(14, IntCons(12, IntCons(10, tenRes)))))
    assertEquals(removeOdd(twentyNumbers), twentyRes)

  test("countEven: on multiple list"):
    assertEquals(countEven(IntNil()), 0)
    assertEquals(countEven(threeNumbers), 2)
    assertEquals(countEven(tenNumbers), 5)
    assertEquals(countEven(twentyNumbers), 10)

  test("countEven2:  on multiple list"):
    assertEquals(countEven2(IntNil()), 0)
    assertEquals(countEven2(threeNumbers), 2)
    assertEquals(countEven2(tenNumbers), 5)
    assertEquals(countEven2(twentyNumbers), 10)

  test("multiplyBy2: on multiple lists"):
    assertEquals(multiplyBy2(IntNil()), IntNil())
    val threeRes =
      IntCons(4, IntCons(2, IntCons(0, IntNil())))
    assertEquals(multiplyBy2(threeNumbers), threeRes)
    val tenRes =
      IntCons(-18, IntCons(-16, IntCons(-14, IntCons(-12, IntCons(-10, IntCons(8, IntCons(6, threeRes)))))))
    assertEquals(multiplyBy2(tenNumbers), tenRes)
    val twentyRes =
      IntCons(
        -38,
        IntCons(
          -36,
          IntCons(
            -34,
            IntCons(-32, IntCons(-30, IntCons(28, IntCons(26, IntCons(24, IntCons(22, IntCons(20, tenRes)))))))
          )
        )
      )
    assertEquals(multiplyBy2(twentyNumbers), twentyRes)

  test("anyNegative: on multiple lists"):
    assert(!anyNegative(IntNil()))
    assert(!anyNegative(threeNumbers))
    assert(anyNegative(tenNumbers))
    assert(anyNegative(twentyNumbers))

  test("allEven: on multiple lists"):
    assert(allEven(IntNil()))
    assert(!allEven(threeNumbers))
    assert(!allEven(tenNumbers))
    assert(!allEven(twentyNumbers))

  test("multiplyOdd: on multiple lists"):
    // assertEquals(multiplyOdd(IntNil()), 0)
    assertEquals(multiplyOdd(threeNumbers), 1)
    assertEquals(multiplyOdd(tenNumbers), -945)

  test("horner: on multiple lists"):
    assertEquals(horner(2, IntNil()), 0)
    assertEquals(horner(2, threeNumbers), 4)
    assertEquals(horner(2, tenNumbers), 651)

  test("capAtZero: on multiple lists"):
    assertEquals(capAtZero(IntNil()), IntNil())
    val threeRes =
      IntCons(0, IntCons(0, IntCons(0, IntNil())))
    assertEquals(capAtZero(threeNumbers), threeRes)
    val tenRes =
      IntCons(-9, IntCons(-8, IntCons(-7, IntCons(-6, IntCons(-5, IntCons(0, IntCons(0, threeRes)))))))
    assertEquals(capAtZero(tenNumbers), tenRes)
    val twentyRes =
      IntCons(
        -19,
        IntCons(
          -18,
          IntCons(-17, IntCons(-16, IntCons(-15, IntCons(0, IntCons(0, IntCons(0, IntCons(0, IntCons(0, tenRes))))))))
        )
      )
    assertEquals(capAtZero(twentyNumbers), twentyRes)

  test("removeZeroes: on multiple lists"):
    assertEquals(removeZeroes(IntNil()), IntNil())
    val threeRes =
      IntCons(2, IntCons(1, IntNil()))
    assertEquals(removeZeroes(threeNumbers), threeRes)
    val tenRes =
      IntCons(-9, IntCons(-8, IntCons(-7, IntCons(-6, IntCons(-5, IntCons(4, IntCons(3, threeRes)))))))
    assertEquals(removeZeroes(tenNumbers), tenRes)
    val twentyRes =
      IntCons(
        -19,
        IntCons(
          -18,
          IntCons(
            -17,
            IntCons(-16, IntCons(-15, IntCons(14, IntCons(13, IntCons(12, IntCons(11, IntCons(10, tenRes)))))))
          )
        )
      )
    assertEquals(removeZeroes(twentyNumbers), twentyRes)

  test("reverseAppend: on multiple lists"):
    assertEquals(reverseAppend(IntNil(), IntNil()), IntNil())
    val tenRes = IntCons(
      0,
      IntCons(
        1,
        IntCons(2, IntCons(3, IntCons(4, IntCons(-5, IntCons(-6, IntCons(-7, IntCons(-8, IntCons(-9, IntNil()))))))))
      )
    )
    assertEquals(reverseAppend(tenNumbers, IntNil()), tenRes)
    assertEquals(reverseAppend(IntNil(), tenNumbers), tenNumbers)
    assertEquals(
      reverseAppend(threeNumbers, threeNumbers),
      IntCons(0, IntCons(1, IntCons(2, IntCons(2, IntCons(1, IntCons(0, IntNil()))))))
    )

  test("takeWhilePositive: empty list"):
    assertEquals(takeWhilePositive(IntNil()), IntNil())
    assertEquals(takeWhilePositive(threeNumbers), IntCons(2, IntCons(1, IntNil())))
    assertEquals(takeWhilePositive(tenNumbers), IntNil())

  test("append: empty lists"):
    assertEquals(append(IntNil(), IntNil()), IntNil())
    assertEquals(append(tenNumbers, IntNil()), tenNumbers)
    assertEquals(append(IntNil(), tenNumbers), tenNumbers)
    assertEquals(
      append(threeNumbers, threeNumbers),
      IntCons(2, IntCons(1, IntCons(0, IntCons(2, IntCons(1, IntCons(0, IntNil()))))))
    )

  // ---
  test("appendUsingReverseAppend: empty lists"):
    assertEquals(appendUsingReverseAppend(IntNil(), IntNil()), IntNil())
    assertEquals(appendUsingReverseAppend(tenNumbers, IntNil()), tenNumbers)
    assertEquals(appendUsingReverseAppend(IntNil(), tenNumbers), tenNumbers)
    assertEquals(
      appendUsingReverseAppend(threeNumbers, threeNumbers),
      IntCons(2, IntCons(1, IntCons(0, IntCons(2, IntCons(1, IntCons(0, IntNil()))))))
    )
  /*++++++*/

  test("collectMultiples: empty list"):
    assertEquals(collectMultiples(2, IntNil()), IntNil())
    val threeRes =
      IntCons(2, IntCons(0, IntNil()))
    assertEquals(collectMultiples(2, threeNumbers), threeRes)
    val tenRes =
      IntCons(-8, IntCons(-6, IntCons(4, threeRes)))
    assertEquals(collectMultiples(2, tenNumbers), tenRes)
    val twentyRes =
      IntCons(-18, IntCons(-16, IntCons(14, IntCons(12, IntCons(10, tenRes)))))
    assertEquals(collectMultiples(2, twentyNumbers), twentyRes)

  test("last: empty list"):
    intercept[IllegalArgumentException]:
      last(IntNil())
    assertEquals(last(threeNumbers), 0)
    assertEquals(last(tenNumbers), 0)
    assertEquals(last(twentyNumbers), 0)

  test("init: empty list"):
    intercept[IllegalArgumentException]:
      init(IntNil())

    val threeRes =
      IntCons(2, IntCons(1, IntNil()))
    assertEquals(init(threeNumbers), threeRes)
    val tenRes =
      IntCons(-9, IntCons(-8, IntCons(-7, IntCons(-6, IntCons(-5, IntCons(4, IntCons(3, threeRes)))))))
    assertEquals(init(tenNumbers), tenRes)
    val twentyRes =
      IntCons(
        -19,
        IntCons(
          -18,
          IntCons(
            -17,
            IntCons(-16, IntCons(-15, IntCons(14, IntCons(13, IntCons(12, IntCons(11, IntCons(10, tenRes)))))))
          )
        )
      )
    assertEquals(init(twentyNumbers), twentyRes)

  test("contains: empty list"):
    assert(!contains(IntNil(), 2))
    assert(contains(threeNumbers, 2))
    assert(!contains(threeNumbers, 5))
    assert(contains(tenNumbers, 0))
    assert(!contains(tenNumbers, 100))
    assert(contains(twentyNumbers, -19))
    assert(!contains(twentyNumbers, -55))

  test("isSubset: empty list"):
    assert(isSubset(IntNil(), threeNumbers))
    assert(isSubset(threeNumbers, tenNumbers))
    assert(isSubset(tenNumbers, twentyNumbers))
    assert(!isSubset(threeNumbers, IntNil()))
    assert(!isSubset(tenNumbers, threeNumbers))
    assert(!isSubset(twentyNumbers, tenNumbers))

  test("intersection: empty list"):
    assertEquals(intersection(IntNil(), twentyNumbers), IntNil())
    assertEquals(intersection(threeNumbers, threeNumbers), threeNumbers)
    assertEquals(intersection(threeNumbers, tenNumbers), threeNumbers)
    assertEquals(intersection(threeNumbers, twentyNumbers), threeNumbers)
    assertEquals(intersection(tenNumbers, twentyNumbers), tenNumbers)

  test("difference: empty list"):
    assertEquals(difference(IntNil(), twentyNumbers), IntNil())
    assertEquals(difference(threeNumbers, threeNumbers), IntNil())
    assertEquals(difference(threeNumbers, tenNumbers), IntNil())
    assertEquals(
      difference(tenNumbers, threeNumbers),
      IntCons(-9, IntCons(-8, IntCons(-7, IntCons(-6, IntCons(-5, IntCons(4, IntCons(3, IntNil())))))))
    )
    assertEquals(
      difference(twentyNumbers, tenNumbers),
      IntCons(
        -19,
        IntCons(
          -18,
          IntCons(
            -17,
            IntCons(-16, IntCons(-15, IntCons(14, IntCons(13, IntCons(12, IntCons(11, IntCons(10, IntNil())))))))
          )
        )
      )
    )

  test("minMax: on multiple lists"):
    intercept[IllegalArgumentException]:
      minMax(IntNil())
    assertEquals(minMax(threeNumbers), (0, 2))
    assertEquals(minMax(tenNumbers), (-9, 4))
    assertEquals(minMax(twentyNumbers), (-19, 14))

  test("polishEval: addition"):
    val xs = IntCons(Add, IntCons(1, IntCons(2, IntNil())))
    assertEquals(polishEval(xs)._1, 3)

  test("polishEval: nested operations"):
    val xs = IntCons(Add, IntCons(2, IntCons(Multiply, IntCons(3, IntCons(4, IntNil())))))
    assertEquals(polishEval(xs)._1, 14)

  test("polishEval: on empty list"):
    intercept[EmptyListException]:
      polishEval(IntNil())
    intercept[EmptyListException]:
      polishEval(IntCons(Add, IntCons(2, IntNil())))

  test("polishEval: on invalid input"):
    intercept[InvalidOperatorException]:
      polishEval(IntCons(Add, IntCons(2, IntCons(-18, IntCons(3, IntCons(4, IntNil()))))))

class ListOpsBiggerTest extends munit.FunSuite:
  val MAX_LENGTH = 100

  def positiveNumbers(to: Int, from: Int = 1): IntList =
    if from > to then IntNil()
    else IntCons(from, positiveNumbers(to, from + 1))
  def reversePositiveNumbers(to: Int, from: Int = 1): IntList =
    if from > to then IntNil()
    else IntCons(to - from + 1, reversePositiveNumbers(to, from + 1))
  def negativeNumbers(to: Int, from: Int = 1): IntList =
    if from > to then IntNil()
    else IntCons(-from, negativeNumbers(to, from + 1))
  def mixedNumbers(to: Int, from: Int = 1): IntList =
    if from > to then IntNil()
    else IntCons(if from % 2 == 0 then -from else from, mixedNumbers(to, from + 1))
  def evenNumbers(to: Int, from: Int = 1): IntList =
    if from > to then IntNil()
    else IntCons(from * 2, evenNumbers(to, from + 1))

  test(f"length: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(length(positiveNumbers(i)), i)

  test(f"allPositiveOrZero: list 1 to $MAX_LENGTH elements, all positive"):
    for
      i <- 1 to MAX_LENGTH
    do
      assert(allPositiveOrZero(positiveNumbers(i)))

  test(f"allPositiveOrZero: list 2 to $MAX_LENGTH elements, some negative, some positive"):
    for
      i <- 2 to MAX_LENGTH
    do
      assert(!allPositiveOrZero(mixedNumbers(i)))

  test(f"countPositive: list with 1 to $MAX_LENGTH elements, all positive"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(countPositive(positiveNumbers(i)), i)

  test(f"countPositive: list with 1 to $MAX_LENGTH elements, all negative"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(countPositive(negativeNumbers(i)), 0)

  test("sum: empty list"):
    assertEquals(sum(IntNil()), 0)

  test(f"sum: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(sum(positiveNumbers(i)), (1 to i).sum)

  test("product: empty list"):
    assertEquals(product(IntNil()), 1)

  test(f"product: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(product(positiveNumbers(i)), (1 to i).product)

  test("anyOdd: empty list"):
    assertEquals(anyOdd(IntNil()), false)

  test(f"anyOdd: list with 1 to $MAX_LENGTH elements, odds and evens"):
    for
      i <- 1 to MAX_LENGTH
    do
      assert(anyOdd(positiveNumbers(i)))

  test(f"anyOdd: list with 1 to $MAX_LENGTH elements, only evens"):
    for
      i <- 1 to MAX_LENGTH
    do
      assert(!anyOdd(evenNumbers(i)))

  test(f"min: list with 1 to $MAX_LENGTH elements, positive"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(min(positiveNumbers(i)), 1)

  test(f"min: list with 1 to $MAX_LENGTH elements, negative"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(min(negativeNumbers(i)), -i)

  test(f"subtract: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(subtract(positiveNumbers(i)), (1 to i by 2).sum - (2 to i by 2).sum)

  test(f"countEven: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(countEven(positiveNumbers(i)), i / 2)

  test(f"countEven2: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(countEven2(positiveNumbers(i)), i / 2)

  test(f"anyNegative: list with 1 to $MAX_LENGTH elements, all positive"):
    for
      i <- 1 to MAX_LENGTH
    do
      assert(!anyNegative(positiveNumbers(i)))

  test(f"anyNegative: list with 2 to $MAX_LENGTH elements, some positive, some negative"):
    for
      i <- 2 to MAX_LENGTH
    do
      assert(anyNegative(mixedNumbers(i)))

  test(f"allEven: list with 1 to $MAX_LENGTH elements, not all even"):
    for
      i <- 1 to MAX_LENGTH
    do
      assert(!allEven(positiveNumbers(i)))

  test(f"allEven: list with 1 to $MAX_LENGTH elements, all even"):
    for
      i <- 1 to MAX_LENGTH
    do
      assert(allEven(evenNumbers(i)))

  test(f"multiplyOdd: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(multiplyOdd(positiveNumbers(i)), (1 to i by 2).product)

  test(f"capAtZero: list with 1 to $MAX_LENGTH elements, negative"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(capAtZero(negativeNumbers(i)), negativeNumbers(i))

  test(f"reverse: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(reverse(positiveNumbers(i)), reversePositiveNumbers(i))

  test(f"takeWhilePositive: list with 1 to $MAX_LENGTH elements, positive"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(takeWhilePositive(positiveNumbers(i)), positiveNumbers(i))

  test(f"takeWhilePositive: list with 1 to $MAX_LENGTH elements, positive and negative"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(takeWhilePositive(mixedNumbers(i)), IntCons(1, IntNil()))

  test(f"last: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(last(positiveNumbers(i)), i)

  test(f"minMax: list with 1 to $MAX_LENGTH elements"):
    for
      i <- 1 to MAX_LENGTH
    do
      assertEquals(minMax(positiveNumbers(i)), (1, i))
