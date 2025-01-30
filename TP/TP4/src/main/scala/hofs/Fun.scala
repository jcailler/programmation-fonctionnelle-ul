package hofs

/************************/
/*** Warm-up exercise ***/
/************************/

/* Head exercise */

// Check if the head of the list is even
def headIsEven(l: IntList): Boolean =
  !l.isEmpty && l.head % 2 == 0

// Check if the head of he list is positive
def headIsPositive(l: IntList): Boolean =
  !l.isEmpty && l.head > 0

// Check if the head of the list follows a certain property
def headHasProperty(p: Int => Boolean, l: IntList): Boolean =
  !l.isEmpty && p(l.head)

// Check if the head of the list is even --- higher order version
def headIsEven1(l: IntList): Boolean =
  ???

// Check if the head of he list is positive --- higher order version
def headIsPositive1(l: IntList): Boolean =
  ???


/* ConstructTwo exercise */

def DoubleTriple(x: Int) =
  IntCons(x * 2, IntCons(x * 3, IntNil()))

def DivideTrivide(x: Int) =
  IntCons(x / 2, IntCons(x / 3, IntNil()))

def IncrementDeuxcrement(x: Int) =
  IntCons(x + 1, IntCons(x + 2, IntNil()))

def ConstructTwo(f: Int => Int, g: Int => Int): Int => IntList =
  (x: Int) => ???

val DoubleTriple2 = ???
val DivideTrivide2 = ???
val IncrementDeuxcrement2 = ???



/******************/
/*** Conclusion ***/
/******************/

/* map and filter as foldRight */

def mapAsFoldRight(f: Int => Int): IntList => IntList =
  ???

def filterAsFoldRight(p: Int => Boolean): IntList => IntList =
  ???


/* forall and exists */

def forall(p: Int => Boolean)(l: IntList): Boolean =
  if l.isEmpty then true
  else p(l.head) && forall(p)(l.tail)

def exists(p: Int => Boolean)(l: IntList): Boolean =
  if l.isEmpty then false
  else p(l.head) || exists(p)(l.tail)

def allEven(l: IntList): Boolean =
    if l.isEmpty then true
    else (l.head % 2 == 0) && allEven(l.tail)

def anyNegative(l: IntList): Boolean =
  if l.isEmpty then false
  else l.head < 0 || anyNegative(l.tail)

def allEven2(l: IntList): Boolean =
  ???

def anyNegative2(l: IntList): Boolean =
  ???

def forallNoIf(p: Int => Boolean)(l: IntList): Boolean =
  ???

def existsNoIf(p: Int => Boolean)(l: IntList): Boolean =
  ???


/***************************/
/*** Functions as values ***/
/***************************/

/* Composition */

def andThen(f: Int => Double, g: Double => String) =
  ???

/* Identity */

val id: Int => Int = ???

/* flip */

def flip(f: (Int, Int) => Int): (Int, Int) => Int =
  ???

/* Refactoring with composition */

val squareMinusOne     = (x: Int) => (x - 1) * (x - 1)
val squarePlusOne      = (x: Int) => (x + 1) * (x + 1)
val squareSquare       = (x: Int) => (x * x) * (x * x)
val squareMinusTwo     = (x: Int) => (x - 2) * (x - 2)
val squareSquareSquare = (x: Int) =>
  ((x * x) * (x * x)) * ((x * x) * (x * x))

val square = (x: Int) => x * x
val plusOne = (x: Int) => x + 1
val minusOne = (x: Int) => x - 1
def composeInt(f: Int => Int, g: Int => Int): Int => Int =
  x => f(g(x))

/* Lifting */

def adder(f: Int => Double, g: Int => Double): Int => Double =
  ???

def multiplier(f: Int => Double, g: Int => Double): Int => Double =
  ???

/* Heavy lifting */

def lifter(op: (Double, Double) => Double): (Int => Double, Int => Double) => (Int => Double) =
  ???

val adder2 = ???
val multiplier2 = ???

/* Multi-lifting */

def meet(f: Int => Boolean, g: Int => Boolean): (Int => Boolean) =
  ???

def Meet(l: IntPredicateList): (Int => Boolean) =
  ???

/***************************/
/*** Functions as values ***/
/***************************/

def isGreaterThanBasic(x: Int, y: Int): Boolean =
  x > y
val isGreaterThanAnon: (Int, Int) => Boolean =
  (x, y) => x > y
val isGreaterThanCurried: Int => Int => Boolean =
  x => y => x > y // Same as `x => (y => x > y)`
def isGreaterThanCurriedDef(x: Int)(y: Int): Boolean =
  x > y

// How to call:
//   For all x, y:
//     isGreaterThan(x, y)
//       == isGreaterThanAnon(x, y)
//       == isGreaterThanCurried(x)(y)

/* incrHeadByX */

def incrHeadByXBasic(x: Int, l: IntList): IntList =
  if l.isEmpty then l
  else IntCons(l.head + x, l.tail)

val incrHeadByXAnon: (Int, IntList) => IntList = ???

val incrHeadByXCurried: Int => IntList => IntList = ???

def incrHeadByXCurriedDef(x: Int)(l: IntList): IntList =  ???

/* addToFront */

def addToFrontBasic(x: Int, y: Int, l: IntList): IntList =
  IntCons(x, IntCons(y, l))

val addToFrontAnon: (Int, Int, IntList) => IntList = ???

val addToFrontPartlyCurried: (Int, Int) => IntList => IntList = ???

val addToFrontCurried: Int => Int => IntList => IntList = ???

def addToFrontCurriedDef(x: Int)(y: Int)(l: IntList): IntList = ???

/* contains */

def containsBasic(l: IntList, n: Int): Boolean =
  !l.isEmpty && (n == l.head || containsBasic(l.tail, n))

def containsAnon: (IntList, Int) => Boolean = ???

def containsCurried: IntList => Int => Boolean = ???

def containsCurriedDef(l: IntList)(n: Int): Boolean = ???

/* headHasProperty */

def headHasPropertyBasic(p: Int => Boolean, l: IntList): Boolean =
  !l.isEmpty && p(l.head)

val headHasPropertyAnon: ((Int => Boolean), IntList) => Boolean = ???

val headHasPropertyCurried: (Int => Boolean) => IntList => Boolean = ???

def headHasPropertyCurriedDef(p: Int => Boolean)(l: IntList): Boolean = ???

val headIsEven2 = ???

val headIsPositive2 = ???

/* Currying container functions */

val p2All =
  IntCons(123456, IntCons(654321, IntCons(111222, IntCons(333444, IntCons(555666, IntCons(787878, IntNil()))))))

val p2Staff =
  IntCons(654321, IntCons(333444, IntNil()))

def isRegisteredForP2Def(id: Int): Boolean = ???

val isRegisteredForP2Val = ???

def isP2StudentDef(id: Int): Boolean = ???

def andLifter(f: Int => Boolean, g: Int => Boolean): Int => Boolean =
  n => f(n) && g(n)

def notLifter(f: Int => Boolean): Int => Boolean =
  n => !f(n)

val isP2StudentVal = ???

def isCourseStudentDefPartlyCurried(all: IntList, staff: IntList): Int => Boolean =
  ???

/* Equality */

val f0 = (x: Long) => x
val f1 = (x: Long) => if x > 0 then x else -x
val f2 = (x: Long) => x + 1 - 1
val f3 = (x: Long) =>
  Math.sqrt(x.toDouble * x.toDouble).round
val f4: Long => Long = x =>
  if x < 0 then f4(x + 1) - 1
  else if x > 0 then f4(x - 1) + 1
  else 0

def eqBoolBool(
    f: Boolean => Boolean,
    g: Boolean => Boolean
) =  ???

/* Fixed points */

val a = (x: Int) => x
val b = (x: Int) => -x
val c = (x: Int) => x + 1
val d = (x: Int) => (x / 2) + 5
val e = (x: Int) => if x % 10 == 0 then x else (x + 1)
val f = (x: Int) => -(x * x)
val g = (x: Int) => /* 🔥 */ /* assuming x > 0 */
  if x == 1 then 1
  else if x % 2 == 0 then x / 2
  else 3 * x + 1

def fixedPoint(f: Int => Int, start: Int): Int =
  ???
