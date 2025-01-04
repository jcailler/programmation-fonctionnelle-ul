// Import the functions `celsiusToFahrenheit` and `fahrenheitToCelsius` from the
// `degrees` package.
import degrees.{celsiusToFahrenheit, fahrenheitToCelsius}

// As we are in a worksheet (a file ending with `.worksheet.sc`), if your IDE is
// setup correctly, you should see the type and values of x1, x2 and x3
// displayed in green comments. If that's not the case, ask someone from the
// teaching team to help you.
val x1 = celsiusToFahrenheit(0.0)
val x2 = fahrenheitToCelsius(32.0)
val x3 = fahrenheitToCelsius(42.0)

// You can write any Scala code in a worksheet, including defining functions.
// Let's define a function `hello` that takes a `String` as argument and returns
// a `String`:
def hello(name: String): String =
  s"Hello $name!"

// And try to call it. You should see the results `Hello World!` and `Hello
// Ada!` displayed in green comments next to the two calls below.
hello("World")
hello("Ada")
