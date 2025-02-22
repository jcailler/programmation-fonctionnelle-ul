package parallelism

import collection.parallel.CollectionConverters.IterableIsParallelizable
import parallelism.common.Task.task

object FoldReduce:

  extension [A](l: List[A])
    def reduceWithFold(op: (A, A) => A): A =
      ???

  extension [A](l: List[A])
    def reducePar(op: (A, A) => A): A =
      ???

  extension [A](l: List[A])
    def aggregate[B](z: B)(seqop: (B, A) => B, combop: (B, B) => B): B =
     ???

end FoldReduce
