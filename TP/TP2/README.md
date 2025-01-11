# Functional Programming - TP 2: Recursion

In this exercise you will learn about structural recursion in scala.

## Structural Recursion
* We provide the signature of the functions you need to write in the exercise files. The body is replaced with `???`: that’s the part you need to come up with!
* The exercise files are found in the folder `TP2/src/main/scala/recursion`. The exercises are those files which start with `Ex` (for exercise), sorted by the type of structure you will work on. The files `IntList.scala` and `IntTree.scala` should **stay unchanged**. They define the necessary classes you will work on in the exercises.
* You will perform recursion on different structures like lists, trees, etc. The sufficient background to solve the exercises is given below.
* The number of exercises is quite large, the exercises are provided for training purposes. Feel free to skip some of the simpler ones if you already have a good grasp on recursion and Scala programming!
* For each function we provided a test. As for the first exercise you can run the tests by entering `sbt` while you are in your project directory, and then compiling your code using `compile`. To run all the tests use `test`. If you only want to run the test of a specific function use `testOnly -- "*function*"`, where function is the name of the function you want to test.

For example you might find the following signature of the factorial function in the file [`ExLists.scala`](./src/main/scala/recursion/ExLists.scala):
```scala
// As a first step implement the factorial function n!=n*(n-1)*...*2*1
def fac(n:Int) : Int = {
  ??? 
}
```
After you replaced the `???` by the correct code you might run `compile` and `testOnly -- "*fac*"`. In this case this will test if `fac(6)==6 * 5 * 4 * 3 * 2 * 1`. In particular a correct test does not necessarily mean that you implemented the correct function (you might also pass the test by replacing `???` by `720`).

You can go on implementing the other functions in this file. The necessary background on Lists is given below.

Note that the syntax of scala does not require to specify the return type of the function, but for your convenience we already gave it.
Furthermore the curly braces are not necessary to mark the body of the function if you have the right spacing.

## Recursion on Lists

In this exercise, you will write recursive functions that operate on lists. These lists are represented as instances of the `IntList` class. An `IntList` can be either:

* `IntNil()`, which represents an empty list,
* `IntCons(head, tail)`, which represents a non-empty list starting with `head` (an integer) and continuing with `tail` (a list of integers).

Hence, the list `1, 2` is represented by `IntCons(1, IntCons(2, IntNil()))`.

`IntList` objects have three operations — `.head`, `.tail`, and `.isEmpty` — demonstrated in the following REPL session:
```scala
scala> import recursion.*

scala> IntCons(2, IntNil()).head
val res0: Int = 2

scala> IntCons(2, IntNil()).tail
val res1: recursion.IntList = IntNil()

scala> val oneTwo = IntCons(1, IntCons(2, IntNil()))
val oneTwo: recursion.IntCons = IntCons(1,IntCons(2,IntNil()))

scala> oneTwo.isEmpty
val res2: Boolean = false

scala> oneTwo.head
val res3: Int = 1

scala> oneTwo.tail
val res4: recursion.IntList = IntCons(2,IntNil())

scala> oneTwo.tail.isEmpty
val res5: Boolean = false

scala> oneTwo.tail.head
val res6: Int = 2

scala> oneTwo.tail.tail
val res7: recursion.IntList = IntNil()

scala> oneTwo.tail.tail.isEmpty
val res8: Boolean = true
```

## Polish Notation
 
The last exercise on recursion on lists is a bit more advanced that the previous ones.

Polish Notation (PN) is a way to write arithmetic expressions where every operator comes before its operands. For example, instead of writing `3 + 4`, you would write `+ 3 4`.

Your task is to implement a PN evaluator for an `IntList` where operators are represented by negative integers: we’ll deal with just addition and multiplication for now:

```scala
val Add = -1
val Multiply = -2
```

For example, the expression `3 + 4` would be represented as `IntCons(Add, IntCons(1, IntCons(2, IntNil())))` and the expression `3 * 4` would be represented as `IntCons(Mul, IntCons(1, IntCons(2, IntNil())))`.

Here is a REPL session demonstrating how the polishEval function should behave:

```scala
scala> // List:   [Add, 3, 4]
scala> // Prefix: + 3 4
scala> // Infix:  3 + 4
scala> val expr1 = IntCons(Add, IntCons(3, IntCons(4, IntNil())))
val expr1: recursion.IntCons = IntCons(-1,IntCons(3,IntCons(4,IntNil())))

scala> polishEval(expr1)
val res40: (Int, recursion.IntList) = (7,IntNil())

scala> // List:   [Multiply, Add, 5, 3, 4]
scala> // Prefix: * + 5 3 4
scala> // Infix:  (5 + 3) * 4
scala> val expr2 = IntCons(Multiply, IntCons(Add, IntCons(5, IntCons(3, IntCons(4, IntNil())))))
val expr2: recursion.IntCons = IntCons(-2,IntCons(-1,IntCons(5,IntCons(3,IntCons(4,IntNil())))))

scala> polishEval(expr2)._1
val res41: Int = 32
```

`polishEval` is supposed to return a pair which consists of an Integer, which will be our result and an IntList which is necessary for the recursive calls.

You are now prepared to solve the exercises in the file  [`ExLists.scala`](./src/main/scala/recursion/ExLists.scala) and implement the corresponding functions.

## Lists as Sets
Lists are ordered collections of objects; as a result, most set operations can be defined on them too. There is no additional explanation needed to solve the corresponding exercises.  

You are now prepared to solve the exercises in the file [`ExListsAsSets.scala`](./src/main/scala/recursion/ExListsAsSets.scala) and implement the corresponding functions.

## Recursion on Trees
Now, we’ll explore recursion on binary trees. A binary tree is a tree where each node has at most two children. An `IntTree` can be either:

+ `IntEmptyTree()`, which represents an empty tree,
+ `IntBranch( value , left , right)`: which represents a node with value `value` (an integer) and two children `left` and `right` (trees of integers).

Hence, a tree with two non-empty nodes, a root node with value `1` with a left child with value `2`, is represented by `IntBranch(1, IntBranch(2, IntEmptyTree(), IntEmptyTree()), IntEmptyTree())`.

IntTree objects have four operations — `.value`, `.left`, `.right` and `.isEmpty` — demonstrated in the following REPL session:

```scala

scala> import recursion.*

scala> IntBranch(2, IntEmptyTree(), IntEmptyTree()).value
val res11: Int = 2

scala> IntBranch(2, IntEmptyTree(), IntEmptyTree()).left
val res12: recursion.IntTree = IntEmptyTree()

scala> IntBranch(2, IntEmptyTree(), IntEmptyTree()).right
val res13: recursion.IntTree = IntEmptyTree()

scala> val tree = IntBranch(1, IntBranch(2, IntEmptyTree(), IntEmptyTree()), IntEmptyTree())
val tree: recursion.IntBranch = IntBranch(1,IntBranch(2,IntEmptyTree(),IntEmptyTree()),IntEmptyTree())

scala> tree.isEmpty
val res14: Boolean = false

scala> tree.value
val res15: Int = 1

scala> tree.left
val res16: recursion.IntTree = IntBranch(2,IntEmptyTree(),IntEmptyTree())

scala> tree.left.isEmpty
val res17: Boolean = false

scala> tree.left.value
val res18: Int = 2

scala> tree.left.left
val res19: recursion.IntTree = IntEmptyTree()

scala> tree.left.right
val res20: recursion.IntTree = IntEmptyTree()

scala> tree.right
val res21: recursion.IntTree = IntEmptyTree()

scala> tree.right.isEmpty
val res22: Boolean = true
```

You are now prepared to solve the exercises in the file [`ExTrees.scala`](./src/main/scala/recursion/ExTrees.scala) and implement the corresponding functions.

### Recursion on Strings

In this exercise, you will write recursive functions that operate on strings. A `String` can be thought of as a list of characters. Like lists, we can apply the same structural recursion principles to strings. Each string has the `isEmpty`, `head` and `tail` methods similar to `IntList`:

```scala
scala> "Hello Ada".tail
val res25: String = ello Ada

scala> "Hello Ada".tail.head
val res26: Char = e

scala> "Hello Ada".tail.isEmpty
val res27: Boolean = false

scala> " ".isEmpty
val res28: Boolean = false

scala> "".isEmpty
val res29: Boolean = true
```

Note the type of the `head` method: it returns a `Char`, not a `String`. A `Char` is a single character, whereas a `String` is a sequence of characters. A `String` is surrounded by double quotes (e.g. "This is a String"), whereas a `Char` is surrounded by single quotes (e.g. 'c').

For the respective exercises, you must not use any other method than `isEmpty`, `head` and `tail` on strings.

You are now prepared to solve the exercises in the file [`ExStrings.scala`](./src/main/scala/recursion/ExStrings.scala) and implement the corresponding functions.
