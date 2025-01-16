import patmat.*
import IntList.*
import IntIntList.*

abstract class IntListOpsTestBase extends munit.FunSuite:
  test("extractSecond: empty"):
    assertEquals(extractSecond(IntNil), ExtractResult.EmptyList)

  test("extractSecond: not long enough"):
    assertEquals(extractSecond(IntCons(1, IntNil)), ExtractResult.NotLongEnough)

  test("extractSecond: good"):
    assertEquals(extractSecond(IntCons(1, IntCons(2, IntNil))), ExtractResult.SecondElem(2))

  test("unzip: empty"):
    assertEquals(unzip(IntIntNil), (IntNil, IntNil))

  test("unzip: non-empty"):
    assertEquals(
      unzip(IntIntCons((1, 2), IntIntCons((2, 3), IntIntNil))),
      (IntCons(1, IntCons(2, IntNil)), IntCons(2, IntCons(3, IntNil)))
    )

  test("movingWindow: empty"):
    assertEquals(movingWindow(IntNil), IntIntNil)

  test("movingWindow: non-empty"):
    assertEquals(
      movingWindow(IntCons(1, IntCons(2, IntCons(3, IntNil)))),
      IntIntCons((1, 2), IntIntCons((2, 3), IntIntNil))
    )
