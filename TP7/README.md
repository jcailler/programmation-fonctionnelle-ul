# Functional Programming - TP 7: Laziness

In this exercise we’ll explore lazy computation by experimenting with lazy lists, where procrastination meets programming.

Lazy lists (as `LazyList`s in Scala) are called “lazy” because they compute elements only when they are needed:

* Elements are memoized; that is, the value of each element is computed at most once.
* Elements are computed in-order and are never skipped. In other words, accessing the tail causes the head to be computed first.

This allows for powerful new constructs, such as infinite data structures.

Remember, in this session, procrastination isn’t a bad thing… it’s the entire point! Let’s dive together into the world of LazyLists, at our own pace, of course. 🐢💤

## Why Call Right Now When You Can Call Later?

### Quick Recap

In the very beginning of this course, we have studied two different evaluation strategies:

* Call-by-value: function arguments are fully evaluated before the function.
* Call-by-name: function arguments are evaluated every time they are used, and not before then.

This week, you will get to know a third evaluation strategy: Call-by-need. It is similar to call-by-name, but the result of the argument evaluation is memoized (stored) for future uses.

In Scala, call-by-need variables or fields are denoted by a `lazy` keyword before the definition. This means that once a `lazy` variable (or field) is evaluated, the result is memoized (stored) for future uses. Lazy lists are a great example of call-by-need evaluation, the elements are computed on-demand and also stored for future use.

With this out of the way, let’s dive into to the first exercise.

### Understanding Evaluation Strategies ⭐️

First, let’s warm up on some simple code. What will the following code print?

```Scala
val x = { print("x"); 1 }
lazy val y = { print("y"); 2 }
def z = { print("z"); 3 }
println(z + y + x + z + y + x)
```

Take the time to discuss your answer with your classmates before reading the solution.

<details>
<summary> Solution </summary>

xzyz12

</details><br/>

### Who Evaluates What and When? ⭐️

Think of lazy lists as a box that only shows items when requested. After accessing an item, you don’t revisit it in the box. This “on-demand” behavior is deeply connected to the concept of call-by-need evaluation.

Now, let’s consider the following implementation of `List1`, `List2` and `List3`:

```Scala
class List1[+A](val state: List1State[A])

enum List1State[+A]:
  case Cons(head: A, tail: List1[A])
  case Nil

class List2[+A](val init: => List2State[A]):
  lazy val state: List2State[A] = init

enum List2State[+A]:
  case Cons(head: A, tail: List2[A])
  case Nil

class List3[+A](val state: () => List3State[A])

enum List3State[+A]:
  case Cons(head: () => A, tail: () => List3[A])
  case Nil
```

Which evaluation strategy do each of these lists use: by-value, by-name, or by-need?

<details>
<summary> Solution </summary>

* `List1` uses by-value,
* `List2` uses by-need,
* `List3` uses by-name.

</details><br/>

## “Infinity and Beyond!” - Implementing Infinite Lazy Lists

During the lecture, you were introduced to the concept of infinite lists, made possible through lazy evaluation in programming.

Lazy lists only compute elements as they are needed, allowing for the definition of potentially infinite data structures.

### MyLazyList Implementation

This week we’ll use `MyLazyList`, our own implementation of lazy lists in Scala. We also provide a skeleton for the exercise.

The `MyLazyList` class has a lazy field pointing to a state, which can either be a `LZCons` (representing a non-empty list) or `LZNil` (representing an empty list). The class is described below:

```Scala
class MyLazyList[+A](init: () => MyLazyListState[A]):
  lazy val state: MyLazyListState[A] = init()

enum MyLazyListState[+A]:
  case LZCons(elem: A, tail: MyLazyList[A])
  case LZNil
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

We provide utility methods to help you create instances of `MyLazyList`:

```Scala
def cons[A](elem: => A, tail: => MyLazyList[A]): MyLazyList[A] =
  MyLazyList(() => LZCons(elem, tail))

def empty: MyLazyList[Nothing] = MyLazyList(() => LZNil)
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

Also, here are the provided methods to interact with `MyLazyList`:

* `isEmpty`: Check if the list is empty.
* `head`: Retrieve the first element.
* `tail`: Get the rest of the list after the first element.
* `size`: Get the size of the list.
* `foreach`: Iterate over each element of the list. This method takes a function and applies it to each item in the list (without modifying it).
* `contains`: Check if the list contains a given element.
* `get`: Get the element at a given index.

```Scala
def isEmpty: Boolean = self.state match
  case LZNil        => true
  case LZCons(_, _) => false

def head: A = self.state match
  case LZNil        => throw RuntimeException("head of empty list")
  case LZCons(x, _) => x

def tail: MyLazyList[A] = self.state match
  case LZNil         => throw RuntimeException("tail of empty list")
  case LZCons(_, xs) => xs

def size: Int = self.state match
  case LZNil         => 0
  case LZCons(_, xs) => 1 + xs.size

def foreach(f: A => Unit): Unit = self.state match
  case LZNil => ()
  case LZCons(x, xs) =>
    f(x)
    xs.foreach(f)

def contains(elem: A): Boolean = self.state match
  case LZNil => false
  case LZCons(x, xs) =>
    if x == elem then true
    else xs.contains(elem)

def get(i: Int): A =
  if i < 0 then throw RuntimeException("index out of bounds")
  else
    self.state match
      case LZNil => throw RuntimeException("index out of bounds")
      case LZCons(x, xs) =>
        if i == 0 then x
        else xs.get(i - 1)
```

<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

>[!NOTE]
>These operations will execute delayed computation through the lazy list. `size`, `foreach`, and `contains` ensure the whole lazy list is evaluated; hence, they do not terminate on infinite lazy lists.

Before we continue, take a moment to familiarize yourself with their implementation.

This week, we’ll implement other operations for lazy lists, and they should take **constant time** (`O(1)`) regardless of the size of the list or the content. They should delay computation and not execute immediately.

### Basic Lazy List Operations - Part 1 ⭐️

Your task is to implement the following methods:

* `take(n)` returns a lazy list containing the first `n` elements of the original lazy list. If the original lazy list has fewer than `n` elements, it will return a lazy list with the same elements as the original.
* `drop(n)` returns a lazy list with the first `n` elements removed. If the original lazy list has fewer than `n` elements, it will return an empty lazy list.

Before you start, take a look at the “wrong” implementation of `take`:

```Scala
def wrongTake(n: Int): MyLazyList[A] =
  if n <= 0 then empty
  else
    self.state match
      case LZNil         => empty
      case LZCons(x, xs) => cons(x, xs.wrongTake(n - 1))
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

`wrongTake` seems to work fine, but it has a problem: forcing the computing too early. The first element of the lazy list is evaluated immediately when `wrongTake` is called. If we call `wrongTake` on an infinite lazy list with complicated references to the first element, it may never terminate.

We don’t need to look at the first element until we need it, so we can wrap the logic of `take` inside the closure of `MyLazyList` class. Remember, these operations should delay computation as much as possible.

Here are the definition of these methods:

```Scala
def take(n: Int): MyLazyList[A] =
  if n <= 0 then empty
  else
    MyLazyList(() =>
      self.state match
        case LZNil => ???
        case LZCons(x, xs) => ???
    )


def drop(n: Int): MyLazyList[A] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

Test these methods on infinite lazy lists and check whether they terminate.
How does the behavior and result differ compared to a normal list?

### Building Infinite Lazy Lists Using Recursive Functions ⭐️

Infinite lazy lists can be defined using recursive functions, often referencing themselves. Such definitions do not cause infinite loops by themselves due to the nature of the lazy list’s lazy computation.

Reimplement the `from` and `range` methods:

* `from(x)` generates an infinite lazy list starting from the given number `x` and increments by 1 for each subsequent element.
* `range(x, y)` generates a lazy list starting from the number `x` and going up to (but not including) the number `y`, incrementing by 1 for each subsequent element.

```Scala
def from(x: Int): MyLazyList[Int] =
  ???

def range(x: Int, y: Int): MyLazyList[Int] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/IntLazyLists.scala](./laziness/src/main/scala/laziness/IntLazyLists.scala)
</div>

<details>
<summary> Hint </summary>

You can use the operations you just implemented.

</details><br/>


### Basic Lazy List Operations - Part 2 ⭐️

Next, your task is to implement the following methods:

* `map(f)` applies a function `f` to each element of the lazy list and returns a new lazy list with the results.
* `filter(f)` returns a new lazy list containing only the elements of the original lazy list for which the given predicate `f` returns true.
* `zip(that)` returns a lazy list of pairs of corresponding elements of the original lazy list and `that`. If one of the two lazy lists is longer than the other, its remaining elements are ignored.

```Scala
def map[B](f: A => B): MyLazyList[B] =
  ???

def filter(p: A => Boolean): MyLazyList[A] =
  ???

def zip[B](that: MyLazyList[B]): MyLazyList[(A, B)] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

### Building Infinite Lazy Lists Using Recursive Data ⭐️

Let practice more infinite lazy lists using the operations you just implemented. In this section, we focus on building infinite lazy lists using recursive data.

#### Simple Recursion Lazy List

Consider the following example:

```Scala
val anonymList: MyLazyList[Int] = cons(1, anonymList)
```
1. What does this list correspond to?
2. Does the definition of `anonymList` cause an infinite loop?
3. Implement an infinite list of `2`s using `anonymList` and `map`.

```Scala
lazy val infiniteTwoes: MyLazyList[Int] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/IntLazyLists.scala](./laziness/src/main/scala/laziness/IntLazyLists.scala)
</div>

We can easily construct the lazy list of all natural numbers using from.

```Scala
val naturalNumbers1: MyLazyList[Int] = from(0)
```

4. Try to implement the same lazy list using itself and `map`.

```Scala
lazy val naturalNumbers2: MyLazyList[Int] =
  cons(0, ???)
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/IntLazyLists.scala](./laziness/src/main/scala/laziness/IntLazyLists.scala)
</div>

#### Fibonacci’s Magic Show! 🔥

The Fibonacci sequence is a series of numbers where each number is the sum of the two preceding ones. The first few values in the sequence are: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144.

Implement the Fibonacci sequence by filling the hole using `zip` and `map`.

```Scala
lazy val fib: MyLazyList[Int] =
  cons(0, cons(1, ???))
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/IntLazyLists.scala](./laziness/src/main/scala/laziness/IntLazyLists.scala)
</div>

Be careful, if your implementation of the operations does not follow our rule strictly, you may get an infinite loop when you try to access the elements of `fib`.

### Basic Lazy List Operations - Part 3 ⭐️

Last but not least, your task is to implement the following methods:

* `append` returns a new lazy list consisting of the elements of the first lazy list followed by the elements of the second lazy list.
* `flatMap` applies a function `f` to each element of the lazy list and returns a new lazy list with the results. The resulting lazy list will be a concatenation of all the lazy lists returned by `f`.

```Scala
def append(that: MyLazyList[A]): MyLazyList[A] =
  ???

def flatMap[B](f: A => MyLazyList[B]): MyLazyList[B] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/MyLazyList.scala](./laziness/src/main/scala/laziness/MyLazyList.scala)
</div>

After you finish the implementation of `append`, you will notice how “efficient” it is compared to `++` on regular lists. `++` from regular lists has to traverse the first list to the end and build a new list. `append` on lazy lists only needs to traverse the first list when needed, spreading the cost over the entire process.

### String Lazy List 🔥

Write a lazy list of all non-empty strings using the characters “0” and “1” and the concatenation operation +. In other words, every non-empty string composed of “0” and “1” should be reached at some point.

```Scala
lazy val codes: MyLazyList[String] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/OtherPractice.scala](./laziness/src/main/scala/laziness/OtherPractice.scala)
</div>

Using `codes`, write a lazy list of all possible non-empty palindromes of “0” and “1”. You may use the `.reverse` function defined on strings, as well as `filter`.

```Scala
lazy val palCodes: MyLazyList[String] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/OtherPractice.scala](./laziness/src/main/scala/laziness/OtherPractice.scala)
</div>

Computing the reverse of large numbers of strings and comparing them are expensive. Can you achieve the same result without filtering? The palindromes do not have to be in the same order.

Note that for each palindrome, there is a unique code `s` which is a prefix of the palindrome and `s.reverse` is a suffix of the palindrome.
1. `s + s.reverse`
2. `s + "0" + s.reverse`
3. `s + "1" + s.reverse`

Based on this observation, `palCodes` can be implemented using `middle`:

```Scala
val middle: MyLazyList[String] = cons("", cons("0", cons("1", empty)))
```

(`middle` does not need to be a lazy list. We define it this way to make the list methods compatible with our implementation.)

Now, we can implement `palCodes2` without filtering:

```Scala
lazy val palCodes2: MyLazyList[String] =
  // need to add base cases at the beginning
  // need to add base cases at the beginning
  cons("0", cons("1", empty)).append(???)
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/OtherPractice.scala](./laziness/src/main/scala/laziness/OtherPractice.scala)
</div>

## More practices on infinite lazy lists

Having explored the foundations of infinite lazy lists and their recursive nature, let’s transition to understanding specific sequences that can be represented using lazy lists.

### Look And Say ⭐️

Consider the following series:
```
1
1 1
2 1
1 2 1 1
1 1 1 2 2 1
3 1 2 2 1 1
...........
```
1. Can you guess the next row?
<details>
<summary> Hint </summary>

Come to the exercise session and ask the teacher! The rule is kinda stupid.

</details><br/>

2. Now, let us encode an element of the sequence above as a `List[Int]`. Write a function to compute the next line, based on the current line.
```Scala
def nextLine(currentLine: List[Int]): List[Int] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/OtherPractice.scala](./laziness/src/main/scala/laziness/OtherPractice.scala)
</div>

3. Implement a lazy list `funSeq` which constructs this sequence.
```Scala
lazy val funSeq: MyLazyList[List[Int]] =
  ???
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/OtherPractice.scala](./laziness/src/main/scala/laziness/OtherPractice.scala)
</div>

### Sieve of Erathostenes ⭐️

Sieve of Eratosthenes is an ancient technique to calculate prime numbers:

* Start with all integers from 2, the first prime number.
* Eliminate all multiples of 2.
* The first element of the resulting list is 3, a prime number.
* Eliminate all multiples of 3.
* Iterate forever. At each step, the first number in the list is a prime number and we eliminate all its multiples.

Your task is to implement the Sieve of Eratosthenes using lazy lists.

```Scala
lazy val primeNumbers: MyLazyList[Int] =
  import MyLazyListState.*
  def sieve(s: MyLazyList[Int]): MyLazyList[Int] =
  ???
  sieve(???)
```
<div style="text-align: right; color:grey"> 

[laziness/src/main/scala/laziness/IntLazyLists.scala](./laziness/src/main/scala/laziness/IntLazyLists.scala)
</div>

And thus, you are done for this week!

Lazy list is a clever way to represent delayed computation and infinite data structures. However, it is not a silver bullet. Abusing lazy lists may lead to performance issues, due to the overhead of closures and memoization.
