package recursion

/*
 * Here you find a framework for the exercises on recursion.
 * The exercises are linked with different topics which you can
 * check out in the README file of the exercise.
 */

// Recursion on Strings

// Implement a function that computes the length of a string:

def stringLength(s: String): Int ={
  ???
}

/*
 * Implement a function that capitalizes every character in a string:
 * You will need the methods toUpper and toString of Char:
 *
 * scala> 'c'.toUpper
 * val res30: Char = C
 * 
 * scala> 'c'.toString
 * val res31: String = c
 */

def capitalizeString(s: String): String ={
  ???
}

/*
 * In this exercise, we call “word” any sequence of non-whitespace characters.
 * For example " Bonjour ! " has two words. Implement a function that
 * counts the number of words in a string:
 * Example run:
 * 
 * scala> wordCount("  hello ")
 * val res32: Int = 1
 *
 * scala> wordCount("  hello   world !")
 * val res33: Int = 3
 * 
 * As before, you may use c.isWhitespace to check if a character c counts as whitespace.
 * Consider defining a helper function discardWord that skips over the first word
 * of a string and returns the corresponding suffix 
 * (in other words, discardWord("abc def") should return " def").
 */

def discardWord(s:String): String={
    ???  
  }

def wordCount(s: String): Int ={
  ???
}

/*
 * Implement a function that checks if a string is blank (i.e., if it only
 * contains whitespace characters or is empty):
 * You may find the .isWhitespace method of Char useful:
 * 
 * scala> 'c'.isWhitespace
 * val res29: Boolean = false
 * 
 * scala> ' '.isWhitespace
 * val res30: Boolean = true
 */

def isBlank(s: String): Boolean ={
  ???
}


/*
 * Implement a function that encrypts a string using the Caesar
 * cipher method. The Caesar cipher is an ancient form of encryption,
 * where each letter in a string is shifted by a fixed number of places
 * down the alphabet.
 *
 * In this exercise, we assume that the input string only contains English
 * lowercase letters. In addition, we shift the letters in a circular way,
 * so that z shifted by 1 becomes a, z shifted by 2 becomes b, etc.
 *
 * Every English letter has a corresponding ASCII and Unicode code
 * which is an integer value. Codes of English letters are the same in ASCII
 * and Unicode; the English lowercase letter ‘a’ corresponds to the number 97,
 * ‘b’ is 98, and so on up to ‘z’ which is 122.
 *
 * In Scala, you can get the ASCII value of an English letter using the toInt method,
 * and convert it back to a character using the toChar method:
 *
 * scala> 'a'.toInt
 * val res34: Int = 97
 * 
 * scala> 97.toChar
 * val res35: Char = a
 * 
 * Here is a REPL session demonstrating how to encrypt a single character:
 * 
 * scala> val aCode = 'a'.toInt
 *      | val zCode = 'z'.toInt
 *      | val numLetters = zCode - aCode + 1
 * val aCode: Int = 97
 * val zCode: Int = 122
 * val numLetters: Int = 26
 *
 * scala> val dCode = 'd'.toInt
 * val dCode: Int = 100
 * 
 * scala> val dPlus3 = (dCode + 3 - aCode) % numLetters + aCode
 * val dPlus3: Int = 103
 * 
 * scala> dPlus3.toChar
 * val res36: Char = g
 * 
 * scala> val dPlus25 = (dCode + 25 - aCode) % numLetters + aCode
 * val dPlus25: Int = 99
 * 
 * scala> dPlus25.toChar.toString
 * val res37: String = c
 * 
 * And here are two additional examples of your caesarCipher function
 * should behave:
 * 
 * scala> caesarCipher("abc", 2)
 * val res38: String = cde
 * 
 * scala> caesarCipher("abz", 3)
 * val res39: String = dec
 * 
 * Implement the caesarCipher function accordingly:
 */

def caesarCipher(s: String, shift: Int): String ={
  ???
}

// Implement a function that reverses a string:

def reverseString(s: String): String ={
  ???
}
