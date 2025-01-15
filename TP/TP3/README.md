# Functional Programming - TP 3: Pattern Matching

This exercise set is intended to help you practice pattern matching.

## Introduction to Pattern Matching

In previous exercises, we used `if` conditionals and `.` field accessors to write functions on data types such as lists or trees. This week, we'll use pattern matching to make these functions more succinct and readable. We'll move from this:

```scala
def reduceIf(f: (Int, Int) => Int)(l: IntList): Int =
  if l.isEmpty then throw IllegalArgumentException("Empty list!")
  else if l.tail.isEmpty then l.head
  else f(l.head, reduceIf(f)(l.tail))
```

to this:

```scala
def reduceMatch(f: (Int, Int) => Int)(l: IntList): Int =
  l match
    case IntNil              => throw IllegalArgumentException("Empty list!")
    case IntCons(hd, IntNil) => hd
    case IntCons(hd, tl)     => f(hd, reduceMatch(f)(tl))
```

Most functional programmers find the second one much more readable, because it aligns the way the data is *destructed* (taken apart into a head and a tail) and the way the data is *constructed* (assembled from a head and a tail):

> [!NOTE]
> in this function, the `f` is a higher-order argument. We will discuss them in detail later.

```scala
def constructDestruct =
  IntCons(1, IntCons(2, IntNil)) match
    case IntCons(a, IntCons(b, IntNil)) =>
      println(s"Found $a and $b")
    case _ => throw Exception("Not possible!")
```

> [!WARNING] 
> Previously, we wrote `IntNil()` for empty `IntList`s. Now that we know about `enum`s and case classes, we can use the more succinct and convenient syntax `IntNil` (no parentheses).

<details>
<summary> Hint </summary>

If you find yourself looking for more examples of pattern matching after completing this set, consider revisiting week 1 and week 2 exercises and redefining all the functions with pattern matching.
</details>


## Weekdays ([Weekday.scala](./src/main/scala/patmat/Weekday.scala) & [WeekdayOps.scala](./src/main/scala/patmat/WeekdayOps.scala))

Days of the week are a great example of simple enumerations. So, which day is tomorrow? let's implement a function to find out.

The `enum` representing weekdays is defined as follows (in [Weekday.scala](./src/main/scala/patmat/Weekday.scala)):

```scala
enum Weekday:
  case Monday
  case Tuesday
  case Wednesday
  case Thursday
  case Friday
  case Saturday
  case Sunday
```

Complete the following two functions (in [WeekdayOps.scala](./src/main/scala/patmat/WeekdayOps.scala)):

1. `next` returns the next day of the week:
   ```scala
   def next(d: Weekday): Weekday =
     ???
   ```
2. `prev` returns the previous day of the week:
   ```scala
   def prev(d: Weekday): Weekday =
     ???
   ```

<details>
<summary> Hint </summary>

Want to test your code? Run `testOnly WeekdayOpsTest` in `sbt`.
</details>


> [!NOTE]
> This exercise is taken from [Logical Foundations](https://softwarefoundations.cis.upenn.edu/lf-current/Basics.html), a book about mathematical proofs of programs, and translated into Scala.

## Tri-booleans ([TriBool.scala](./src/main/scala/patmat/TriBool.scala) & [TriBoolOps.scala](./src/main/scala/patmat/TriBoolOps.scala))

By now you're very familiar with Booleans. But in real life, not every thing is `Yes` or `No`: sometimes we just don't know! Tri-boolean logic helps with this by adding an indeterminate value, `Maybe` (in [TriBool.scala](./src/main/scala/patmat/TriBool.scala)):

```scala
enum TriBool:
  case Yes
  case No
  case Maybe
```

`Maybe` is like a Boolean value that is not yet known. 

1. Take the time to think of how `Maybe` combine with `Yes` and `No`.

- If I have two objects, one which is for sure blue, and one that may be blue, then can I say that I have at least one blue item? Is that for sure or maybe? What does this entail about the “or” operator on tri-Booleans?

- If I have the same two objects (one blue, one maybe blue), can I conclusively say that not all or my objects are blue? Can I promise that they are all blue? What does this entail about the “and” operator on tri-Booleans?

2. Implement the following operations (we expect that you'll find pattern-matching very nice for that!)

```scala
def neg(b: TriBool): TriBool =
  ???
```
```scala
def and(b1: TriBool, b2: TriBool): TriBool =
  ???
```
```scala
def or(b1: TriBool, b2: TriBool): TriBool =
  ???
```
```scala
def nand(b1: TriBool, b2: TriBool): TriBool =
  ???
```

<details>
<summary> Hint 1 </summary>

For pattern-matching two values simultaneously, often the following style is used:

```scala
  (b1, b2) match
    case (Yes, Yes) => [...]
    case (Yes, No)  => [...]
```
</details>

An alternative is to use nested match-expressions.

<details>
<summary> Hint 2 </summary>

Want to test your code? Run `testOnly TriBoolOpsTest` in `sbt`.
</details>

> [!NOTE] 
> `nand` is a very surprising operator. If you're not familiar with it, inspect the test cases, or [read more about it!](https://en.wikipedia.org/wiki/Sheffer_stroke)

## Contexts ([EnumContext.scala](./src/main/scala/patmat/EnumContext.scala))

Now that you have a bit of experience with pattern matching, let's use it to construct more complex types. In this exercise we'll study *contexts*, which are essentially lists of keys and values. 

Our contexts associate names (`String`s) with values (`Int`s), and let us do the following:

- Create an empty context;
- Add an additional key/value pair (called a “binding”) to a context;
- Look up a key to retrieve the corresponding value;
- Remove a key and its corresponding value.

For instance, we can have a context which associates `"x"` with `1` and `"y"` with `2`. We can then look up the keys: looking up `"x"` in the context produces `1`, looking up `"y"` produces `2`, and looking up `"z"` or any other name results in a not-found error. We can add more mapping in the context: after associating `"z"` with `3`, looking `"z"` up in the new context will result in `3`, instead of an error.

Think of what Scala types and features you may use to represent a context before proceeding.

> [!NOTE]
>  Think about your answer before opening the file.

<details>
  <summary>Our choice of representation (already in <code>EnumContext.scala</code>)</summary>
    We'll start by representing contexts using an enum (and we'll see another representation later):

```Scala
enum Context:
  case Empty
  case Cons(name: String, value: Int, tail: Context)
```
    
  We have two cases:
  - `Empty`, which represents an empty context;
  - `Cons`, which forms a new context by associating a new name with its value in an existing context.

  For example, the context that associates `"x"` with `1` and `"y"` with `2` can be represented as `Cons("x", 1, Cons("y", 2, Empty))`, or `Cons("y", 2, Cons("x", 1, Empty))`.
  
</details>

> [!NOTE] 
> If you want to experiment with contexts in the playground, make sure to add `import EnumContext.*` to bring `Cons` and `Empty` into the worksheet's scope.

> [!WARNING]
>  When multiple bindings in a context have the same name, only the outermost is effective. For instance, given a context `Cons("a", 1, Cons("b", 2, Cons("a", 3, Empty)))`, looking up `"a"` this context should return `1` instead of `3`.

Implement the following three functions (in [EnumContext.scala](./src/main/scala/patmat/EnumContext.scala)):

- `empty` returns an empty context:
  ```scala
  def empty: Context =
    ???
  ```
- `cons` forms a new context by associating a new pair of name-value in an existing context:
  ```scala
  def cons(name: String, value: Int, rem: Context) =
    ???
  ```
- `lookup` looks a name up in a given context:
  ```scala
  def lookup(ctx: Context, name: String): LookupResult =
    ???
  ```

  Notice the return type: we used another `enum`, called `LookupResult`, to capture two possible results: either finding a value, or indicating that the name could not be found.
  ```scala
  enum LookupResult:
    case Ok(v: Int)
    case NotFound
  ```
  
- `erase` drops **all** bindings with the given name in the context:
  ```scala
  def erase(ctx: Context, name: String): Context =
    ???
  ```

<details>
<summary> Hint </summary>

Want to test your code? Run `testOnly EnumContextTest` in `sbt`.
</details>

## Tree Mapping and Reducing ([IntTree.scala](./src/main/scala/patmat/IntTree.scala) & [IntTreeOps.scala](./src/main/scala/patmat/IntTreeOps.scala))

<details>
<summary> Hint </summary>

Want to test your solutions in this chapter? Run `testOnly IntTreeOpsTest` in `sbt`.
</details>

Last week we worked with trees using a clunky API of `.left`, `.right`, and `.isEmpty`. This time, let's do it the right way. And, to mix things up a bit, we'll see a tree that contains values only at the leaves, instead of in every node.

The `IntTree` `enum` is defined as the following (in [IntTree.scala](./src/main/scala/patmat/IntTree.scala)):

```scala
enum IntTree:
  case Leaf(value: Int)
  case Branch(left: IntTree, right: IntTree)
```

What might be the `enum` representation of the tree below?

<details>
  <summary>Solution</summary>

  ```Scala
Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))
  ```
</details>

![treeExampleImage](example.jpg)

### Tree Mapping

Let's start with the `treeMap` function (in [IntTreeOps.scala](./src/main/scala/patmat/IntTreeOps.scala)):

```scala
def treeMap(tree: IntTree, value: Int): IntTree =
    ???
```

It takes a tree and adds a specified value to each leaf node.

For instance, let's name the tree we just saw in the diagram as `t`. The following diagram dipicts the computation of `treeMap(t, 1)`.

![treeMapExampleImage1](treeMap1.jpg)

![treeMapExampleImage2](treeMap2.jpg)

We will later see a generalized `treeMap` function that can apply arbitrary functions to the values in a tree.

### Tree Reducing

The second task is the `treeReduce` function (in [IntTreeOps.scala](./src/main/scala/patmat/IntTreeOps.scala)):

```scala
def treeReduce(tree: IntTree): Int =
    ???
```

Given a tree, the function returns the sum of all leaf nodes. The diagram below depicts the process `treeReduce(t)`.

![treeReduceExampleImage1](treeReduce1.jpg)

![treeReduceExampleImage2](treeReduce2.jpg)

Intuitively, it is a *bottom-up* aggregation of values in the leaves. The values flow from the bottom to the root. At each branch node, the aggregated results from the two children are merged.

But many of us prefer to think of it as a “pull” operation: to compute its own value, each internal node “pulls” the values of the subtrees by making recursive call.

A bit more formally,

- For each branch node, e.g. `Branch(left, right)`, the function should recurse on both left and right and merge the results.
- For the leaf node, e.g. `Leaf(value)`, the value is returned.

Again, we will later see `reduce` in a more general setting, not just summation of values.

## IntList ([IntList.scala](./src/main/scala/patmat/IntList.scala) & [IntListOps.scala](./src/main/scala/patmat/IntListOps.scala))

You already used `IntList`s in last weeks exercise, let's implement a few functions again, this time with pattern matching. Before starting this part of exercises, we suggest you implementing several `IntList` questions from previous weeks' exercises using pattern matching to warm yourself up!


<details>
<summary> Hint </summary>

Want to check answers in this section on computer? Test them with `testOnly IntListOpsTest`.
</details>


### polishEval

First, rewrite `polishEval` ([from last week](../TP2/)) with pattern-matching (in [IntListOps.scala](./src/main/scala/patmat/IntListOps.scala)):

  ```scala
  def polishEval(l: IntList): (Int, IntList) =
    ???
  ```

Throw `InvalidOperationNumber exception` if the operator is not defined, and `InvalidExpression` if the input is not a valid polish-notation expression.

Compare your version with the original `if`-based implementation. Which one is more readable?

### extractSecond

Enumerations and case classes are often useful to distinguish multiple kinds of results. In an imperative language this would often be done with an exception: a function returns a result, and may throw various exceptions if the result cannot be computed. In Scala (and functional programming), we tend to use case classes instead, with one case per kind of result.

For example, a function that extracts the second element of a list might return a type like the following:

```scala
enum ExtractResult:
  case SecondElem(i: Int)
  case NotLongEnough
  case EmptyList
```

When the input list is empty, `extractSecond` returns `EmptyList`; when the input list is not long enough to have a second element (i.e. it only has one element), `extractSecond` returns `NotLongEnough`; finally, when the input list has a second element, the function returns it.

Implement `extractSecond`:

  ```scala
  def extractSecond(l: IntList): ExtractResult =
    ???
  ```

### zipping and unzipping

We have studied many functions on single lists, and also seen that some of these generalize to trees; here, we will introduce some of the most essential operations on multiple lists.

Let's define a type IntIntList of lists of pairs of `Int`s:

```scala
enum IntIntList:
  case IntIntNil
  case IntIntCons(xy: (Int, Int), xs: IntIntList)
import IntIntList.*
```

1. Implement a function `zip` to construct a list of pairs from a pair of lists:

```scala
def zip(l1: IntList, l2: IntList): IntIntList =
  ???
```

Here is one possible specification for `zip`, using `l[n]` to mean "the `n`-th element of list `l`": Given two lists `xs` and `ys`, let `zs` be `zip(xs, ys)`. Then, `zs` should have length `min(length(xs), length(ys))`, and the `i`-th element of `zs` should be `(xs[i], ys[i])` for all `i`.

Note the part about the length of `zs`. For instance, `zip(IntNil, IntCons(1, Nil))` should equal to `IntIntNil`.

2. Define a function `unzip` to construct a pair of lists from a list of pairs:

```scala
def unzip(l: IntIntList): (IntList, IntList) =
  ???
```

3. What relations are there between `zip` and `unzip`? Are you able to prove those relations?

4. Use your function `zip` to implement a function `movingWindow` on lists that returns sequences of consecutive pairs in its input list. For example, `movingWindow` applied to the list `a b c d e` should produce the list `ab bc cd de`.

```scala
def movingWindow(l: IntList): IntIntList =
  ???
```
