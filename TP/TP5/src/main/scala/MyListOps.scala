package poly

import scala.annotation.tailrec
import MyList.*

// Helper functions
def append[A](l1: MyList[A], l2: MyList[A]): MyList[A] =
    l1 match
        case Nil => l2
        case Cons(x,xs) => Cons(x, append(xs, l2))


def map[A, B](l: MyList[A])(f: A => B): MyList[B] =
  l match
    case Nil         => Nil
    case Cons(x, xs) => Cons(f(x), map(xs)(f))

extension [A](l: MyList[A])
  def ++(that: MyList[A]): MyList[A] = append(l, that)

def allThreeLetterWords(words: MyList[String]): MyList[String] =
  filter(words)(_.length == 3)



// Higher-order functions

def filter[A](l: MyList[A])(p: A => Boolean): MyList[A] =
    ???

def foldRight[A, B](l: MyList[A])(f: (A, B) => B, base: B): B =
    ???

def reduceRight[A](l: MyList[A])(f: (A, A) => A): A =
    ???

def forall[A](l: MyList[A])(p: A => Boolean): Boolean =
    ???

def exists[A](l: MyList[A])(p: A => Boolean): Boolean =
    ???

def zip[A, B](l1: MyList[A], l2: MyList[B]): MyList[(A, B)] =
    ???

def zipWith[A, B, C](l1: MyList[A], l2: MyList[B])(op: (A, B) => C): MyList[C] =
      ???

def elementsAsStrings[A](l: MyList[A]): MyList[String] =
    ???

def length[A](l: MyList[A]): Int =
    ???

def takeWhilePositive(l: MyList[Int]): MyList[Int] =
    ???

def last[A](l: MyList[A]): A =
    ???

def capitalizeString(l: MyList[Char]): MyList[Char] =
    ???

// use that class to make the wordCount function
case class WordCountState(count: Int, lastWasWS: Boolean)

def wordCount(l: MyList[Char]): Int =
    ???

// Flatmap and cross-product

def flatMap[A, B](l: MyList[A])(f: A => MyList[B]): MyList[B] =
    ???

def flatten[A](l: MyList[MyList[A]]): MyList[A] = 
    ???

def crossProduct[A, B](l1: MyList[A], l2: MyList[B]): MyList[(A, B)] =
    ???


// Triangle

type NodeId = Int
type DirectedEdge = (NodeId, NodeId)
type DirectedGraph = MyList[DirectedEdge]
type Triangle = (NodeId, NodeId, NodeId)

def triangles(edges: DirectedGraph): MyList[Triangle] =
    ???


// FoldLeft and Tail Recursion

def sum0(l: MyList[Int]): Int = 
    l match
        case Nil         => 0
        case Cons(x, xs) => x + sum0(xs)
        
def sum1(l: MyList[Int]): Int ={
    //@tailrec // Uncomment this line.
    def sum(l: MyList[Int], acc: Int): Int =
        ???
    sum(l, 0)
}

//@tailrec // Uncomment this line.
def foldLeft[A, B](base: B, f: (B, A) => B)(l: MyList[A]): B =
    ???

def sum0Fold(l: MyList[Int]): Int =
    ???

def sum1Fold(l: MyList[Int]): Int =
    ???

def reverseAppend[A](l1: MyList[A], l2: MyList[A]): MyList[A] =
    ???

def reverse[A](l: MyList[A]): MyList[A] = reverseAppend(l, Nil)

val countEven: MyList[Int] => Int =
    ???

val totalLength: MyList[String] => Int =
    ???

