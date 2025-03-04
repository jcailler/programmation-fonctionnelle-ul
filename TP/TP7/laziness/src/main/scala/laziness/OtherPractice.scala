package laziness

import MyLazyList.*

object OtherPractice:

  lazy val codes: MyLazyList[String] =
    ???

  lazy val palCodes: MyLazyList[String] =
    ???

  val middle: MyLazyList[String] = cons("", cons("0", cons("1", empty)))

  lazy val palCodes2: MyLazyList[String] =
    // need to add base cases at the beginning
    cons("0", cons("1", empty)).append(???)

  def nextLine(currentLine: List[Int]): List[Int] =
    ???

  lazy val funSeq: MyLazyList[List[Int]] =
    ???
