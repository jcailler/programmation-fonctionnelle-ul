package degrees

class ConvertTest extends munit.FunSuite:
  val DELTA = 0.0001

  test("celsiusToFahrenheit(0) should be 32"):
    assertEqualsDouble(celsiusToFahrenheit(0), 32.0, DELTA)

  test("celsiusToFahrenheit(100) should be 212"):
    assertEqualsDouble(celsiusToFahrenheit(100), 212.0, DELTA)

  test("fahrenheitToCelsius(32) should be 0"):
    assertEqualsDouble(fahrenheitToCelsius(32), 0.0, DELTA)

  test("fahrenheitToCelsius(212) should be 100"):
    assertEqualsDouble(fahrenheitToCelsius(212), 100.0, DELTA)

  test("celsiusToFahrenheit and fahrenheitToCelsius should be inverse functions"):
    for v <- -100 to 100 do
      assertEqualsDouble(fahrenheitToCelsius(celsiusToFahrenheit(v)), v.toDouble, DELTA)
      assertEqualsDouble(celsiusToFahrenheit(fahrenheitToCelsius(v)), v.toDouble, DELTA)
