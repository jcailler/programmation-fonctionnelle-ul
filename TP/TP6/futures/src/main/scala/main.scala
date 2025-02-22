import futures.ops.*

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt
import scala.concurrent.ExecutionContext.Implicits.global

import futures.friends.{
  makeFriendsWithCallback,
  makeFriendsMonadicFlatMap,
  makeFriendsMonadicFor,
  makeFriendsDirect,
  makeFriendsMonadicParallel,
  makeFriendsMonadicParallelFor,
  makeFriendsDirectParallel
}

def fail(msg: String): Unit =
  System.err.println(msg)
  System.exit(1)

@main def makeFriendsMain(version: String): Unit =
  def blockUntilDone(future: Future[Unit]) =
    println("Waiting for future to complete...")
    Await.result(future, 10.seconds)
    println("Aihsa and Carlos are now friends!")

  version match
    case "callbacks" =>
      println("Using callbacks")
      makeFriendsWithCallback(
        "aisha",
        "carlos",
        () =>
          println("Aihsa and Carlos are now friends!")
      )
      println("Waiting for callback to complete...")
      Thread.sleep(5000) // too bad we don't have another way to wait for the callback to complete.
    case "monadic-flatmap" =>
      println("Using monadic style and flatMap")
      blockUntilDone(makeFriendsMonadicFlatMap("aisha", "carlos"))
    case "monadic-for" =>
      println("Using monadic style and for")
      blockUntilDone(makeFriendsMonadicFor("aisha", "carlos"))
    case "direct" =>
      println("Using monadic style and direct")
      blockUntilDone(makeFriendsDirect("aisha", "carlos"))
    case "monadic-par" =>
      println("Using monadic style and parallel (1)")
      blockUntilDone(makeFriendsMonadicParallel("aisha", "carlos"))
    case "monadic-par-for" =>
      println("Using monadic style and parallel (2)")
      blockUntilDone(makeFriendsMonadicParallelFor("aisha", "carlos"))
    case "direct-par" =>
      println("Using direct style and parallel")
      blockUntilDone(makeFriendsDirectParallel("aisha", "carlos"))

@main def tasksMain(): Unit =
  futures.tasks.managerRepl()

@main def futuresTest(): Unit =
  import scala.concurrent.Future

  val f1 = Future { Thread.sleep(1000); println("Hello") }
  val f2 = f1.map(_ => println("world"))

  val f3 = Future:
    Thread.sleep(2000)
    1
  val f4 = Future:
    Thread.sleep(1000)
    2
  f3.zip(f4).map: (a, b) =>
    println(s"Result: ${a + b}")

  val fs = (1 to 5).toList.map: i =>
    Future:
      Thread.sleep(1000 * i)
      println(s"Number $i is finished!")
      i
  race(fs).map: i =>
    println(s"I know that $i will win")
