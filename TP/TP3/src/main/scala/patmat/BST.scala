package patmat

enum BSTContext:
  case Leaf
  case Branch(k: String, v: Int, l: BSTContext, r: BSTContext)

  def isLeaf: Boolean = this match
    case Leaf      => true
    case _: Branch => false

  def key: String = this match
    case Leaf               => throw RuntimeException("key of a leaf")
    case Branch(k, v, l, r) => k

  def value: Int = this match
    case Leaf               => throw RuntimeException("value of a leaf")
    case Branch(k, v, l, r) => v

  def left: BSTContext = this match
    case Leaf               => throw RuntimeException("left of a leaf")
    case Branch(k, v, l, r) => l

  def right: BSTContext = this match
    case Leaf               => throw RuntimeException("right of a leaf")
    case Branch(k, v, l, r) => r
