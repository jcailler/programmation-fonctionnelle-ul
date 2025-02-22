package futures.tasks

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

def managerRepl(): Unit =
  val ADD_RE = """add\s+(\d+)\s+(.*)""".r
  val ADD_AFTER_RE = """addafter\s+(\d+)\s+(\d+)\s+(.*)""".r
  val tasks = collection.mutable.ArrayBuffer.empty[Future[String]]

  @annotation.tailrec
  def loop(): Unit =
    val source = scala.io.StdIn.readLine(text = "> ")
    source.trim() match
      case "quit" | "exit" =>
        Await.result(Future.sequence(tasks), 2.seconds)
        ()
      case ADD_RE(duration, result) =>
        println(s"Started task")
        Thread.sleep(duration.toLong * 1000)
        println(s"Finished task: $result")
        loop()
      case ADD_AFTER_RE(after, duration, result) =>
        ???
      case s =>
        if s.nonEmpty then
          println(s"Unrecognized command: $source")
        loop()

  loop()
