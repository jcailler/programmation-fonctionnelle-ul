import patmat.*
import TriBool.*
import TriBoolOps.*

class TriBoolOpsTest extends munit.FunSuite:
  test("neg") {
    assertEquals(neg(Yes), No)
    assertEquals(neg(No), Yes)
    assertEquals(neg(Maybe), Maybe)
  }

  test("and") {
    assertEquals(and(Yes, Yes), Yes)
    assertEquals(and(Yes, No), No)
    assertEquals(and(Yes, Maybe), Maybe)
    assertEquals(and(No, Yes), No)
    assertEquals(and(No, No), No)
    assertEquals(and(No, Maybe), No)
    assertEquals(and(Maybe, Yes), Maybe)
    assertEquals(and(Maybe, No), No)
    assertEquals(and(Maybe, Maybe), Maybe)
  }

  test("or") {
    assertEquals(or(Yes, Yes), Yes)
    assertEquals(or(Yes, No), Yes)
    assertEquals(or(Yes, Maybe), Yes)
    assertEquals(or(No, Yes), Yes)
    assertEquals(or(No, No), No)
    assertEquals(or(No, Maybe), Maybe)
    assertEquals(or(Maybe, Yes), Yes)
    assertEquals(or(Maybe, No), Maybe)
    assertEquals(or(Maybe, Maybe), Maybe)
  }

  test("nand") {
    assertEquals(nand(Yes, Yes), No)
    assertEquals(nand(Yes, No), Yes)
    assertEquals(nand(Yes, Maybe), Maybe)
    assertEquals(nand(No, Yes), Yes)
    assertEquals(nand(No, No), Yes)
    assertEquals(nand(No, Maybe), Yes)
    assertEquals(nand(Maybe, Yes), Maybe)
    assertEquals(nand(Maybe, No), Yes)
    assertEquals(nand(Maybe, Maybe), Maybe)
  }
