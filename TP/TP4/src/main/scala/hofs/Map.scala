package hofs

/* The following functions relate to the "map" category */

def increment(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else IntCons(l.head + 1, increment(l.tail))

def capAtZero(l: IntList): IntList =
  if l.isEmpty then IntNil()
  else IntCons(if l.head > 0 then 0 else l.head, capAtZero(l.tail))


