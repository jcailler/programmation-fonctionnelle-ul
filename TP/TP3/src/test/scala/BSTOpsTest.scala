import patmat.*

import BSTContext.*
import BSTOps.*
import EnumContext.LookupResult.*

class BSTOpsTest extends munit.FunSuite:
  test("find: singleton tree"):
    assertEquals(lookup(Branch("a", 0, Leaf, Leaf), "a"), Ok(0))
    assertEquals(lookup(Branch("a", 0, Leaf, Leaf), "b"), NotFound)

  test("find: simple tree"):
    val tree = Branch(
      "b",
      0,
      Branch("a", -1, Leaf, Leaf),
      Branch("d", 2, Branch("c", 1, Leaf, Leaf), Branch("e", 3, Leaf, Leaf))
    )
    assertEquals(lookup(tree, "a"), Ok(-1))
    assertEquals(lookup(tree, "b"), Ok(0))
    assertEquals(lookup(tree, "c"), Ok(1))
    assertEquals(lookup(tree, "d"), Ok(2))
    assertEquals(lookup(tree, "e"), Ok(3))
    assertEquals(lookup(tree, "f"), NotFound)
    assertEquals(lookup(tree, "g"), NotFound)

  def mkSingle(k: String, v: Int) =
    Branch(k, v, Leaf, Leaf)

  test("insert: singleton tree"):
    val tree = Branch("b", 0, Leaf, Leaf)
    assertEquals(insert(tree, "c", 1), Branch("b", 0, Leaf, Branch("c", 1, Leaf, Leaf)))
    assertEquals(insert(tree, "a", 1), Branch("b", 0, Branch("a", 1, Leaf, Leaf), Leaf))
    assertEquals(insert(tree, "b", 1), Branch("b", 1, Leaf, Leaf))

  val singleton = mkSingle("x", 0)
  val rightLeaning = Branch("a", 0, mkSingle("l", 0), Branch("b", 0, mkSingle("rl", 1), mkSingle("rr", 2)))
  val leftLeaning = Branch("b", 0, Branch("a", 0, mkSingle("l", 0), mkSingle("rl", 1)), mkSingle("rr", 2))

  test("rotateLeft: singleton tree"):
    assertEquals(rotateLeft(singleton), singleton)

  test("rotateLeft: right-leaning tree"):
    assertEquals(rotateLeft(rightLeaning), leftLeaning)

  test("rotateRight: singleton tree"):
    assertEquals(rotateRight(singleton), singleton)

  test("rotateRight: left-leaning tree"):
    assertEquals(rotateRight(leftLeaning), rightLeaning)
