package recursion

final class EmptyTreeException extends Exception

abstract class IntTree:
  def isEmpty: Boolean
  def value: Int
  def left: IntTree
  def right: IntTree

final case class IntEmptyTree() extends IntTree:
  def isEmpty: Boolean = true
  def value: Int = throw EmptyTreeException()
  def left: IntTree = throw EmptyTreeException()
  def right: IntTree = throw EmptyTreeException()

final case class IntBranch(value: Int, left: IntTree, right: IntTree) extends IntTree:
  def isEmpty: Boolean = false
