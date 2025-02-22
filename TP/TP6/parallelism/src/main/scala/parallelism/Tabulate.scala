package parallelism

import scala.reflect.ClassTag
import scala.collection.parallel.mutable.ParArray
import scala.collection.parallel.CollectionConverters.*

extension (a: Array.type)
  def seqTabulate[A: ClassTag](n: Int)(f: Int => A): Array[A] =
    ???

extension (p: ParArray.type) {
  def parTabulate[A: ClassTag](n: Int)(f: Int => A): ParArray[A] =
    ???
}

extension [A](seq: Array[A])
  def zipWith[B, C: ClassTag](f: (A, B) => C)(other: Array[B]): Array[C] =
    ???

def vectorAdd(a: Array[Int], b: Array[Int]) =
  a.zipWith((l: Int, r: Int) => l + r)(b)

