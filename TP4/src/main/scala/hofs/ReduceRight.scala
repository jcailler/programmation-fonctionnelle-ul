package hofs

/* The following functions relate to the "reduceRight" category */

def min(l: IntList): Int =
  if l.isEmpty then
    throw Exception("Empty list!")
  else 
    if l.tail.isEmpty then l.head
    else
      val m = min(l.tail)
      if l.head < m then l.head else m

def last(l: IntList): Int =
  if l.isEmpty then 
    throw Exception("Empty list!")
  else 
    if l.tail.isEmpty then l.head
    else last(l.tail)