import scala.collection.parallel.mutable.ParArray
import scala.collection.parallel.CollectionConverters.*

extension [A](l: List[A])
  def aggregate[B](z: B)(seqop: (B, A) => B, combop: (B, B) => B): B =
    l match
      case l if l.length <= 2 => l.foldLeft(z)(seqop)
      case _ =>
        val half = l.length / 2
        val (left, right) = l.splitAt(half)
        val List(leftF, rightF) =
          List(left, right).par
            .map(elem => elem.aggregate(z)(seqop, combop))
            .toList
        combop(leftF, rightF)

  // end aggregateMapReduce

  def average(a: List[Int]): Int =
    val (sum, length) = a.aggregate((0, 0))(
      (acc, element) => (acc._1 + element, acc._2 + 1),
      (accL, accR) => (accL._1 + accR._1, accL._2 + accR._2)
    )
    sum / length


average(List(1,2,3))
