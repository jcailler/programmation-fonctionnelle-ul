object Main {
  def main(args: Array[String]): Unit = {

    val fileName = "src/test/huf/test.huf"
    HuffmanParser.parseHuffmanFile(fileName) // Call the parser
  }
}

// Or in command line 
/** object Main {
  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("Usage: run <filename.huf>")
    } else {
      val filename = args(0)
      println(s"Parsing file: $filename")
      HuffmanParser.parseHuffmanFile(filename)
    }
  }
} **/