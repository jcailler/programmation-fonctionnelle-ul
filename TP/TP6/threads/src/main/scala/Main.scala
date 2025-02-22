import threads.*
import scala.util.Random
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.atomic.AtomicInteger
import akka.actor.{ActorRef, ActorSystem, Props}


object Main extends App {

  /** Task without parameter* */
  // Create a thread
  val thread = new Thread(new MyTask())

  // Start the thread
  thread.start()

  // Wait for the thread to complete
  thread.join()


  /** Task with parameters * */
  // Create and start a thread with parameters
  val threadWithParam = ???

  // // Start the thread
  // threadWithParam.start()

  // // Wait for the thread to complete
  // threadWithParam.join()


  /** Stoppable task * */
  // Create and start a thread with a stoppable task
  val stoppableThread = new Thread(StoppableTask())
  stoppableThread.start()

  // Stop the thread gracefully
  stoppableThread.interrupt()

  // Wait for the thread to complete
  thread.join()


  /** Multiple threads * */
  // Create and start multiple threads using a for loop
  ???

  /** Shared sum * */
  // Shared variable
  val sum = ???

  // Create and start 100 threads
  val sumThreads = for (i <- 1 to 100000) yield {
    ???
  }

  // Wait for all threads to complete
  // sumThreads.foreach(_.join())

  // Print the final value of sum
  // println(s"Final value of sum: ${???}")


  /** Safer shared sum --- Lock edition * */
  // Shared variable
  val sumLock = ???

  // Create a ReentrantLock
  val lock = new ReentrantLock()

  // Create and start 100 threads
  val threadsLock = for (i <- 1 to 100) yield {
    ???
  }

  // Wait for all threads to complete
  // threadsLock.foreach(_.join())

  // Print the final value of sum
  // println(s"Final value of sum: ${???}")


  /** Safer shared sum --- AI edition * */
  // Shared variable as an AtomicInteger
  val sumAI = new AtomicInteger(0)

  // Create and start 100 threads
  val threadsAI = for (i <- 1 to 100) yield {
    ???
  }

  // Wait for all threads to complete
  // threadsAI.foreach(_.join())

  // Print the final value of sum
  // println(s"Final value of sum: ${sumAI.get()}")

  /** Actors system */
  // Create an ActorSystem
  val system = ActorSystem("ThreadCommunicatorSystem")

  // Create ThreadActor2
  val actor2 = system.actorOf(Props(classOf[ThreadActor2]), "threadActor2")

  // Create ThreadActor1 and pass the reference of ThreadActor2 to it
  val actor1 = system.actorOf(Props(new ThreadActor1(actor2)), "threadActor1")

  // Start the communication
  actor1 ! StartCommunication


  /** Rendez-vous shared variable **/

  /** Rendez-vous actors **/

  println("Main thread has finished execution")
}
