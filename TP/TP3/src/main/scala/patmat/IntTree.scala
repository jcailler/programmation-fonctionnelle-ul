package patmat

enum IntTree:
  case Leaf(value: Int)
  case Branch(left: IntTree, right: IntTree)
