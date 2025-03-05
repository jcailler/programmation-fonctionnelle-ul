import scala.io.Source

object HuffmanParser {
  def parseHuffmanFile(filename: String): Unit = {
    val source = Source.fromFile(filename)

    try {
      val lines = source.getLines().toList
      if (lines.isEmpty) {
        println("Error: File is empty!")
        return
      }

      // Read header information
      val header = lines.headOption.getOrElse("")
      if (!header.startsWith("HUF")) {
        println("Error: Invalid .huf file format!")
        return
      }
      println(s"File Format: $header")

      // Extract symbol frequencies
      val symbolLine = lines.find(_.startsWith("SYMBOLS:")).getOrElse("")
      val symbolPattern = "'(.*?)':([01]+)|([^: ]):([01]+)".r

      val symbolMap = symbolPattern.findAllMatchIn(symbolLine.stripPrefix("SYMBOLS:").trim)
        .map { m =>
          val symbol = 
            if (m.group(1) != null) {
              // Handle quoted symbols like ' ' and '\n'
              m.group(1) match {
                case "\\n" => '\n' // Convert the escaped newline correctly
                case " "  => ' '  // Preserve space as a character
                case other => other.head 
              }
            } else {
              // Handle normal single characters (e.g., r, e, s)
              m.group(3).head
            }

          val code = if (m.group(2) != null) m.group(2) else m.group(4)
          symbol -> code
        }
        .toMap

      println("Symbol Frequencies:")
      symbolMap.foreach { case (symbol, code) =>
        val displaySymbol = symbol match {
          case ' '  => "' '"   
          case '\n' => "'\\n'" 
          case _    => symbol.toString
        }
        println(s"$displaySymbol -> $code")
      }

      // Extract encoded data
      val dataLine = lines.find(_.startsWith("DATA:")).getOrElse("")
      val encodedData = dataLine.stripPrefix("DATA:").trim

      println(s"Encoded Data: $encodedData")

    } catch {
      case e: Exception => println(s"Error reading file: ${e.getMessage}")
    } finally {
      source.close()
    }
  }
}
