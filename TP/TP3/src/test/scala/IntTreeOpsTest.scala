import patmat.*
import IntTreeOps.*
import IntTree.*

class IntTreeOpsTest extends munit.FunSuite:
  test("treeMap: identity") {
    (0 until 100).foreach: _ =>
      val TreeConfig(tree, _, _) = generate(maxDepth = 10)
      val tree1 = treeMap(tree, 0)
      assertEquals(tree, tree1)
  }

  test("treeMap: plus one") {
    (0 until 100).foreach: _ =>
      val TreeConfig(tree, reducer, mapper) = generate(maxDepth = 10)
      val tree1 = treeMap(tree, 1)
      assertEquals(tree1, mapper(x => x + 1))
  }

  test("treeMap: plus one"):
      val t = IntTree.Branch(IntTree.Branch(IntTree.Leaf(1), IntTree.Leaf(2)), IntTree.Leaf(3))
      assertEquals(treeMap(t, 1), IntTree.Branch(IntTree.Branch(IntTree.Leaf(2), IntTree.Leaf(3)), IntTree.Leaf(4)))
  
  test("treeReduce"):
      val t = IntTree.Branch(IntTree.Branch(IntTree.Leaf(1), IntTree.Leaf(2)), IntTree.Leaf(3))
      assertEquals(treeReduce(t), 6)