import patmat.*
import PolishNotation.*
import PolishNotationAtom.*

class PolishNotationTests extends munit.FunSuite:

  test("polishEval: addition"):
    assertEquals(polishEval(plusOneTwo)._1, 3)

  test("polishEval: nested operations"):
    assertEquals(polishEval(plusTwoTimesThreeFour)._1, 14)
