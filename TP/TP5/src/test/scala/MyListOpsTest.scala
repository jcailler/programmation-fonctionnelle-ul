package poly

import poly.MyList.*

class ListOpsTests extends munit.FunSuite:
  val twoNumbers: MyList[Int] =
    Cons(-3, Cons(-5, Nil))

  val threeChars: MyList[Char] =
    Cons('b', Cons('a', Cons('e', Nil)))

  val fourNumbers: MyList[Int] =
    Cons(1, Cons(2, Cons(-3, Cons(4, Nil))))

  val fiveChars: MyList[Char] =
    Cons('e', Cons('a', Cons('b', Cons(' ', Cons('c', Nil)))))

  val twoLetterWords: MyList[String] =
    Cons("at", Cons("or", Cons("be", Cons("in", Cons("to", Cons("by", Nil))))))

  val threeLetterWords: MyList[String] =
    Cons("and", Cons("tea", Cons("ate", Cons("for", Cons("six", Cons("try", Cons("sin", Cons("see", Nil))))))))

  val fourLetterWords: MyList[String] =
    Cons("name", Cons("tree", Cons("list", Cons("like", Nil))))

  enum GenListOpt:
    case OneToN
    case AllOne

  def generateList(n: Int, opt: GenListOpt): MyList[Int] =
    @annotation.tailrec
    def gen(cur: Int, l: MyList[Int]): MyList[Int] =
      if cur < 0 then l
      else
        val v = opt match
          case GenListOpt.OneToN => cur - 1
          case GenListOpt.AllOne => 1
        gen(cur - 1, Cons(v, l))
    gen(n - 1, Nil)

  val N = 50000
  val manyNumbers1ToN = generateList(N, GenListOpt.OneToN)
  val manyNumbers1 = generateList(N, GenListOpt.AllOne)

  test("map: empty list remains empty"):
    assertEquals(map(Nil)((x: Int) => x), Nil)

  test("map: increment integers in the list"):
    assertEquals(map(Cons(1, Cons(2, Cons(3, Nil))))((x: Int) => x + 1), Cons(2, Cons(3, Cons(4, Nil))))

  test("map: append 's' to strings in the list"):
    assertEquals(map(Cons("apple", Cons("banana", Nil)))((x: String) => x + "s"), Cons("apples", Cons("bananas", Nil)))

  test("filter: empty list remains empty"):
    assertEquals(filter(Nil)((x: Int) => x > 10), Nil)

  test("filter: filter even numbers"):
    assertEquals(filter(Cons(1, Cons(2, Cons(3, Cons(4, Nil)))))((x: Int) => x % 2 == 0), Cons(2, Cons(4, Nil)))

  test("filter: filter strings with length greater than 4"):
    assertEquals(filter(Cons("sky", Cons("banana", Nil)))((x: String) => x.length > 4), Cons("banana", Nil))

  test("foldRight: sum of integers"):
    assertEquals(foldRight(Cons(1, Cons(2, Cons(3, Nil))))((x: Int, y: Int) => x + y, 0), 6)

  test("foldRight: concatenate strings"):
    assertEquals(foldRight(Cons("a", Cons("b", Cons("c", Nil))))((x: String, y: String) => x + y, ""), "abc")

  test("foldRight: multiplication on empty list"):
    assertEquals(foldRight(Nil)((x: Int, y: Int) => x * y, 1), 1)

  test("reduceRight: sum of integers"):
    assertEquals(reduceRight(Cons(1, Cons(2, Cons(3, Nil))))((x: Int, y: Int) => x + y), 6)

  test("reduceRight: concatenate strings"):
    assertEquals(reduceRight(Cons("a", Cons("b", Cons("c", Nil))))((x: String, y: String) => x + y), "abc")

  test("forall: all positive numbers"):
    assert(forall(Cons(1, Cons(2, Cons(3, Nil))))((x: Int) => x > 0))

  test("forall: not all even numbers"):
    assert(!forall(Cons(1, Cons(2, Cons(3, Nil))))((x: Int) => x % 2 == 0))

  test("exists: contains even number"):
    assert(exists(Cons(1, Cons(2, Cons(3, Nil))))((x: Int) => x % 2 == 0))

  test("exists: does not contain number greater than 10"):
    assert(!exists(Cons(1, Cons(2, Cons(3, Nil))))((x: Int) => x > 10))

  test("zip: pair integers with strings"):
    assertEquals(
      zip(Cons(1, Cons(2, Cons(3, Nil))), Cons("a", Cons("b", Cons("c", Nil)))),
      Cons((1, "a"), Cons((2, "b"), Cons((3, "c"), Nil)))
    )

  test("zipWith: sum paired integers"):
    assertEquals(
      zipWith(Cons(1, Cons(2, Cons(3, Nil))), Cons(4, Cons(5, Cons(6, Nil))))((x: Int, y: Int) => x + y),
      Cons(5, Cons(7, Cons(9, Nil)))
    )

  test("zipWith: concatenate paired strings"):
    assertEquals(
      zipWith(Cons("a", Cons("b", Nil)), Cons("c", Cons("d", Nil)))((x: String, y: String) => x + y),
      Cons("ac", Cons("bd", Nil))
    )

  test("elementsAsStrings: empty list"):
    assertEquals(elementsAsStrings(Nil), Nil)

  test("elementsAsStrings: threeChars"):
    assertEquals(elementsAsStrings(threeChars), Cons("b", Cons("a", Cons("e", Nil))))

  test("elementsAsStrings: fourNumbers"):
    assertEquals(elementsAsStrings(fourNumbers), Cons("1", Cons("2", Cons("-3", Cons("4", Nil)))))

  test("length: empty list"):
    assertEquals(length(Nil), 0)

  test("length: twoNumbers"):
    assertEquals(length(twoNumbers), 2)

  test("length: threeChars"):
    assertEquals(length(threeChars), 3)

  test("takeWhilePositive: empty list"):
    assertEquals(takeWhilePositive(Nil), Nil)

  test("takeWhilePositive: list with 2 elements"):
    assertEquals(takeWhilePositive(twoNumbers), Nil)

  test("takeWhilePositive: list with 4 elements"):
    assertEquals(takeWhilePositive(fourNumbers), Cons(1, Cons(2, Nil)))

  test("last: empty list"):
    intercept[IllegalArgumentException]:
      last(Nil)

  test("last: twoNumbers"):
    assertEquals(last(twoNumbers), -5)

  test("last: threeChars"):
    assertEquals(last(threeChars), 'e')

  test("capitalizeString: empty list"):
    assertEquals(capitalizeString(Nil), Nil)

  test("capitalizeString: threeChars"):
    assertEquals(capitalizeString(threeChars), Cons('B', Cons('A', Cons('E', Nil))))

  test("wordCount: empty string"):
    assertEquals(wordCount(Nil), 0)

  test("wordCount: 1 word with one space before"):
    assertEquals(wordCount(MyList.from(" hello")), 1)

  test("wordCount: 1 word with two spaces before"):
    assertEquals(wordCount(MyList.from("  hello")), 1)

  test("wordCount: 1 word with one space after"):
    assertEquals(wordCount(MyList.from("hello ")), 1)

  test("wordCount: 1 word with two spaces after"):
    assertEquals(wordCount(MyList.from("hello  ")), 1)

  test("wordCount: 1 word with one space before and after"):
    assertEquals(wordCount(MyList.from(" hello ")), 1)

  test("wordCount: 2 words with one space between"):
    assertEquals(wordCount(MyList.from("hello world")), 2)

  test("wordCount: 2 words with two spaces between"):
    assertEquals(wordCount(MyList.from("hello  world")), 2)

  test("wordCount: 2 words with one space before and after"):
    assertEquals(wordCount(MyList.from(" hello world ")), 2)

  test("wordCount: 3 words with one space between"):
    assertEquals(wordCount(MyList.from("hello world again")), 3)

  test("flatMap: empty list"):
    assertEquals(flatMap[Int, Int](Nil)(n => Cons(n, Cons(n * 2, Nil))), Nil)

  test("flatMap: double and flattern a list of integers"):
    assertEquals(
      flatMap(Cons(1, Cons(2, Nil)))(n => Cons(n, Cons(n * 2, Nil))),
      Cons(1, Cons(2, Cons(2, Cons(4, Nil))))
    )

  test("flatten: empty list"):
    assertEquals(flatten(Nil), Nil)

  test("flatten: a list of lists of words"):
    val l = Cons(twoLetterWords, Cons(threeLetterWords, Cons(fourLetterWords, Nil)))
    assertEquals(flatten(l), twoLetterWords ++ threeLetterWords ++ fourLetterWords)

  test("cross-product: generate menu"):
    val mains = Cons("burger", Cons("Pizza", Cons("Pasta", Nil)))
    val sides = Cons("Salad", Cons("Soup", Nil))
    val meals = Cons(
      ("burger", "Salad"),
      Cons(
        ("burger", "Soup"),
        Cons(("Pizza", "Salad"), Cons(("Pizza", "Soup"), Cons(("Pasta", "Salad"), Cons(("Pasta", "Soup"), Nil))))
      )
    )
    assertEquals(crossProduct(mains, sides), meals)

  test("allThreeWords: return the correct words in the right order"):
    assertEquals(allThreeLetterWords(threeLetterWords ++ twoLetterWords ++ fourLetterWords), threeLetterWords)

  test("allThreeWords: empty list returns empty list"):
    assert(allThreeLetterWords(Nil).isEmpty)

  test("allThreeWords: returns empty list if no three letter words"):
    assert(allThreeLetterWords(twoLetterWords ++ fourLetterWords).isEmpty)

  // ???: tests

  test("sum0: empty list"):
    assertEquals(sum0(Nil), 0)

  test("sum0: twoNumbers"):
    assertEquals(sum0(twoNumbers), -8)

  test("sum0: fourNumbers"):
    assertEquals(sum0(fourNumbers), 4)

  test("sum1: empty list"):
    assertEquals(sum1(Nil), 0)

  test("sum1: twoNumbers"):
    assertEquals(sum1(twoNumbers), -8)

  test("sum1: fourNumbers"):
    assertEquals(sum1(fourNumbers), 4)

  test("sum0Fold: empty list"):
    assertEquals(sum0Fold(Nil), 0)

  test("sum0Fold: twoNumbers"):
    assertEquals(sum0Fold(twoNumbers), -8)

  test("sum0Fold: fourNumbers"):
    assertEquals(sum0Fold(fourNumbers), 4)

  test("sum1Fold: empty list"):
    assertEquals(sum1Fold(Nil), 0)

  test("sum1Fold: twoNumbers"):
    assertEquals(sum1Fold(twoNumbers), -8)

  test("sum1Fold: fourNumbers"):
    assertEquals(sum1Fold(fourNumbers), 4)

  // start sum0-large-list
  // test("sum0: large list"):
  //   assertEquals(sum0(manyNumbers1), N)
  // end sum0-large-list

  // test("sum0Fold: large list"):
  //   assertEquals(sum0Fold(manyNumbers1), N)

  test("sum1: large list"):
    assertEquals(sum1(manyNumbers1), N)

  test("sum1Fold: large list"):
    assertEquals(sum1Fold(manyNumbers1), N)

  test("reverseAppend: empty lists"):
    assertEquals(reverseAppend(Nil, Nil), Nil)

  test("reverseAppend: twoNumbers to empty list"):
    assertEquals(reverseAppend(twoNumbers, Nil), Cons(-5, Cons(-3, Nil)))

  test("reverseAppend: fourNumbers to empty list"):
    assertEquals(
      reverseAppend(fourNumbers, Nil),
      Cons(4, Cons(-3, Cons(2, Cons(1, Nil))))
    )

  test("reverseAppend: threeChars to empty list"):
    assertEquals(reverseAppend(threeChars, Nil), Cons('e', Cons('a', Cons('b', Nil))))

  test("reverseAppend: empty list to twoNumbers"):
    assertEquals(reverseAppend(Nil, twoNumbers), twoNumbers)

  test("reverseAppend: empty list to fourNumbers"):
    assertEquals(reverseAppend(Nil, fourNumbers), fourNumbers)

  test("reverseAppend: fourNumbers to twoNumbers"):
    assertEquals(
      reverseAppend(fourNumbers, twoNumbers),
      Cons(4, Cons(-3, Cons(2, Cons(1, twoNumbers))))
    )

  test("reverseAppend: twoNumbers to fourNumbers"):
    assertEquals(
      reverseAppend(twoNumbers, fourNumbers),
      Cons(-5, Cons(-3, fourNumbers))
    )

  test("reverse: twoNumbers"):
    assertEquals(reverse(twoNumbers), Cons(-5, Cons(-3, Nil)))

  test("reverse: threeChars"):
    assertEquals(
      reverse(threeChars),
      Cons('e', Cons('a', Cons('b', Nil)))
    )

  test("countEven: empty list"):
    assertEquals(countEven(Nil), 0)

  test("countEven: twoNumbers"):
    assertEquals(countEven(twoNumbers), 0)

  test("countEven: fourNumbers"):
    assertEquals(countEven(fourNumbers), 2)

  test("countEven: large list"):
    assertEquals(countEven(manyNumbers1ToN), N / 2)

  // test("curriedZipWith: sum paired integers"):
  //   assertEquals(
  //     curriedZipWith((x: Int, y: Int) => x + y)(Cons(1, Cons(2, Cons(3, Nil))))(Cons(4, Cons(5, Cons(6, Nil)))),
  //     Cons(5, Cons(7, Cons(9, Nil)))
  //   )

  // test("curriedZipWith: concatenate paired strings"):
  //   assertEquals(
  //     curriedZipWith((x: String, y: String) => x + y)(Cons("a", Cons("b", Nil)))(Cons("c", Cons("d", Nil))),
  //     Cons("ac", Cons("bd", Nil))
  // )
