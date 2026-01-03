package recursion

/*
 * Here you find a framework for the exercises on recursion.
 * The exercises are linked with different topics which you can
 * check out in the README file of the exercise.
 */

// The next few exercises implement set operations on lists

// Write a contains(l, n) function that checks whether list l
// contains value n:

def contains(l: IntList, n: Int): Boolean ={
  ???  
}

// Write an isSubset(l, L) function that checks whether list l
// is a subset of list L (that is, all elements of l are also contained in L).
// You can use contains from above for this.

def isSubset(l: IntList, L: IntList): Boolean ={
  ???  
}

/*
 * Write an intersection(l, L) function that constructs a new list 
 * whose elements are the same as those of l in the same order,
 * but with all elements not contained in L removed.
 * You can use contains from above for this.
 */

def intersection(l: IntList, L: IntList): IntList ={
  ???  
}

/*
 * Write a difference(l, L) function that constructs a new list
 * whose elements are the same as those of l in the same order,
 * but with all elements contained in L removed.
 * You can use contains from above for this.
 */

def difference(l: IntList, L: IntList): IntList ={
  ???  
}
