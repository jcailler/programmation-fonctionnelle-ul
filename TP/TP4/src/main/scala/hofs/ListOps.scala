package hofs

def length(l: IntList): Int =
  if l.isEmpty then 0
  else 1 + length(l.tail)

def allPositiveOrZero(l: IntList): Boolean =
  if l.isEmpty then true
  else l.head >= 0 && allPositiveOrZero(l.tail)

// def sum(l: IntList): Int =
//   if l.isEmpty then 0
//   else l.head + sum(l.tail)

// def product(l: IntList): Int =
//   if l.isEmpty then 1
//   else l.head * product(l.tail)

def anyOdd(l: IntList): Boolean =
  if l.isEmpty then false
  else (l.head % 2 != 0) || anyOdd(l.tail)

def decrement(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else IntCons(l.head - 1, decrement(l.tail))

// def increment(l: IntList): IntList =
//   if l.isEmpty then IntNil()
//   else IntCons(l.head + 1, increment(l.tail))

def multiplyBy2(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else IntCons(2 * l.head, multiplyBy2(l.tail))

def anyNegative(l: IntList): Boolean =
  if l.isEmpty then false
  else l.head < 0 || anyNegative(l.tail)

def allEven(l: IntList): Boolean =
  if l.isEmpty then true
  else (l.head % 2 == 0) && allEven(l.tail)

def horner(x: Int, l: IntList): Int =
  if l.isEmpty then 0
  else l.head + x * horner(x, l.tail)

def reverseAppend(l1: IntList, l2: IntList): IntList =
  if l1.isEmpty then l2
  else reverseAppend(l1.tail, IntCons(l1.head, l2))

def append(l1: IntList, l2: IntList): IntList =
  if l1.isEmpty then l2
  else IntCons(l1.head, append(l1.tail, l2))

def contains(l: IntList, n: Int): Boolean =
  if l.isEmpty then false
  else (l.head == n) || contains(l.tail, n)

def isSubset(l: IntList, L: IntList): Boolean =
  if l.isEmpty then true
  else contains(L, l.head) && isSubset(l.tail, L)

def countPositive(l: IntList): Int =
  if l.isEmpty then 0
  else (if l.head > 0 then 1 else 0)
         + countPositive(l.tail)

def removeOdd(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else if l.head % 2 != 0 then removeOdd(l.tail)
  else IntCons(l.head, removeOdd(l.tail))

// def countEven(l: IntList): Int =
//   if l.isEmpty then 0
//   else (if l.head % 2 == 0 then 1 else 0)
//          + countEven(l.tail)

// def capAtZero(l: IntList): IntList =
//   if l.isEmpty then IntNil()
//   else IntCons(if l.head > 0 then 0 else l.head,
//                capAtZero(l.tail))

// def removeZeroes(l: IntList): IntList =
//   if l.isEmpty then IntNil()
//   else if l.head == 0 then removeZeroes(l.tail)
//   else IntCons(l.head, removeZeroes(l.tail))

// def collectEven(l: IntList): IntList =
//   if l.isEmpty then IntNil()
//   else if l.head % 2 == 0 then
//     IntCons(l.head, collectEven(l.tail))
//   else collectEven(l.tail)

def subtract(l: IntList): Int =
  if l.isEmpty then
    throw IllegalArgumentException("Empty list!")
  else if l.tail.isEmpty then l.head
  else l.head - subtract(l.tail)

def takeWhilePositive(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else if l.head > 0 then
    IntCons(l.head, takeWhilePositive(l.tail))
  else IntNil()

def collectMultiples(d: Int, l: IntList): IntList =
  if l.isEmpty then IntNil()
  else if l.head % d == 0 then
    IntCons(l.head, collectMultiples(d, l.tail))
  else collectMultiples(d, l.tail)

// def last(l: IntList): Int =
//   if l.isEmpty then
//     throw IllegalArgumentException("Empty list!")
//   else if l.tail.isEmpty then l.head
//   else last(l.tail)

def init(l: IntList): IntList =
  if l.isEmpty then
    throw IllegalArgumentException("Empty list!")
  else if l.tail.isEmpty then IntNil()
  else IntCons(l.head, init(l.tail))

def intersection(l: IntList, L: IntList): IntList =
  if l.isEmpty then IntNil()
  else if contains(L, l.head) then
    IntCons(l.head, intersection(l.tail, L))
  else intersection(l.tail, L)

def difference(l: IntList, L: IntList): IntList =
  if l.isEmpty then IntNil()
  else if contains(L, l.head) then
    difference(l.tail, L)
  else IntCons(l.head, difference(l.tail, L))

// def multiplyOdd(l: IntList): Int =
//   if l.isEmpty then 1
//   else
//     val m = if l.head % 2 != 0
//             then l.head else 1
//     m * multiplyOdd(l.tail)

// def min(l: IntList): Int =
//   if l.isEmpty then
//     throw IllegalArgumentException("Empty list!")
//   else if l.tail.isEmpty then l.head
//   else
//     val m = min(l.tail)
//     if l.head < m then l.head else m