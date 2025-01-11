package recursion

class TreeOpsTests extends munit.FunSuite:
  val emptyTree: IntTree = IntEmptyTree()
  val sampleTree: IntTree =
    IntBranch(1, IntBranch(2, IntEmptyTree(), IntEmptyTree()), IntBranch(3, IntEmptyTree(), IntEmptyTree()))

  test("treeSize: empty tree"):
    assertEquals(treeSize(emptyTree), 0)

  test("treeSize: sample tree"):
    assertEquals(treeSize(sampleTree), 3)

  test("treeDepth: empty tree"):
    assertEquals(treeDepth(emptyTree), 0)

  test("treeDepth: sample tree"):
    assertEquals(treeDepth(sampleTree), 2)

  test("treeSum: empty tree"):
    assertEquals(treeSum(emptyTree), 0)

  test("treeSum: sample tree"):
    assertEquals(treeSum(sampleTree), 6)

  test("treeAllEven: empty tree"):
    assertEquals(treeAllEven(emptyTree), true)

  test("treeAllEven: sample tree"):
    assertEquals(treeAllEven(sampleTree), false)

  test("treeIncrement: empty tree"):
    assertTreeEquals(treeIncrement(emptyTree), IntEmptyTree())

  test("treeIncrement: sample tree"):
    assertTreeEquals(
      treeIncrement(sampleTree),
      IntBranch(2, IntBranch(3, IntEmptyTree(), IntEmptyTree()), IntBranch(4, IntEmptyTree(), IntEmptyTree()))
    )

  test("treeShow: empty tree"):
    assertEquals(treeShow(emptyTree), "")

  test("treeShow: sample tree"):
    assertEquals(treeShow(sampleTree), "1\n2\n3\n")

  test("treeShowPostOrder: sample tree"):
    assertEquals(treeShowPostOrder(sampleTree), "2\n3\n1\n")

  def assertTreeEquals(tree1: IntTree, tree2: IntTree): Unit =
    if tree1.isEmpty && tree2.isEmpty then
      // Both trees are empty, so they are equal.
      return

    if tree1.isEmpty || tree2.isEmpty then
      // One of the trees is empty and the other is not, so they are not equal.
      fail("One of the trees has more nodes than the other.")

    if tree1.value != tree2.value then
      // The current values in both trees are different.
      fail(s"Tree values differ. Expected: ${tree1.value}, but found: ${tree2.value}.")

    // Continue the comparison with the left and right subtrees.
    assertTreeEquals(tree1.left, tree2.left)
    assertTreeEquals(tree1.right, tree2.right)
