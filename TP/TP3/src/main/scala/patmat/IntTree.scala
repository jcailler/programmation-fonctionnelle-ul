package patmat

import util.Random

enum IntTree:
  case Leaf(value: Int)
  case Branch(left: IntTree, right: IntTree)

  def size: Int = this match
    case Leaf(_)             => 1
    case Branch(left, right) => left.size + right.size

  def sum:Int = this match
    case Leaf(v)             => v
    case Branch(left, right) => left.size + right.size

object IntTree:
  case class TreeConfig(tree: IntTree, reducer: ((Int, Int) => Int) => Int, mapper: (Int => Int) => IntTree)
  object TreeConfig:
    def Leaf(value: Int): TreeConfig = TreeConfig(IntTree.Leaf(value), _ => value, op => IntTree.Leaf(op(value)))
    def Branch(left: TreeConfig, right: TreeConfig): TreeConfig =
      TreeConfig(
        IntTree.Branch(left.tree, right.tree),
        op => op(left.reducer(op), right.reducer(op)),
        op => IntTree.Branch(left.mapper(op), right.mapper(op))
      )

  def generate(maxDepth: Int): TreeConfig =
    def recur(nowDepth: Int): TreeConfig =
      if nowDepth >= maxDepth then TreeConfig.Leaf(Random.between(-10, 10))
      else
        Random.between(0, 2) match
          case 0 => TreeConfig.Leaf(Random.between(-10, 10))
          case 1 => TreeConfig.Branch(recur(nowDepth + 1), recur(nowDepth + 1))
    recur(1)
