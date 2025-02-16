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
      val symbolMap = symbolLine.stripPrefix("SYMBOLS:").trim
        .split(" ")
        .map(_.split(":"))
        .collect { case Array(sym, freq) => sym.head -> freq.toInt }
        .toMap

      println("Symbol Frequencies:")
      symbolMap.foreach { case (char, freq) => println(s"  $char -> $freq") }

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
