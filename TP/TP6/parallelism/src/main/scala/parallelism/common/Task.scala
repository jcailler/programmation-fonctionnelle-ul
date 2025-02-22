package parallelism.common

import java.util.concurrent.*
import scala.util.DynamicVariable

object Task:
  val forkJoinPool = ForkJoinPool()

  abstract class TaskScheduler:
    def schedule[T](body: => T): ForkJoinTask[T]
    def parallel[A, B](taskA: => A, taskB: => B): (A, B) =
      val right = task {
        taskB
      }
      val left = taskA
      (left, right.join())

  class DefaultTaskScheduler extends TaskScheduler:
    def schedule[T](body: => T): ForkJoinTask[T] =
      val t = new RecursiveTask[T]:
        def compute = body
      Thread.currentThread match
        case wt: ForkJoinWorkerThread =>
          t.fork()
        case _ =>
          forkJoinPool.execute(t)
      t

  val scheduler =
    DynamicVariable[TaskScheduler](DefaultTaskScheduler())

  def task[T](body: => T): ForkJoinTask[T] =
    scheduler.value.schedule(body)
