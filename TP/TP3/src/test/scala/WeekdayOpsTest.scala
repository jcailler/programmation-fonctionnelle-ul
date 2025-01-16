import patmat.*
import WeekdayOps.*
import Weekday.*

class WeekdayOpsTest extends munit.FunSuite:
  test("next") {
    assertEquals(next(Monday), Tuesday)
    assertEquals(next(Tuesday), Wednesday)
    assertEquals(next(Wednesday), Thursday)
    assertEquals(next(Thursday), Friday)
    assertEquals(next(Friday), Saturday)
    assertEquals(next(Saturday), Sunday)
    assertEquals(next(Sunday), Monday)
  }

  test("prev") {
    assertEquals(prev(Monday), Sunday)
    assertEquals(prev(Tuesday), Monday)
    assertEquals(prev(Wednesday), Tuesday)
    assertEquals(prev(Thursday), Wednesday)
    assertEquals(prev(Friday), Thursday)
    assertEquals(prev(Saturday), Friday)
    assertEquals(prev(Sunday), Saturday)
  }
