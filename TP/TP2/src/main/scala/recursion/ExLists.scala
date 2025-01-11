package recursion

/* 
 * Here you find a framework for the exercises on recursion.
 * The exercises are linked with different topics which you can check out in the
 * README file of the exercise.
 */

// As a first step implement the factorial function n!=n*(n-1)*...*2*1
def fac(n:Int):Int={
  ???
}

/*
 * There is a more efficient way to define fac,
 * such that the the recursive function invocation is the last step when
 * executing the function body. In this version of the function,
 * fac(n-1) is allowed but n*fac(...) is not permitted.
 * In order to implement this version of the factorial function, we need a
 * helper function hfac that gets called from our new fac function nfac.
 */

def nfac(n:Int) : Int = {
  def hfac(n:Int, acc:Int) : Int = {
      //What could be the role of acc?
      ???
  }
  //With which parameters do you need to call hfac now?
  ???
}

//
// Recursion on lists
//

// Implement a function that computes the length of an IntList:
def length(l: IntList): Int ={
  ???
}

/*
 * A linked list is a bit like a Matryoshka doll: 
 * you have to open the larger one to access the smaller ones inside.
 * So ask yourself: how would you count how many nested dolls a large Matryoshka
 * doll contains? Can you translate this process into code?
 */

// Implement a function that determines if all values in an IntList are
// positive or zero:
def allPositiveOrZero(l: IntList): Boolean ={
  ???   
}

// Implement a function that counts the number of positive values in an IntList:
def countPositive(l: IntList): Int ={
  ???  
}

// Implement a function that computes the sum of all elements in an IntList:
def sum(l: IntList): Int ={
  ???  
}

// Implement a function that computes the product of all elements in an IntList:
def product(l: IntList): Int ={
  ???  
}

/*
 * Implement a function that determines if any value in an IntList is odd:
 * You can use x % 2 == 0 to check whether a number is even, 
 * but does it work to use x % 2 == 1 to check whether a number is odd?
 * What should this function return for an empty list? Why?
 */

def anyOdd(l: IntList): Boolean ={
  ???  
}

/*
 * Implement a function that creates a new list whose values 
 * correspond to the ones in the original list, decremented by one:
 */

def decrement(l: IntList): IntList ={
  ???  
}

// Write a function that collects all even values from an IntList:

def collectEven(l: IntList): IntList ={
  ???  
}

/*
 * Write a function min that retrieves the minimum of a list.
 * If min is called on an empty list, it should throw an
 * IllegalArgumentException using
 * throw IllegalArgumentException("Empty list!").
 */

def min(l: IntList): Int ={
  ???  
}

// Implement a function that creates a new list whose values correspond
// to the ones in the original list incremented by one:

def increment(l: IntList): IntList ={
  ???  
}

// Implement a function that performs right-associative subtraction.
// That is, 1, 2, 3, 4, 5 should become 1 - (2 - (3 - (4 - 5))) = 3.

// If subtract is called on an empty list, it should throw an
// IllegalArgumentException using throw IllegalArgumentException("Empty list!"):

def subtract(l: IntList): Int ={
  ???  
}

// Write a function that creates a new list with all odd values removed:

def removeOdd(l: IntList): IntList ={
  ???  
}

// Can you find an algebraic relationship between collectEven and removeOdd?

// Implement a function that counts the number of even values in an IntList:

def countEven(l: IntList): Int = {
  ???  
}

// Could you write countEven using collectEven and length?
def countEven2(l:IntList): Int ={
  ???  
}

// Implement a function that creates a new list whose values
// correspond to the ones in the original list multiplied by two:

def multiplyBy2(l: IntList): IntList ={
  ???  
}

// Implement a function that determines if any value in an
// IntList is negative:

def anyNegative(l: IntList): Boolean ={
  ???  
}

// Implement a function that determines if all values in an
// IntList are even:

def allEven(l: IntList): Boolean ={
  ???  
}

// Implement a function that computes the product of all
// odd numbers in an IntList:

def multiplyOdd(l: IntList): Int ={
  ???  
}

/*
 * Given a polynomial:
 *    a0+a1x+a2x^2+a3x^3+⋯+anx^n
 * We represent it as a list of coefficients [a0,a1,a2,⋯ ,an].
 *
 * Using the Horner’s rule,
 *  a0+x(a1+x(a2+x(a3+⋯+x(an−1+x an)⋯)))
 * write a function that evaluates a polynomial given its list
 * of coefficients and a value for x:
 */

def horner(x: Int, l: IntList): Int ={
  ???  
}

// Implement a function that creates a copy of a list with all numbers
// greater than 0 replaced with zeroes:

def capAtZero(l: IntList): IntList ={
  ???  
}

//Implement a function that creates a copy of a list with all
// zeroes removed:

def removeZeroes(l: IntList): IntList ={
  ???  
}

/*
 * Implement a function that appends the reversed version of an
 * IntList to another.
 * For example, calling reverseAppend with the
 * lists 1, 2, 3 and 7, 8, 9 should produce 3, 2, 1, 7, 8, 9
 */

def reverseAppend(l1: IntList, l2: IntList): IntList ={
  ???  
}

// Implement a function that reverses an IntList:

def reverse(l: IntList): IntList ={
  ???  
}

/*
 * Implement a function that creates a new list containing the
 * same elements as its input list, except that it discards every
 * number starting from the first negative number.
 * That is, 1, 2, -3, 4 should produce just 1, 2.
 */

def takeWhilePositive(l: IntList): IntList ={
  ???  
}

// Implement a function that concatenates two lists:

def append(l1: IntList, l2: IntList): IntList ={
  ???  
}

// Bonus: can you write it using just reverse and reverseAppend?
def appendUsingReverseAppend(l1: IntList, l2: IntList): IntList ={
  ???  
}

// Write a function that collects all the multiples of an integer d
// in a list l:

def collectMultiples(d: Int, l: IntList): IntList ={
  ???  
}

/*
 * Implement a function that retrieves the last element from an IntList.
 * If last is called on an empty list, it should throw an
 * IllegalArgumentException using throw IllegalArgumentException("Empty list!").
 */

def last(l: IntList): Int ={
  ???  
}

// Implement a function that creates a copy of an IntList with the last
// element removed. This functions is sometimes called butlast:

def init(l: IntList): IntList ={
  ???  
}

/*
 * The goal of this exercise is to write function that retrieves the smallest
 * and largest elements from an IntList as a pair.

 * Because your functions must return 2 values, you will need to return a pair.
 * A pair containing the integers 1 and 2 is written as (1, 2).
 * Similarly, the type of a pair containing two integers is written as (Int, Int).
 * Here is a REPL session demonstrating how to create and use pairs:

scala> val pair = (1, 2)
val pair: (Int, Int) = (1,2)

scala> pair._1
val res9: Int = 1

scala> pair._2
val res10: Int = 2

scala> val (first, second) = pair
val first: Int = 1
val second: Int = 2

 * To find the smallest or largest of two integers, you can use the
 * scala.math.min and scala.math.max functions.
 *
 * If minMax is called on an empty list, it should throw an
 * IllegalArgumentException using throw IllegalArgumentException("Empty list!").
 */

def minMax(l: IntList): (Int, Int) ={
  ???  
}

// Implement a function that evaluates an expression in Polish Notation,
// see the README file for more information about Polish notation.
val Add = -1
val Multiply = -2

def polishEval(l: IntList): (Int, IntList) ={
  ???  
}
