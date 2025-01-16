import patmat.*
import EnumContext.*
import LookupResult.*

class EnumContextTest extends munit.FunSuite:
  test("lookup empty should yield `NotFound`") {
    assertEquals(lookup(empty, "a"), NotFound)
    assertEquals(lookup(empty, "x"), NotFound)
    assertEquals(lookup(empty, "123"), NotFound)
    assertEquals(lookup(empty, "000"), NotFound)
  }

  test("lookup should work for the context cons(\"a\", 1, cons(\"b\", 2, cons(\"c\", 3, empty)))") {
    val ctx = cons("a", 1, cons("b", 2, cons("c", 3, empty)))
    assertEquals(lookup(ctx, "a"), Ok(1))
    assertEquals(lookup(ctx, "b"), Ok(2))
    assertEquals(lookup(ctx, "c"), Ok(3))
    assertEquals(lookup(ctx, "d"), NotFound)
    assertEquals(lookup(ctx, "e"), NotFound)
  }

  test("lookup should work for cons(\"a\", 1, cons(\"a\", 2, cons(\"b\", 3, empty)))") {
    val ctx = cons("a", 1, cons("a", 2, cons("b", 3, empty)))
    assertEquals(lookup(ctx, "a"), Ok(1))
    assertEquals(lookup(ctx, "b"), Ok(3))
    assertEquals(lookup(ctx, "c"), NotFound)
  }

  test("erase: empty") {
    val ctx = empty
    assertEquals(erase(ctx, "a"), empty)
    assertEquals(erase(ctx, "b"), empty)
  }

  test("erase: singleton") {
    val ctx = cons("a", 1, empty)
    assertEquals(erase(ctx, "a"), empty)
    assertEquals(erase(ctx, "b"), ctx)
  }

  test("erase: length-2 context") {
    val ctx = cons("a", 1, cons("b", 2, empty))
    assertEquals(erase(ctx, "a"), cons("b", 2, empty))
    assertEquals(erase(ctx, "b"), cons("a", 1, empty))
    assertEquals(erase(ctx, "c"), ctx)
  }

  test("erase: duplicate names") {
    val ctx = cons("a", 1, cons("b", 2, cons("a", 3, empty)))
    assertEquals(erase(ctx, "a"), cons("b", 2, empty))
    assertEquals(erase(ctx, "b"), cons("a", 1, cons("a", 3, empty)))
    assertEquals(erase(ctx, "c"), ctx)
  }
