package degrees

@main def main(value: Int, unit: String) =
  unit match
    case "F" | "f" | "fahrenheit" => println(f"$value°F = ${fahrenheitToCelsius(value)}%1.2f°C")
    case "C" | "c" | "celsius"    => println(f"$value°C = ${celsiusToFahrenheit(value)}%1.2f°F")
    case _                        => throw IllegalArgumentException(s"Unknown unit $unit")
