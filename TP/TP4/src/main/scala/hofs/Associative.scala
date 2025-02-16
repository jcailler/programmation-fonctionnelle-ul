package hofs

/* The following functions relate to the "associative" category */

def sum(l: IntList): Int = 
  if l.isEmpty then 0
  else l.head + sum(l.tail)

def product(l: IntList): Int =
  if l.isEmpty then 1
  else l.head * product(l.tail)

