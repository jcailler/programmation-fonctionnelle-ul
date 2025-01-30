# Functional Programming - TP 4: Higher-Order Functions

In this exercise, you'll start by revisiting exercise 2's questions, and search for ways to leverage the power of higher-order functions to write shorter, simpler code. Then you'll become familiar with the notion of “functions as values”. You’ll store functions in `val`s, combine them, make functions that make functions that make functions, etc. 

## Higher-Order Functions on Lists and Trees 

Many of you noted that we asked to write very similar code many, many times in TP 2. Let’s explore this observation in more detail.

### Part 1: Observation ⭐️
Let’s look at five categories of functions, each illustrated by a pair of recursive functions from the `ExList` file of exercise 2 (also available [here](hof-index-cards.pdf) and in [ListOps.scala](src/main/scala/hofs/ListOps.scala)). 

* `sum` and `product` (we’ll call this pair “`associative`”)
* `increment` and `capAtZero` (we’ll call this pair “`map`”)
* `collectEven` and `removeZeroes` (we’ll call this pair “`filter`”)
* `countEven` and `multiplyOdd` (we’ll call this pair “`foldRight`”)
* `min` and `last` (we’ll call this pair “`reduceRight`”)

We provide you files for each of those categories. You can find those categories in [src/main/scala/hofs](src/main/scala/hofs). 
Read the implementation of these functions carefully. For each pair, ask yourself: what do these two functions have in common? Which parts differ? Highlight the differences.


### Part 2: Conjecture ⭐️

Let’s see how well the patterns that you have found generalize. You should have around 20 function left. The objective now is to take all your functions and to put them into one of the five category above, based on how well it fits the pattern that you identified earlier. If you feel that you need more categories, that’s OK. Oh, and beware: two of the cards don’t fit!

<details>
<summary> Hint </summary>

In particular, you may decide that `allEven`/`allPositiveOrZero`/`isSubset` and `anyOdd`/`anyNegative`/`contains` should be their own categories. That’s fine!

</details><br/>

<details>
<summary> Hint </summary>

`reverseAppend` and `init` are the ugly ducklings. `reverseAppend` is an instance of a different pattern (a “`left fold`”) that we will study later.

</details><br/>

Compare your results with another classmate. Do you agree on everything? Did you create the same additional categories? If you disagree, is it because one category is a special case of another one?

<details>
<summary> Hint </summary>

If you look very carefully, you will see that all categories are special cases of “`fold`” and “`reduce`”.

</details><br/>

> [!WARNING]
> In order to compile your code, you may need to comment some functions if you want to have duplicated ones into the same package.


### Part 3: Experiment ⭐️
Time for higher-order functions! Take the categories above one by one, and for each of them, write one single function that is more general than all the examples belonging to that category. To help with this, consider starting with the following warm-up exercise:


#### Warm-up 

Higher-order functions can be used to “abstract away” part of a function. You can find the code for these functions in [src/main/scala/hofs/Fun.scala](src/main/scala/hofs/Fun.scala)

Consider the following two functions, which check whether the head of a list has a certain property, and return false if the list is empty:

```Scala
def headIsEven(l: IntList): Boolean =
  !l.isEmpty && l.head % 2 == 0
def headIsPositive(l: IntList): Boolean =
  !l.isEmpty && l.head > 0
```

The bodies of these functions are very similar, so we can abstract away the common parts into a separate higher-order function (a function that takes a function):

```Scala
def headHasProperty(p: Int => Boolean, l: IntList): Boolean =
  !l.isEmpty && p(l.head)
```

Now, use `headHasProperty` to refactor (i.e. rewrite in a more succinct way) the functions `headIsEven` and `headIsPositive`:

<details>
<summary> Solution </summary>

```Scala
def headIsEven1(l: IntList): Boolean =
  headHasProperty(i => i % 2 == 0, l)

def headIsPositive1(l: IntList): Boolean =
  headHasProperty(i => i > 0, l)
```

</details><br/>

Now take a look at these three functions:

```Scala
def DoubleTriple(x: Int) =
  IntCons(x * 2, IntCons(x * 3, IntNil()))

def DivideTrivide(x: Int) =
  IntCons(x / 2, IntCons(x / 3, IntNil()))

def IncrementDeuxcrement(x: Int) =
  IntCons(x + 1, IntCons(x + 2, IntNil()))
```

What parts do they have in common? What parts are different?

Write a single function `ConstructTwo` that abstracts away the common parts of the three functions above, and rewrite all three functions to use it. Note that this function returns another function, which takes an `Int` as a parameter. Can you write it in a way so it returns a single `IntList` instead?

```Scala
def ConstructTwo(f: Int => Int, g: Int => Int): Int => IntList =
  (x: Int) => ???

val DoubleTriple2 = ???
val DivideTrivide2 = ???
val IncrementDeuxcrement2 = ???
```


#### Refactoring Recursive Functions ⭐️
Your task is now to generalize this process of abstraction to the groups of functions that you have identified previously. You can do it in the corresponding files in [src/main/scala/hofs](src/main/scala/hofs).

The general functions that you come up with will need additional parameters, just like in the refactoring exercise. They should be such that you can rewrite all the functions that fit into the categories as special cases of the general functions, by passing a few parameters.

> [!TIP]
> Here is a series of steps to follow:
> 1. Pick a category.
> 2. Highlight the parts that differ between functions of that category.
> 3. Rewrite all the functions to isolate the parts that differ, representing the differences as `val`s.
> 4. Transform the new `val`s into parameters of the function.
> 
> The following hint shows you this process step by step for “associative” (the category that contains sum and product), but don’t look just yet! Try it on your own and with other students first.

<details>
<summary> Hint: Step-by-step example </summary>

1. Let’s pick the “associative” category.
2. There are two differences:
   * The base case (`0` vs `1`)
   * The recursive case (`+` vs `*`)
3. We isolate the `+` and `*` into `val` functions, and the 0 and 1 into simple `vals`: 
   * `l.head + sum(list.tail)` becomes `f(l.head, sum(list.tail))`, where `f` is `(x, y) => x + y`.
   * `l.head * product(list.tail)` becomes `f(l.head, product(list.tail))` where `f` is `((x, y) => x * y)`.
 The result is:

  ```Scala
  def sumRewritten(l: IntList): Int =
    val base = 0
    val f = (x: Int, y: Int) => x + y
    if l.isEmpty then base
    else f(l.head, sumRewritten(l.tail))

  def productRewritten(l: IntList): Int =
    val base = 1
    val f = (x: Int, y: Int) => x * y
    if l.isEmpty then base
    else f(l.head, productRewritten(l.tail))
  ```
4. Finally, we extract `base` and `f` into parameters:
  ```Scala
  def associative(l: IntList, base: Int, f: (Int, Int) => Int): Int =
    if l.isEmpty then base
    else f(l.head, associative(l.tail, base, f))

  def sumDef(l: IntList): Int =
    associative(l, 0, (x, y) => x + y)

  def productDef(l: IntList): Int =
    associative(l, 1, (x, y) => x * y)
  ```
  Alternatively, we could also *curry* the last argument of `associative` to make the definitions of `sum` and `product` more succinct:
  ```Scala
  def associativeCurried(base: Int, f: (Int, Int) => Int)(l: IntList): Int =
    if l.isEmpty then base else f(l.head, associativeCurried(base, f)(l.tail))

  val sumVal = associativeCurried(0, (x, y) => x + y)
  val productVal = associativeCurried(1, (x, y) => x * y)
  ```

Your turn! Once you get used to it, you will find that almost all of last week’s functions can be succinctly reimplemented.
</details><br/>

>[!TIP]
> You will find that sometimes you cannot fully abstract across a whole category, because of types: for example, you will be able to write `countEven` and `multiplyOdd` using a single function, and `allPositiveOrZero` and `allEven` using a single function, but unifying the two will not work, due to mismatched types. It’s possible to unify them using a concept called *polymorphism*, which we will study later.


### Part 4: Conclusion
The higher-order functions that we have discovered today are useful beyond Scala, and have names common across programming languages:
   * “`associative`” (`sum`/`product`), once suitably generalized, is almost identical to “`foldRight`”, so it typically doesn’t have its own name.
   * “`map`” (`increment`/`multiply`) and “`filter`” (`collectEven`/`removeOdd`) are special cases of `foldRight`. They are so useful that they are typically defined (and optimized) separately from `foldRight`, with their own name. They are present in the vast majority of modern programming languages (including JavaScript, Python, and Java).
   * “`foldRight`” (`countEven`/`multiplyOdd`) and “`reduceRight`” (`minMax`/`last`) are similar to each other, but `reduceRight` uses the last element of the list as it’s starting point, and does not work for empty lists.
  
  
Variants of these functions are found in many fields: in data science with MapReduce; in databases with `SELECT/WHERE` (`filter`); in graphics with GPUs and SIMD programs; and in many other computational sciences, where they help process large data sets. There are other such higher-order functions that capture common patterns, which we will explore throughout the course.

The supporting code for this week’s exercise includes definitions of all of these functions, should you want to use them. We found them useful in our own solution.

1. `map` and `filter` are special cases of `foldRight`. Can you rewrite them using `foldRight` instead of direct recursion?

```Scala
def mapAsFoldRight(f: Int => Int): IntList => IntList =
  ???

def filterAsFoldRight(p: Int => Boolean): IntList => IntList =
  ???
```

2. `allEven` and `anyNegative` can be implemented using `foldRight`, but is the result as efficient as the original function? Why or why not?
<details>
<summary> Hint </summary>
Try evaluating each implementation with the substitution model and counting evaluation steps.
</details><br/>

3. Another way to implement `allEven` and `anyNegative` would be to define two functions:
  * one function, `forall`, to check whether a predicate (a function from `Int` to `Boolean`) holds (i.e., evaluates to `true`) for all values in a list, and
  * one function, `exists`, to check whether a predicate holds for any value in a list ([Fun.scala](src/main/scala/hofs/Fun.scala)):
```Scala
def forall(p: Int => Boolean)(l: IntList): Boolean =
  if l.isEmpty then true
  else p(l.head) && forall(p)(l.tail)

def exists(p: Int => Boolean)(l: IntList): Boolean =
  if l.isEmpty then false
  else p(l.head) || exists(p)(l.tail)
```
  1. Rewrite `allEven` and `anyNegative` using `forall`/`exists`.

```Scala 
  def allEven(l: IntList): Boolean =
    if l.isEmpty then true
    else (l.head % 2 == 0) && allEven(l.tail)

  def anyNegative(l: IntList): Boolean =
    if l.isEmpty then false
    else l.head < 0 || anyNegative(l.tail)
```
  2. The two implementations provided above use if with a constant branch (`if … then true else … and if … then false else …`). Can you simplify them to eliminate the `if`s?
  
```Scala 
def forallNoIf(p: Int => Boolean)(l: IntList): Boolean =
  ???

def existsNoIf(p: Int => Boolean)(l: IntList): Boolean =
  ???
```


### Part 5: Going Further
The functions that we have seen today are applicable beyond lists; we will look at this in more depth next week, but you may be interested to get started right away:

1. Look over the tree functions of exercise 2. Do you see patterns and categories similar to the ones we saw for lists? Could you operate the same generalization by defining map, filter, and reduce on trees?

2. Look over the string functions of exercise 2. Are they different from the list functions? Is there anything preventing you to use your list functions on strings?


## Functions as Values

You will know learn how to manipulate functions are values. The material for this part is in [Fun.scala](src/main/scala/hofs/Fun.scala).

### Operations on Functions 

1. Think about the types that you already know in Scala: `Boolean`, `Int`, `String`, … (what else?): each of them has operations that you can use to combine them: `*` on `Int`s, `||` on `Booleans`, `+` on `Strings`, `==` on most types… (what else?)  
Which one of these operations make sense for functions? Do they make sense of all functions, or just for some functions? What could it mean to “or” or “add” two functions together?

<details>
<summary> Hint </summary>

Consider concrete examples: if I have two functions `isOdd` and `isGreaterThan5`, how can I combine them? What operators can I apply to their results?

How about two functions $f: x \mapsto x + 1$ and $g: x \mapsto x²$? The answer should be a bit different from the Boolean case. Why? Consider the argument and return types.
</details><br/>

<details>
<summary> Hint </summary>

Take time to think about what `==` may mean for functions. If we define equality as $f(x) = g(x)$ for all $x$, then could you write a program that checks whether two functions are equal? How long would that program run for?
</details><br/>

2. Can you think of operations that make sense for functions, but not for the other types you know?
<details>
<summary> Hint </summary>

Think again about $f: x \mapsto x + 1$ and $g: x \mapsto x²$. I can add or multiply their results, of course, but what else can I do with them?

Then think about logical negation (not), `isEven`, and `isOdd`. Can I define on in terms of the other two?
</details><br/>

### Function Combinations

#### Composition ⭐️

1. Write a function that takes two functions `f` and `g`, and returns a new function that applies them in sequence (`f`, then `g`). This is called composition and usually written `g ∘ f` in mathematics. In Scala, it is typically written `g 'compose' f` or `f 'andThen' g` (both are built-in):

```Scala
  def andThen(f: Int => Double, g: Double => String) =
  ???
```

2. Can you write variants of compose for other types of functions? `Bool => Bool`, or `String => String`, for example. Does the implementation look different?

3. More generally, assume that f has type `A => B` and g has type `C => D`. Under what conditions can you compose `f` and `g` to form `g ∘ f`? What about `f ∘ g`?
  

#### Identity

0 is the neutral element for `+`: $0 + x = x + 0$ for all $x$. 1 is the neutral element for `*`. What is the neutral element for compose? That is, which function id is such that `id ∘ f ≡ f ∘ id ≡ f` for all `f`? (we use `f ≡ g` here to mean that `f(x) == g(x)` for all `x`).

```Scala
val id: Int => Int = ???
```

#### Flip

1. Define a function `flip`. It takes a function and returns the same function, but with the arguments flipped.

  ```Scala
  def flip(f: (Int, Int) => Int): (Int, Int) => Int =
    ???
  ```

2. What happens if you compose `flip` with itself (in other words, what does `flip ∘ flip` do?)

#### Refactoring with Composition ⭐️

Higher-order functions are often very useful to capture repeated patterns. For example, here are some closely related functions:
```Scala
val squareMinusOne     = (x: Int) => (x - 1) * (x - 1)
val squarePlusOne      = (x: Int) => (x + 1) * (x + 1)
val squareSquare       = (x: Int) => (x * x) * (x * x)
val squareMinusTwo     = (x: Int) => (x - 2) * (x - 2)
val squareSquareSquare = (x: Int) =>
  ((x * x) * (x * x)) * ((x * x) * (x * x))
```

Can you define these functions in terms of the following ones?

```Scala
val square = (x: Int) => x * x
val plusOne = (x: Int) => x + 1
val minusOne = (x: Int) => x - 1
def composeInt(f: Int => Int, g: Int => Int): Int => Int =
  x => f(g(x))
```

#### Lifting ⭐️

1. Write a function that takes two functions `Int => Int` and returns a new function `Int => Int` whose results are the sum of the results of the first two functions.
```Scala
def adder(f: Int => Double, g: Int => Double): Int => Double =
  ???
```

<details>
<summary> Hint </summary>

The result should be such that `adder(f, g)(x) == f(x) + g(x)`.
</details><br/>

This is called a *lifted* version of `+`. In mathematics, this is the usual definition of `+` on functions.

2. Can you do the same for `*`, `-`, `/`, other operators? Do you notice any similarities?

```Scala
def multiplier(f: Int => Double, g: Int => Double): Int => Double =
  ???
```

3. We saw previously that 0 is the neutral element of `+`. What is the neutral element of `adder`? In other words, what function is such `adder(f, g) ≡ f`?

#### Heavy Lifting

Let’s extract the common code between the functions in the previous exercise.

1. Write a function that takes a single function `op` (a binary operator such as `+`) and returns a lifted version of that operation (like adder above).
```Scala
def lifter(op: (Double, Double) => Double): (Int => Double, Int => Double) => (Int => Double) =
  ???
```

<details>
<summary> Hint </summary>

`lifter((x, y) => x + y)` should be the same as `adder`. Look for the common parts in the implementation, and extract the ones that vary between them.
</details><br/>

2. Rewrite `adder` and other related functions in terms of this one.
  

#### Multi-lifting

1. Write a lifted version of the boolean `&&` (and) operator.
```Scala
def meet(f: Int => Boolean, g: Int => Boolean): (Int => Boolean) =
  ???
```

<details>
<summary> Hint </summary>

This function should be such that `meet(f, g)(x) == f(x) && g(x)`.
</details><br/>

Functions that return booleans are often called “predicates” (in other words, `f: Int => Boolean` is a predicate, and `meet` above combines two predicates into one).

2. 🔜 Generalize `meet` to accept more than one predicate. That is, write a function `Meet` which, given a list of predicates, returns a single predicate that lifts `&&` across all of the predicates.
```Scala
def Meet(l: IntPredicateList): (Int => Boolean) =
  ???
```

### Values as Functions: `def`s, `val`s and Currying

The idea of currying is to change (slightly) the way a function is invoked (called) to make it easier to use. Let us explore this on two examples:

#### Translating Between `def`s and `val`s (named and anonymous functions) ⭐️

Using the template below, rewrite (and show how to call) each of the following functions:

```Scala
def isGreaterThanBasic(x: Int, y: Int): Boolean =
  x > y
val isGreaterThanAnon: (Int, Int) => Boolean =
  (x, y) => x > y
val isGreaterThanCurried: Int => Int => Boolean =
  x => y => x > y // Same as `x => (y => x > y)`
def isGreaterThanCurriedDef(x: Int)(y: Int): Boolean =
  x > y

// How to call:
//   For all x, y:
//     isGreaterThan(x, y)
//       == isGreaterThanAnon(x, y)
//       == isGreaterThanCurried(x)(y)
//       == isGreaterThanCurriedDef(x)(y)
```

1. `incrHeadByX`

```Scala
def incrHeadByXBasic(x: Int, l: IntList): IntList =
  if l.isEmpty then l
  else IntCons(l.head + x, l.tail)

val incrHeadByXAnon: (Int, IntList) => IntList =
  ???

val incrHeadByXCurried: Int => IntList => IntList =
  ???

def incrHeadByXCurriedDef(x: Int)(l: IntList): IntList =
  ???
```

2. `addToFront`
```Scala
def addToFrontBasic(x: Int, y: Int, l: IntList): IntList =
  IntCons(x, IntCons(y, l))

val addToFrontAnon: (Int, Int, IntList) => IntList = ???

val addToFrontPartlyCurried: (Int, Int) => IntList => IntList = ???

val addToFrontCurried: Int => Int => IntList => IntList = ???

def addToFrontCurriedDef(x: Int)(y: Int)(l: IntList): IntList = ???
```

3. `contains`
```Scala
def containsBasic(l: IntList, n: Int): Boolean =
  !l.isEmpty && (n == l.head || contains(l.tail, n))

def containsAnon: (IntList, Int) => Boolean = ???

def containsCurried: IntList => Int => Boolean = ???

def containsCurriedDef(l: IntList)(n: Int): Boolean = ???
```
>[!NOTE]
> It is debatable whether `containsAnon` is really anonymous, since it must use its own name (the one being defined in the def) for the recursive call. This recursion issue is also why we use def instead of val here.

4. `headHasProperty` (from “Part 3”, above)
```Scala
def headHasPropertyBasic(p: Int => Boolean, l: IntList): Boolean =
  !l.isEmpty && p(l.head)

val headHasPropertyAnon: ((Int => Boolean), IntList) => Boolean = ???

val headHasPropertyCurried: (Int => Boolean) => IntList => Boolean = ???

def headHasPropertyCurriedDef(p: Int => Boolean)(l: IntList): Boolean = ???
```
Finally, using this new `headHasPropertyCurried`, rewrite `headIsEven` and `headIsPositive.` Which version is shorter?
```Scala
def headIsEven(l: IntList): Boolean =
  !l.isEmpty && l.head % 2 == 0
def headIsPositive(l: IntList): Boolean =
  !l.isEmpty && l.head > 0
```

```Scala
val headIsEven2 = ???

val headIsPositive2 = ???
```

#### Currying Container Functions ⭐️

Now let’s see how these different styles can help:

Let’s assume we have a list of id numbers registered for this course (students and staff), and a separate list for just the staff. Thus, registered students are those that occur in the first list but not the second one:

```Scala
val PFAll =
  IntCons(123456, IntCons(654321, IntCons(111222, IntCons(333444, IntCons(555666, IntCons(787878, IntNil()))))))

val PFStaff =
  IntCons(654321, IntCons(333444, IntNil()))
```

1. Write a function `isRegisteredForPF` that checks whether a given id number appears in the `PFAll` list. Write two versions: one using `containsBasic`, as a `def`; and one using `containsCurried`, as a `val`. For the `val`, do not create an anonymous function: the definition should have no mention of a id number variable.
```Scala
def isRegisteredForPFDef(id: Int): Boolean = ???

val isRegisteredForPFVal = ???
```
2. Write a function `isPFStudent` that checks whether a id number corresponds to a registered student. Write two versions: one using `containsBasic`, as a def; and one using `containsCurried`, `notLifter`, and `andLifter`, as a val. For the `val`, as before, do not create an anonymous function: the definition should have no mention of an id number variable.

```Scala
def isPFStudentDef(id: Int): Boolean =
  ???
```

```Scala
def andLifter(f: Int => Boolean, g: Int => Boolean): Int => Boolean =
  n => f(n) && g(n)

def notLifter(f: Int => Boolean): Int => Boolean =
  n => !f(n)

val isPFStudentVal = ???
```
The val style is often called “point-free style”, which means using only function combinators like `andLifter` and `notLifter` instead of explicit parameter names.

3. Notice that the two versions of the function above will always scan both lists. Using the `difference` function on lists, write a more general, curried function that takes two lists, computes the difference, and then returns a function that takes a id number and validates it against the resulting list. Make sure that the list difference is computed once. Can you do it in point-free style, with no anonymous functions?

```Scala
def isCourseStudentDefPartlyCurried(all: IntList, staff: IntList): Int => Boolean =
  ???
``` 

### Equality 🔥

Mathematicians generally say that two functions are “equal” when they have the same outputs for all inputs.

1. By this definition, which of the following functions are equal?
```Scala 
  val f0 = (x: Long) => x
  val f1 = (x: Long) => if x > 0 then x else -x
  val f2 = (x: Long) => x + 1 - 1
  val f3 = (x: Long) =>
    Math.sqrt(x.toDouble * x.toDouble).round
  val f4: Long => Long = x =>
    if x < 0 then f4(x + 1) - 1
    else if x > 0 then f4(x - 1) + 1
    else 0
```

2. Can you define a function that checks whether two functions of type `Boolean => Boolean` are equal?
```Scala
def eqBoolBool(
    f: Boolean => Boolean,
    g: Boolean => Boolean
) =  ???
``` 
What about functions of the following types?

* `Int => Boolean`
* `Boolean => Int`
* `IntList => Boolean`
* `Boolean => IntList`
* `Int => Boolean => IntList`

3. Can you come up with a general result? For which types `A` and `B` can one write a function `eqAB` that checks whether two functions of type `A => B` return the same outputs for all inputs?
4. What do you think of this definition of equality? Is `f4` really “the same” as `f0`, or do they differ in any way?

### Fixed Points 🔥

1. A value `x` is a *fixed point* of `f` if `f(x) == x`. Do the following functions have fixed points ? If so, which one(s)?
```Scala
val a = (x: Int) => x
val b = (x: Int) => -x
val c = (x: Int) => x + 1
val d = (x: Int) => (x / 2) + 5
val e = (x: Int) => if x % 10 == 0 then x else (x + 1)
val f = (x: Int) => -(x * x)
val g = (x: Int) => /* 🔥 */ /* assuming x > 0 */
  if x == 1 then 1
  else if x % 2 == 0 then x / 2
  else 3 * x + 1
```

2. Write a function `fixedPoint` that takes a function `f` and an integer `x`, checks whether `x` is already a fixpoint of `f`, and then looks for a fixed point by repeatedly calling `f`, until it converges.
```Scala
  def fixedPoint(f: Int => Int, x: Int): Int =
    ???
``` 
For example, `fixedPoint(((x: Int) => x / 2 + 5), 20)` will call itself recursively with `x = 15` (`20 / 2 + 5`), then `x = 12` (`15 / 2 + 5`), then `x = 11` (`12 / 2 + 5`), then `x = 10` (`11 / 2 + 5`).

3. For each of the following expressions, indicate whether it terminates, and if so, what value is returned:

* `fixedPoint(((x: Int) => x / 2), 4)` 
* `fixedPoint(((x: Int) => -x), 3)`
* `fixedPoint(((x: Int) => x), 123456)` 
* `fixedPoint(((x: Int) => x + 1), 0)`
* `fixedPoint(((x: Int) => if (x % 10 == 0) then x else x + 1), 35)` 
* `fixedPoint(((x: Int) => x / 2 + 5), 20)`

What happens when there is no fixed point? Does `fixedPoint` work for all functions above that have a fixed point? Does it depend on the starting value of `x`?

4. More generally, for which functions and which inputs does `fixedPoint` work?
