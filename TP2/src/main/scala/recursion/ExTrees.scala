package recursion

// Recursion on trees

/*
 * Here you find a framework for the exercises on recursion.
 * The exercises are linked with different topics which you
 * can check out in the README file of the exercise.
 */

/*
 * Implement a function that computes the size of an IntTree.
 * The size of a tree is the number of nodes it contains.
 */

def treeSize(t: IntTree): Int ={
  ???  
}

/*
 * Implement a function that computes the depth of an IntTree.
 * The depth of a tree is the number of edges on the longest path
 * between the root and a leaf. The example tree on the git repository
 * has a depth of 2.
 */

// To find the largest of two integers, you can use the scala.math.max function.

def treeDepth(t: IntTree): Int ={
  ???  
}

/*
 * Implement a function that computes the sum of all values in an IntTree,
 * similarly to the sum function for IntLists:
 */

def treeSum(t: IntTree): Int ={
  ???  
}

// Implement a function that determines if all values in an IntTree are even:

def treeAllEven(t: IntTree): Boolean = {
  ???
}

// Implement a function that increases each value in an IntTree by one:

def treeIncrement(t: IntTree): IntTree ={
  ???  
}

/*
 * Implement a function that creates a string containing all values in an IntTree,
 * in pre-order. This means that the value of each node must be printed
 * before its children, and then the values of the left subtree must be printed
 * before the values of the right subtree.
 *
 * For the example tree in the README file,
 * IntBranch(1, IntBranch(2, IntEmptyTree(), IntEmptyTree()), IntEmptyTree()),
 * the output should be:
 *
 * 1
 * 2
 * 
 * Your function must return a String.
 * 
 * To concatenate two strings, you can use the + operator:
 * 
 * scala> "Hello " + "Ada"
 * val res23: String = Hello Ada
 *
 * To convert an integer to a string, you can use the toString method:
 * 
 * scala> 3.toString
 * val res24: String = 3
 *
 * In Scala, like in Java, line breaks in strings are represented by
 * the \n character.
 */

def treeShow(t: IntTree): String ={
  ???  
}

/*
 * How would you modify your function to collect the values in post-order?
 * Try comparing the execution of the original pre-order function
 * and the post-order variant on an example, using the substitution model from the first lecture.
 */

 def treeShowPostOrder(t: IntTree): String = {
  ???
 }
