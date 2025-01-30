# Functional Programming - TP 5: Polymorphism

The exercise set is intended to help you practice lists and polymorphism.

## Warm-Up: Polymorphic Lists ⭐️

In previous exercises and labs, we used `IntList` for lists which elements are integers. This week, we’ll move to polymorphic lists.

<details><summary> Reminder: Algebraic Data Types </summary>

In week 3, we learned that algebraic data types can be created with the `enum` construct. Check the previous lectures or [this](https://docs.scala-lang.org/scala3/book/types-adts-gadts.html) for more details.

</details>

Polymorphic lists can be defined as an algebraic data type in the following way:
```scala
enum MyList[+A]:
  case Nil
  case Cons(x: A, xs: MyList[A])
```


> [!TIP] Covariance
> The `+` before `A` indicates that `List` is covariant in `A`. Check [this](https://docs.scala-lang.org/tour/variances.html) for more details, or ignore it for now — we will cover it later!

We’ll use the above `MyList` type in this exercise.

> [!NOTE] Check Yourself
> How would you define the `isEmpty`, `head`, and `tail` methods on such polymorphic lists?

<details>
<summary> Solution</summary>

```scala
def isEmpty: Boolean = this match
  case Nil => true
  case _   => false

def head: A = this match
  case Nil        => throw EmptyListException()
  case Cons(x, _) => x

def tail: MyList[A] = this match
  case Nil         => throw EmptyListException()
  case Cons(_, xs) => xs
```
</details>

## Functions on Polymorphic Lists ⭐️

A skeleton for this part is given in the file [`MyListOps.scala`](src/main/scala/MyListOps.scala) of the exercise files.

### Part 1: Higher-order Functions 

In last week, we have implemented higher-order functions on `IntList`s. For example, `map` was defined as:

```scala
def map(l: IntList)(f: Int => Int): IntList =
  l match
    case IntNil         => IntNil
    case IntCons(x, xs) => IntCons(f(x), map(xs)(f))
```

In contrast, the function `map`, generalized to generic lists, has the following signature:

```scala
def map[A, B](l: MyList[A])(f: A => B): MyList[B] =
  l match
    case Nil         => Nil
    case Cons(x, xs) => Cons(f(x), map(xs)(f))
```

1. Based on the example above, write a generic signature for `filter`, `foldRight`, `reduceRight`, `forall`, `exists`, `zip`, and `zipWith`.

<details>
<summary> Solution </summary>

```scala
    def filter[A](l: MyList[A])(p: A => Boolean): MyList[A] =
      ???

    def foldRight[A, B](l: MyList[A])(f: (A, B) => B, base: B): B =
      ???

    def reduceRight[A](l: MyList[A])(f: (A, A) => A): A =
      ???

    def forall[A](l: MyList[A])(p: A => Boolean): Boolean =
      ???

    def exists[A](l: MyList[A])(p: A => Boolean): Boolean =
      ???

    def zip[A, B](l1: MyList[A], l2: MyList[B]): MyList[(A, B)] =
      ???

    def zipWith[A, B, C](l1: MyList[A], l2: MyList[B])(op: (A, B) => C): MyList[C] =
      ???
```
</details>


2. In previous exercises, we had separate implementations for `foldRight` and `foldRightList` (we had to handle the cases of returning an integer and returning an `IntList` separately).
Do we need to define a similar `foldRightList` on polymorphic lists?

<details> 
<summary> Solution </summary>

No, type variable B can be instantized to `MyList[Int]`.

</details>

3. Implement these eight higher-order functions (`map` plus all other ones above) on `MyList` using pattern matching.

Then, using these APIs of lists:

1. Implement function `elementsAsStrings` which converts every element of a list to a string (you may need the `.toString` function):

```scala
    def elementsAsStrings[A](l: MyList[A]): MyList[String] =
      ???
```

2. Reimplement functions from previous exercises on polymorphic lists:

```scala
    //length returns the length of the list
    def length[A](l: MyList[A]): Int =
      ???

    //returns the first elements of the list until the first non-positive element occurs (exclusding it)
    def takeWhilePositive(l: MyList[Int]): MyList[Int] =
      ???

    //returns the last element of the list
    def last[A](l: MyList[A]): A =
      ???
```

3. Adapt the string functions `capitalizeString` and `wordCount` to operate on lists of characters:

<details><summary> Hint </summary>
 
You might need `toUpper` and `isWhitespace`

</details>

```scala
    def capitalizeString(l: MyList[Char]): MyList[Char] =
        ???

    def wordCount(l: MyList[Char]): Int =
        ???
```

> [!WARNING] 
> The solution from exercise session 2 for wordCount doesn’t express itself naturally as a fold… try to look for a different one!

> [!NOTE]
> Both `String` and `List[Char]` or `MyList[Char]` represent sequences of characters. However, it’s usually more efficient and convenient to use `String` for text processing and manipulation in Scala because `String` has optimized storage for texts and rich APIs tailored for text operations.

### Part 2: More Functions --- `flatMap` and `cross-product`

#### `flatMap`

You may have come across `flatMap`, a powerful higher-order function that can be used to transform and flatten container datatypes, such as lists.

```scala
def flatMap[A, B](l: MyList[A])(f: A => MyList[B]): MyList[B] =
  ???
```

The idea of `flatMap(f)(l)` is:

- Map: apply a function `f` to each element of the list `l`, where `f` returns a list;
- Flatten: concatenate all the resulting lists into a single list.

For example,

```scala
object FlatMapExamples:
  val numbers: MyList[Int] = Cons(2, Cons(3, Nil))

  val mapped = map(numbers)((n: Int) =>
    Cons(n, Cons(n * 2, Nil))
  )
  // For simplicity, we write Cons as `::` in the results.
  // Result: (2 :: 4 :: Nil) :: (3 :: 6 :: Nil)

  val flatMapped = flatMap(numbers)((n: Int) =>
    Cons(n, Cons(n * 2, Nil))
  )
  // Result: 2 :: 4 :: 3 :: 6 :: Nil
```

1. Implement `flatMap`. You may use the `append` function that we included in the starting code.

2. Implement `flatten` using `flatMap`. `flatten` takes a list of lists, and returns the concatenation of all the lists list:

```scala
    def flatten[A](l: MyList[MyList[A]]): MyList[A] =
      ???
```

#### `cross-product`

The cross-product function, often referred to as the Cartesian product, produces all possible pairs (combinations) of elements from two lists.

```scala
def crossProduct[A, B](l1: MyList[A], l2: MyList[B]): MyList[(A, B)] =
  ???
```

For example, given a list of main dishes and a list of side dishes, we can use `crossProduct` to generate all possible meal combinations:

```scala
object CrossProductExamples:
  val mains = Cons("burger", Cons("Pizza", Cons("Pasta", Nil)))
  val sides = Cons("Salad", Cons("Soup", Nil))

  val meals = crossProduct(mains, sides)
  // Result:
  // ("burger", "Salad") :: ("burger", "Soup") :: ("Pizza", "Salad") ::
  // ("Pizza", "Soup") :: ("Pasta", "Salad") :: ("Pasta", "Soup") :: Nil
```

- Implement `crossProduct` using `flatMap`.

### Part 3: Triangles in DAG

Consider a directed graph (DAG) given by its set of (directed) edges stored as a list of pairs of nodes:

```scala
type NodeId = Int
type DirectedEdge = (NodeId, NodeId)
type DirectedGraph = MyList[DirectedEdge]
type Triangle = (NodeId, NodeId, NodeId)
```

Define the triangles function that finds all cycles of length 3, with three distinct nodes, in the given graph.

```scala
def triangles(edges: DirectedGraph): MyList[Triangle] =
  ???
```

<details> <summary> Hint </summary>

You can make use of `flatMap`, `map` and `filter`.

</details>

Each cycle should appear only once. For instance, given the edges:

```
Cons((1, 2), Cons((2, 3), Cons((3, 1), Nil)))
```

You should return exactly one of the three following possibilities:

```
(1, 2, 3), (2, 3, 1), (3, 1, 2)
```

You are free to decide which of the three you return.

## Option Type

A skeleton for this part is given in the file [`Option.scala`](src/main/scala/Option.scala) of the exercise files.

In last week’s exercises, we use a custom type `LookupResult` for the result of looking up in a context:

```scala
enum LookupResult:
  case Ok(v: Int)
  case NotFound
```

It’s always good to explore the [Scala standard library](https://scala-lang.org/api/3.x/). After all, why use a custom type when there is something suitable in the standard library?

Can you find a suitable type for `LookupResult`?

One suitable choice is already given by the title: the [Option](https://scala-lang.org/api/3.x/scala/Option.html#) type!
<details><summary> Is there any other suitable container in the standard library? </summary>

[Tuple](https://scala-lang.org/api/3.x/scala/Tuple$.html#), [Either](https://scala-lang.org/api/3.x/scala/util/Either.html#).

</details>



### Part 1. Basic Usage

The basic usage of `Option` type is as the return type of functions that might not always return a valid value.

<details><summary> How does <code>Option</code> work? </summary>

`Option` has two possible outcomes: `Some` and `None` (which are self-explanatory). For example if your function has as return type `Option[Int]`, then you can return either an integer `n` by `Some(n)` or return nothing by `None`.

Implement `findFirstEvenNumber` to return the first even number in the list, or `None` if there isn’t one.

```scala
def positiveOrNothing(n: Int): Option[Int] = 
    if n > 0 then
        Some(n)
    else
        None
```

Note that `Some(n)` is of type `Option[Int]` whereas `n` is (of course) of type `Int`.
</details>

```scala
def findFirstEvenNumber(l: List[Int]): Option[Int] =
  ???
```

### Part 2. Drawing Parallels with List in Standard Library


Notice that `Option` also has `map`, `flatMap`, `filter` just like `List`. Do you know why?
<details><summary> Hint </summary>

An option is like a list with only one element.

</details>

**In this part**, we use the `List` (`scala.collection.immutable.List`) from the standard library.

You can compare the definition of `map`, `flatMap` and `filter` in [standard library `List` methods](https://scala-lang.org/api/3.x/scala/collection/immutable/List.html#) with [`Option`](https://scala-lang.org/api/3.x/scala/Option.html#)’s. Do the definitions line up? What’s the difference between the definitions on `scala.collection.immutable.List` and our custom polymorphic lists `poly.MyList`?

1. Implement `parseStringToInt` and `findSquareRoot`. Then, define `findSquartRootFromString` to chain these two functions to parse a string and find its square root.

<details><summary> Hint </summary>

You might need `Math.pow` and `Math.sqrt` from the [Math](https://www.scala-lang.org/api/2.13.5/scala/math/index.html) package. Note that `Math.sqrt` takes a double and returns a double, hence you may also need `toDouble` and `toInt`.

</details>

```scala
def parseStringToInt(s: String): Option[Int] =
  ???

def findSquareRoot(n: Int): Option[Double] =
  ???

def findSquareRootFromString(s: String): Option[Double] =
  ???
```

2.  Given a list of strings representing integers:

```scala

val numberStrings: List[String] = List("1", "2", "star", "4")

```

Try to use `map` to convert them in integers. What issues do you face?

Now, use the member method `flatMap` of `scala.collection.immutable.List` and the `parseStringToInt` function to safely convert them.


**Check Yourself** 🔥

Can you do the same trick using our custom lists `poly.MyList` and definition of `flatMap` instead? Why?
<details><summary> Solution </summary>

No.

The fact that we can line up `List` and `Option` easily is because in the standard library, both `List` and `Option` are subtypes of `IterableOnce`, and signatures of useful methods make use of the supertype `InterableOnce`. For example, the signature of `flatMap` in `List` is `def flatMap[B](f: A => IterableOnce[B]): List[B]`.

We will eventually cover this more advanced API at two points later in the course: first to introduce *comprehensions*, and then more generally *monads*.

</details>

## FoldLeft and Tail Recursion ⭐️

A skeleton for this part is given in the file [`MyListOps.scala`](src/main/scala/MyListOps.scala) of the exercise files.

In TP 2, we learned about tail recursion.
Tail recursion is a special form of recursion where the recursive call is the last operation in the function, which allows the compiler to optimize the recursion by reusing the current function's stack frame for the next function call, effectively transforming the recursion into a loop, and therefore more stack memory efficient. 

### Sum

For example, `sum0` is not a tail recursive function:

```scala
def sum0(l: MyList[Int]): Int = l match
  case Nil         => 0
  case Cons(x, xs) => x + sum0(xs)
```

If you uncomment the test which tests `sum0` on a list with 50000 elements, it is very likely to fail on your machine due to stack overflow!

```scala
// test("sum0: large list"):
//   assertEquals(sum0(manyNumbers1), N)
```

Can you implement the sum algorithm using tail recursion?

> [!NOTE] 
> You need to import the package `scala.annotation.tailrec` in order to use `@tailrec` (the import is included in the exercise skeleton).

```scala
def sum1(l: MyList[Int]): Int =
    // @tailrec // Uncomment this line.
    def sum(l: MyList[Int], acc: Int): Int =
        ???
    sum(l, 0)
```

In Scala, the `@tailrec` annotation is a directive for the compiler, indicating that the annotated method should be tail-recursive. If the method is not tail-recursive, the compiler will raise a compile-time error. 

### FoldLeft

Similar to `foldRight`, `foldLeft` processes the list from the leftmost (head) element to the rightmost element.

The main difference between foldLeft and foldRight is that foldLeft is typically implemented using tail recursion, while foldRight is the opposite.

1. Define `foldLeft`:

```scala
    // @tailrec // Uncomment this line.
    def foldLeft[A, B](base: B, f: (B, A) => B)(l: MyList[A]): B =
      ???
```

2. Define `sum0Fold` using `foldRight`, define `sum1Fold` using `foldLeft`:

```scala
    def sum0Fold(l: MyList[Int]): Int =
      ???
    def sum1Fold(l: MyList[Int]): Int =
      ???
```

3. Reimplement `reverseAppend` using `foldLeft`:

```scala
    def reverseAppend[A](l1: MyList[A], l2: MyList[A]): MyList[A] =
      ???
```
4. Implement `countEven` and `totalLength` using `foldLeft`. `CountEven` takes a list of integers and returns the number of even integers in the list; `totalLength` takes a list of strings and return the sum of each string’s length.

```scala
    val countEven: MyList[Int] => Int =
      ???

    val totalLength: MyList[String] => Int =
      ???
```

## Currying and Composition

A skeleton for this part is given in the file [`Fun.scala`](src/main/scala/Fun.scala) of the exercise files.

<details><summary> Reminder </summary>

You can check the previous exercises for currying and composition in TP 4: Higher-order Functions.
</details>

### CurriedZipWith

Use `map` and `zip` to implement the curried version `curriedZipWith` of `zipWith` in the file `MyListOps.scala`.
<details><summary> Defining polymorphic function values </summary>

Reference: [Polymorphic Function Types](https://docs.scala-lang.org/scala3/reference/new-types/polymorphic-function-types.html).


```scala
// A polymorphic method:
def foo[A](xs: List[A]): List[A] = ???

// A polymorphic function value:
val bar = [A] => (xs: List[A]) => foo[A](xs)
```

`bar` has type `[A] => List[A] => List[A]`. This type describes function values which take a type `A` as a parameter, then take a list of type `List[A]`, and return a list of the same type `List[A]`.
</details>

```scala
val curriedZipWith =
  ???
```

> [!WARNING]
> You must uncomment the corresponding test in [MyListOpsTest](src/test/scala/MyListOpsTest.scala) to test your function

### Polymorphic Composition 🔥

1. In previous exercises we defined a function `compose` to compose functions `f: Int => Double` and `g: Double => String`. Generalize this function to arbitrary pairs of types, using polymorphic argument types.

2. What is the neutral element for the generalized `compose`?

3. In previous exercises, we defined `andLifter` and `notLifter` for functions on `Int`. To make it more general, we can define `andLifter` for functions of arbitrary input types:

```scala
def andLifter[A](f: A => Boolean, g: A => Boolean): A => Boolean =
  a => f(a) && g(a)
```

… and we can generalize further! Look at the following four functions; do they have anything in common?

```scala
def orLifter[A](f: A => Boolean, g: A => Boolean): A => Boolean =
  a => f(a) || g(a)
def sumLifter[A](f: A => Int, g: A => Int): A => Int =
  a => f(a) + g(a)
def listConcatLifter[A, B](f: A => MyList[B], g: A => MyList[B]): A => MyList[B] =
  a => append(f(a),g(a))
```

Write a `binaryLifter` higher-order function to capture the common pattern above, and use it to rewrite all four lifters that we’ve seen up to this point.

```scala
def binaryLifter[A, B, C](op: (B, B) => C)(f: A => B, g: A => B): A => C =
  ???
```

```scala
def andLifter1[A](f: A => Boolean, g: A => Boolean) =
  ???

def orLifter1[A](f: A => Boolean, g: A => Boolean) =
  ???

def sumLifter1[A](f: A => Int, g: A => Int) =
  ???

def listConcatLifter1[A, B](f: A => MyList[B], g: A => MyList[B]) =
  ???
```

4. Similarly, we can implement a `unaryLifter` to generate lifters like `notLifter`. Can you tell which function `unaryLifter` essentially is?

