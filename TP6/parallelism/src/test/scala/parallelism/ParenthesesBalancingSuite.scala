package parallelism

import parallelism.ParenthesesBalancing.*
import scala.language.experimental

class ParenthesesBalancingSuite extends munit.FunSuite:
  val testCasesRaw = Seq(
    "(((())))" -> true,
    "((()))" -> true,
    "(((()))" -> false,
    "((())))" -> false,
    "(o_()" -> false,
    ":-)" -> false,
    "())(" -> false,
    "(if (zero? x) max (/ 1 x))" -> true,
    "I told him (that it's not (yet) done). (But he wasn't listening)" -> true
  )

  val testCases = testCasesRaw.map((s, b) => s.toList -> b)

  // test only recursive
  testCases.foreach(testFunction(isBalancedRecursive, "recursive"))

  // test only fold
  testCases.foreach(testFunction(isBalancedFold, "fold"))

  // test only par
  testCases.foreach(testFunction(isBalancedPar, "parallel"))

  def testFunction(
      f: List[Char] => Boolean,
      msg: String
  ): ((List[Char], Boolean)) => Unit =
    (str, expected) =>
      test(
        s"String $str should${if expected then "" else " not"} be balanced --- $msg"
      ) {
        assertEquals(f(str), expected)
      }
