package hofs

/* The following functions relate to the "filter" category */

def collectEven(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else if l.head % 2 == 0 then
  IntCons(l.head, collectEven(l.tail))
  else collectEven(l.tail)

def removeZeroes(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else 
      if l.head == 0 then removeZeroes(l.tail)
      else IntCons(l.head, removeZeroes(l.tail))


