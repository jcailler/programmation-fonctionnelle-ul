# Functional Programming - TP 1: Scala Basics

The goal of this exercise is to familiarize yourself with the tools and environment of this course by designing some functions related to conversion between Celsius and Fahrenheit degrees.

For each exercise below, we provide the signature of the functions you need to write. The body is replaced with `???`: that's the part you need to come up with!  _We strongly recommend that you think about these exercises on paper first_.

Exercises marked with ⭐️ are the most important ones.
Exercises marked with 🔥 are the most challenging ones.

You do not need to complete all exercises to succeed in this class, and you do not need to do all exercises in the order they are written.

**You are allowed to copy/clone/fork this repository, but not to share solutions of the exercise in any public repository or web page.**


## Obtaining the Exercise Files
* Go to the following page: [https://github.com/jcailler/programmation-fonctionnelle-ul.git](https://github.com/jcailler/programmation-fonctionnelle-ul.git).
* Clone or fork the repository: `git clone https://github.com/jcailler/programmation-fonctionnelle-ul.git` or download it by clicking on the green `<code>` button and selecting `download`.
* This will create a directory named `programmation-fonctionnelle-ul`
* Then, go to `TP/TP1`, where the `degrees` project is located. You can change your working directory by running the change directory (cd) command: `cd programmation-fonctionnelle-ul/TP/TP1`.


## The Project Structure
Now that we’ve obtained the project, let’s take a look at its structure:
```
.
├── build.sbt
├── project
│   └── ...
└── src
    ├── main
    │   └── scala
    │       ├── degrees
    │       │   ├── cli
    │       │   │   └── main.scala
    │       │   └── convert.scala
    │       └── examples.worksheet.sc
    └── test
        └── scala
            └── degrees
                └── ConvertTest.scala
``` 
* All the files ending with `.sbt` or in the `project/ directory` are build tool configuration files: you don’t need to modify them yet. We will discuss them later in the course.
* The material for this exercise is in `src/main/scala/`.
* The sources of the unit tests are in `src/test/scala/`. You will need to make all the tests pass to complete the exercise, and you should write additional tests to check for cases that our tests do not cover.

## Using SBT and Running the Tests

SBT stands for Scala Build Tool, it’s a dependency manager and build tool for Scala. We will use it to compile our code and run the tests.

Start SBT by positioning yourself in `programmation-fonctionnelle-ul/TP/TP1/degrees/` and run the following command:  
```
$ sbt
```

This command has to be run in the project’s root directory (in our case, ``programmation-fonctionnelle-ul/TP/TP1/degrees/`, but in the general case it is the directory that you cloned from git and that contains the `build.sbt` file).

Once it’s finished starting (this may take a while), you’ll be able to enter SBT commands:

* You can compile your project using the `compile` command.
* You can run the tests with the `test` command (this automatically compiles your code if needed to). Note that if compilation fails, no tests will be run.
* You can exit SBT using the `exit` command.
* The first time you’ll run `test` in an exercise project you should see many errors: that’s normal, your job is to make the tests pass!


Let’s look at the output of the test command in more detail:
```Make
$ sbt
[info] welcome to sbt 1.9.4 (Eclipse Adoptium Java 17)
[info] loading settings for project degrees-build-build-build from metals.sbt ...
[info] loading project definition from /Users/username/degrees/project/project/project
[info] loading settings for project degrees-build-build from metals.sbt ...
[info] loading project definition from /Users/username/degrees/project/project
[success] Generated .bloop/degrees-build-build.json
[success] Total time: 0 s, completed Sep 15, 2023, 7:51:32 PM
[info] loading settings for project degrees-build from metals.sbt,plugins.sbt ...
[info] loading project definition from /Users/username/degrees/project
[success] Generated .bloop/degrees-build.json
[success] Total time: 0 s, completed Sep 15, 2023, 7:51:32 PM
[info] loading settings for project degrees from build.sbt ...
[info] set current project to degrees (in build file:/Users/username/degrees/)
[info] sbt server started at local:///Users/username/.sbt/1.0/server/1e514a29bba44b44029e/sock
[info] started sbt server
sbt:degrees> test
[info] compiling 2 Scala sources to /Users/username/degrees/target/scala-3.3.1/classes ...
[info] compiling 1 Scala source to /Users/username/degrees/target/scala-3.3.1/test-classes ...
degrees.ConvertTest:
  + celsiusToFahrenheit(0) should be 32  0.007s
  + celsiusToFahrenheit(100) should be 212  0.001s
==> X degrees.ConvertTest.fahrenheitToCelsius(32) should be 0  0.012s munit.ComparisonFailException: /Users/username/degrees/src/test/scala/degrees/ConvertTest.scala:13 values are not the same expected: 0.0 but was: 42.0
12:  test("fahrenheitToCelsius(32) should be 0"):
13:    assertEqualsDouble(fahrenheitToCelsius(32), 0.0, DELTA)
14:
    at munit.Assertions.failComparison(Assertions.scala:274)
==> X degrees.ConvertTest.fahrenheitToCelsius(212) should be 100  0.0s munit.ComparisonFailException: /Users/username/degrees/src/test/scala/degrees/ConvertTest.scala:16 values are not the same expected: 100.0 but was: 42.0
15:  test("fahrenheitToCelsius(212) should be 100"):
16:    assertEqualsDouble(fahrenheitToCelsius(212), 100.0, DELTA)
17:
    at munit.Assertions.failComparison(Assertions.scala:274)
==> X degrees.ConvertTest.celsiusToFahrenheit and fahrenheitToCelsius should be inverse functions (2pts)  0.003s munit.ComparisonFailException: /Users/username/degrees/src/test/scala/degrees/ConvertTest.scala:20 values are not the same expected: -100.0 but was: 42.0
19:    for v <- -100 to 100 do
20:      assertEqualsDouble(fahrenheitToCelsius(celsiusToFahrenheit(v)), v.toDouble, DELTA)
21:      assertEqualsDouble(celsiusToFahrenheit(fahrenheitToCelsius(v)), v.toDouble, DELTA)
    at munit.Assertions.failComparison(Assertions.scala:274)
[error] Failed: Total 5, Failed 3, Errors 0, Passed 2
[error] Failed tests:
[error]         degrees.ConvertTest
[error] (Test / test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 4 s, completed Sep 15, 2023, 7:51:51 PM
```

This tells us several things:

* The tests run are from the class `ConvertTest` in the `degrees` package.
* The two first tests passed successfully.
* The three last tests failed. That’s why they are displayed in red and start prefixed with `==> X`.
* The failures were due to `munit.ComparisonFailException` errors.
* You’ll need to go ahead and implement the `fahrenheitToCelsius` function to fix these test failures!

## Fixing the Tests

### Open VSCode
If it is not already done, open VSCode and go to the `programmation-fonctionnelle-ul/TP/TP1/degrees/` folder. The first time the IDE starts, it will take some time to download more components. Eventually it will ask you to import the build, please click “Import build”. You’ll need to wait a bit for the import to finish, if an error appears try closing and restarting VS Code in the same way we started it above.

### Understand the Logs
You are now going to fix the tests! Earlier we mentioned a failing test, the stack trace told us that it was failing on line `10` of the `file src/test/scala/degrees/ConvertTest.scala.`

Here is the source code of the failing test:

```Scala
test("fahrenheitToCelsius(32) should be 0"):
  assertEqualsDouble(fahrenheitToCelsius(32), 0.0, DELTA)
```
<div style="text-align: right; color:grey"> 

[degrees/src/test/scala/degrees/ConvertTest.scala](./degrees/src/test/scala/degrees/ConvertTest.scala)
</div>

The first line gives a name to the test. The second line runs `fahrenheitToCelsius(32)` and tests that it equals `0.0` (up to some delta, because we are dealing with floating-point numbers), but in our case, we never got to this point because an exception was thrown, recall that the second line of the stack trace was:

```CMake
==> X degrees.ConvertTest.fahrenheitToCelsius(32) should be 0  0.012s munit.ComparisonFailException: /Users/username/degrees/src/test/scala/degrees/ConvertTest.scala:13 values are not the same expected: 0.0 but was: 42.0
```

This tells us that the exception was thrown happened when calling the `fahrenheitToCelsius` function when testing the output of `fahrenheitToCelsius(32)`, in the `ConvertTest.scala` file at line `13`.

We can hover with our mouse over the call to `fahrenheitToCelsius` in the test method to get more information on it:
```Scala
def fahrenheitToCelsius(fahrenheit: Double): Double
/** Converts a temperature in Fahrenheit degrees to Celsius */
```

<details>
<summary> I don't see that, why? </summary>

VSCode and Metals do not like to have multiple Scala project in the same workspace. If you want to be able to use Metals and all its features, you should stick to one open Scala project per workspace.

If it does not solve your issue, it’s likely that the build wasn’t imported, we can import it manually:

Click on the “m” logo in the left bar and then in the sidebar that appears, click on “Import build”, then wait a bit.

If things still don’t work, try restarting VS Code. If you’re still having issues, try clicking on “Clean compile” from the same sidebar.
</details><br/>

The hover is split into two parts: the first part is:

```Scala
def fahrenheitToCelsius(fahrenheit: Double): Double
```
This means that `fahrenheitToCelsius` is a method that takes a `Double` argument and returns a `Double`. The second part is the documentation of `fahrenheitToCelsius`. We can jump to the definition of `fahrenheitToCelsius` by `Ctrl+click` (`Cmd+click` on Mac) or by `right click -> Go to Definition`. Once there we see:

```Scala
/** Converts a temperature in Fahrenheit degrees to Celsius */
def fahrenheitToCelsius(fahrenheit: Double): Double =
  42.0
```
<div style="text-align: right; color:grey"> 

[degrees/src/main/scala/degrees/convert.scala](./degrees/src/main/scala/degrees/convert.scala)
</div>

Now we know why the test failed: `fahrenheitToCelsius` always returns `42.0`!

Once you’ve fixed this method, you can run `test` from sbt again to see if the test passed.

<details>
<summary> Solution </summary>

```Scala
/** Converts a temperature in Fahrenheit degrees to Celsius */
def fahrenheitToCelsius(fahrenheit: Double): Double =
  (fahrenheit - 32.0) * (5.0/9.0)
```
</details><br/>

If you want to run a single test instead of all tests, you can use the `testOnly` command in the SBT shell to match the name of the test. For example:

```Make
testOnly -- "*fahrenheitToCelsius(32)*"
```

This will match and run the single test `degrees.ConvertTest.fahrenheitToCelsius(32) should be 0`. (The first `*` means anything can appear before `fahrenheitToCelsius(32)` and the second `*` means anything can appear after.)


## Running your Code

Writing code and running tests is nice, but sometimes more direct feedback is useful, like when you want to experiment with Scala or try out some methods that you implemented. You can do this with a worksheet using the Scala REPL or by a `main` function.

### The Worksheet Mode

A *worksheet* is a file where every line of code written in the IDE is executed and its output is displayed as a comment. Any file that ends in `.worksheet.sc` in `src/main/scala` is considered to be a worksheet by the IDE.

There is a worksheet in this example project at `src/main/scala/degrees/examples.worksheet.sc`. You can open it by clicking on it in the file explorer on the left.

Inside this file, you can type any Scala code. The worksheet will be automatically run when the code is saved. Each line of code will be executed one by one and its output will be shown in green on the right.

If your IDE is configured correctly, you should see the following initial output:
```Scala
// Import the functions `celsiusToFahrenheit` and `fahrenheitToCelsius` from the
// `degrees` package.
import degrees.{celsiusToFahrenheit, fahrenheitToCelsius}

// As we are in a worksheet (a file ending with `.worksheet.sc`), if your IDE is
// setup correctly, you should see the type and values of x1, x2, and x3
// displayed in green comments. If that's not the case, ask someone from the
// teaching team to help you.
val x1 = celsiusToFahrenheit(0.0) // : Double = 32.0
val x2 = fahrenheitToCelsius(32.0) // : Double = 0.0
val x3 = fahrenheitToCelsius(42.0) // : Double = 5.555555555555

// You can write any Scala code in a worksheet, including defining functions.
// Let's define a function `hello` that takes a `String` as argument and returns
// a `String`:
def hello(name: String): String =
  s"Hello $name!"

// And try to call it. You should see the results `Hello World!` and `Hello
// Ada!` displayed in green comments next to the two calls below.
hello("World") // : String = Hello World!
hello("Ada") // : String = Hello Ada!
```

### The REPL

If you are more of a terminal person, you can also use the Scala *REPL* for similar purposes. REPL stands for Read-Eval-Print-Loop, it’s a program that reads Scala expressions, evaluates them, prints the result, and then loops back to read the next expression.

After having started SBT, you can start the REPL by typing `console`, you will see the following prompt:

```Scala
scala>
```

At this , you can write any Scala expression you want, for example:

```Scala
scala> val x = 41 + 1
val x: Int = 42
```

If you write an expression without wrapping it in a `val` or a `def`, the REPL will give it a name for you, starting with `res`:
```Scala
scala> 41 + 1
val res0: Int = 42

scala> 41.5 + 0.5
val res1: Double = 42.0

scala> "Hello".isBlank
val res2: Boolean = false
```

The functions and classes of the lab are available inside the REPL, so you can for instance import all the methods `fahrenheitToCelsius` from the `degrees` package by typing:
```Scala
scala> import degrees.fahrenheitToCelsius

scala> fahrenheitToCelsius(0.0)
val res4: Double = 42.0
```

You can enter a multi-line expression in the REPL by using Alt+Enter instead of Enter:

```Scala
scala> if 1 == 1 then
     |   "a"
     | else
     |   "b"
val res5: String = a
```

In order to exit the Scala REPL and go back to sbt, type `Ctrl+D`.

### Running a Main Function

The last way to run your code is to run a *main function*. A main function is a method aimed at being called from the command line. It is similar to a `main` method in Java. We will come back to this later in the course.

In this exercise, there is a main called `main` defined for you in the `cli` package ([degrees/src/main/scala/degrees/cli/main.scala](./TP/TP1/scr/degrees/src/main/scala/degrees/cli/main.scala)). You do not need to understand or modify this code yet. 

However, know that you can run this main function by typing `run` in the SBT console, followed by the arguments expected by this method. In this exercise, the main function defined for you expects two arguments: the first one is the temperature to convert, and the second one is the unit of the temperature to convert. For example:

```Make
sbt:degrees> run 30 c
[info] running degrees.main 30 c
30°C = 86.00°F
[success] Total time: 1 s, completed Sep 14, 2023, 7:46:27 PM
sbt:degrees> run 20 f
[info] running degrees.main 20 f
20°F = 42.00°C
[success] Total time: 0 s, completed Sep 14, 2023, 7:47:32 PM
```

If you run the method with the wrong number of arguments, or with invalid arguments, you will get an error:

```Make
sbt:degrees> run
[info] running degrees.main 
Illegal command line: more arguments expected

sbt:degrees> run 40 m
[info] running degrees.main 40 m
[error] java.lang.IllegalArgumentException: Unknown unit m
[error] 	at degrees.main$package$.main(main.scala:9)
...
```

## Bob and his Personal Stylist Assistant

Bob is a 3rd year student in this class. He tried to do the exercises, just like you, but his code doesn't seem to pass the tests: 

```Scala
def fahrenheitToCelsius(fahrenheit: Double): Double =
  (fahrenheit - 32) * (5/9)
```

Can you help him to understand the issue?

<details>
<summary> Solution </summary>

Bob uses integers instead of double to compute `5/9`: 

```Scala
scala> (5/9)
val res0: Int = 0

scala> (5.0/9.0)
val res1: Double = 0.5555555555555556
```
</details><br/>

Thanks to your help, Bob was completely amazed by the power of Scala. He decided to use this power in order to help him to choose what to wear in the morning. So far, he created a file `stylistAssistant` in `degrees/src/main/scala/degrees/stylistAssistant.scala` and wrote the following code: 

```Scala
package degrees


/*** Tells you what to wear today */
def shouldIWearAJacket(temperature: Double) : String = 
```

Unfortunately, he struggles to write the rest of the code. Help Bob to know what to wear! Bob needs to wear a jacket if the temperature is (strictly) below 20°C, and no jacket otherwise. 

<details>
<summary> Solution </summary>

```Scala
/*** Tells you what to wear today */
def shouldIWearAJacket(temperature: Double) : String = 
  if temperature < 20.0
    then "Yes"
    else "No"
```
</details><br/>


Now, let us design test cases for our function! To do so, create a new file called `stylistAssistantTest` in `degrees/src/test/scala/degree` and write the following code: 


```Scala
package degrees

class StylistAssistantTest extends munit.FunSuite:
```

You can now add a test case for the function `shouldIWearAJacket`. Write one test where Bob is supposed to wear a jacket and one where it is not mandatory. 

<details>
<summary> Solution </summary>

```Scala
class StylistAssistantTest extends munit.FunSuite:
  test("shouldIWearAJacket(12.0) should be `Yes`"):
    assertEquals(shouldIWearAJacket(12.0), "Yes")

  test("shouldIWearAJacket(25.0) should be `No`"):
    assertEquals(shouldIWearAJacket(25.0), "No")
```
</details><br/>

Bob showed its program to his American friend Alice, who was also completely amazed by the power of Scala. Unfortunately, when she tried it, she caught a cold. Why? 

<details>
<summary> Solution </summary>

The function was designed to work with Celsius degrees, but Alice used it with Fahrenheit degrees. 
</details><br/>

To allow Alice to use the stylist assistant, Bob decided to modify its function to accept either Celsius or Fahrenheit. Define a function `shouldIWearAJacketDegreeOrFahrenheit` that modifies the previous function by adding a string parameter corresponding to the temperature unit ("C" of "F"). Write the corresponding test cases.

<details>
<summary> Solution </summary>

The function can be defined as follows:

```Scala

/*** Tells you what to wear today given a temperature unit */
def shouldIWearAJacketDegreeOrFahrenheit(temperature: Double, unit: String) : String = 
  if unit == "C" 
    then {
      if temperature < 20.0
        then "Yes"
        else "No"
    }
    else {
      if unit == "F"
      then {
          if temperature < celsiusToFahrenheit(20.0)
            then "Yes"
            else "No"
      }
      else "Unknown unit"
    }
```

And the test cases: 

```Scala
test("shouldIWearAJacketDegreeOrFahrenheit(12.0, C) should be `Yes`"):
  assertEquals(shouldIWearAJacketDegreeOrFahrenheit(12.0, "C"), "Yes")

test("shouldIWearAJacketDegreeOrFahrenheit(25.0, C) should be `No`"):
  assertEquals(shouldIWearAJacketDegreeOrFahrenheit(25.0, "C"), "No")

test("shouldIWearAJacketDegreeOrFahrenheit(30.0, F) should be `Yes`"):
  assertEquals(shouldIWearAJacketDegreeOrFahrenheit(30.0, "F"), "Yes")

test("shouldIWearAJacketDegreeOrFahrenheit(70.0, F) should be `No`"):
  assertEquals(shouldIWearAJacketDegreeOrFahrenheit(70.0, "F"), "No")

test("shouldIWearAJacketDegreeOrFahrenheit(70.0, X) should be `Unknown unit`"):
  assertEquals(shouldIWearAJacketDegreeOrFahrenheit(25.0, "X"), "Unknown unit")
```

</details><br/>


Alice is now able to use this program too! However, 20°C is way too cold for her, who prefers to wear a jacket while the temperature is not at least 25°C. Define a new function `shouldIWearAJacketWithThreshold` that takes the threshold as a parameter, and design the corresponding test cases. We assume that both the temperature and the threshold are given in the same unit. 


<details>
<summary> Solution </summary>

```Scala

/*** Tells you what to wear today with parametrizable threshold */
def shouldIWearAJacketWithThreshold(temperature: Double, threshold: Double) : String = 
  if temperature < threshold
    then "Yes"
    else "No"
```

And the test cases: 

```Scala
test("shouldIWearAJacketWithThreshold(12.0, 30.0) should be `Yes`"):
  assertEquals(shouldIWearAJacketWithThreshold(12.0, 30.0), "Yes")

test("shouldIWearAJacketWithThreshold(12.0, 10.0) should be `No`"):
  assertEquals(shouldIWearAJacketWithThreshold(12.0, 10.0), "No")
```

Life is simpler when you define your own standards, isn't it? 

</details><br/>

Thank you for helping Bob and Alice! You can play around with those functionalities until you get familiar enough with the environment (why not try to use them in `main` for instance?). Once all your tests have passed, you are done with this first exercise!
