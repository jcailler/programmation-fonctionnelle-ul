# Functional Programming - TP 6: Threads and Concurrency

In this exercise, you are going to learn how to create threads and how to manage synchronization between them. Let us start!

## Meet Your First Thread ⭐️

You can find a skeleton for create a thread in [src/main/scala/threads/MyThread.scala](src/main/scala/threads/MyThread.scala). Take a look at the following code: 

```Scala
// Scala code for thread creation by extending 
// the Runnable class 
class MyTask extends Runnable {
  override def run(): Unit = {
    println(s"Thread ${Thread.currentThread().getName} is running")
    // Perform your task here
    Thread.sleep(1000)  // Simulate some work
    println(s"Thread ${Thread.currentThread().getName} has completed its task")
  }
}
```
As you can see, it uses some predefined functions, such as `currentThread()` and `getName()`. What does this thread do? 

<details><summary>Solution</summary>

This thread, once created, will run the function `run`. This function will make it print its number, wait for 1000 millisecond, and print that the task was completed.
</details>

In order to test you code, you are going to work with the `Main` class ([src/main/scala/Main.scala](src/main/scala/Main.scala)). You can run it by entering `run` in the sbt console. You should see something like this (note that the name of the thread can differ):

```Scala
[info] running Main 
Thread Thread-3 is running
Thread Thread-3 has completed its task
```

Take a look to the `Main` function:

```Scala
// Create a thread
val thread = new Thread(new MyTask())

// Start the thread
thread.start()

// Wait for the thread to complete
thread.join()
```

This function create a new `Thread` which will execute the function `run` of the task `MyTask` (defined in `MyTask.scala`). The thread can be launched thanks to the function `.start()`. Finally, you need to call `.join()` to wait for your thread to terminate.

Can you create a thread that is able to accept a parameter (for example, a new name) and print it?

<details><summary>Hint</summary>

You must define a new class, `MyTaskWithParameter`, that also extend Runnable, but in addition takes a parameter. Then, redefine the function `run` as in the previous example. 
```Scala
class MyTaskWithParameter(param: String) extends Runnable {
  override def run(): Unit = {
	???
  }
}
```
</details>


## Kill the Thread? But It has a Family! ⭐️

While killing people is clearly forbidden, killing threads is only depreciated. In Scala (as in Java), stopping a thread abruptly is generally not recommended due to the potential for leaving the program in an inconsistent state or causing resource leaks. Instead, the preferred approach is to design your threads to be cancellable by checking for a stop condition regularly within the thread's task.

However, if you need to forcefully stop a thread, you can use deprecated methods like `Thread.stop()`, but these are not safe and should be avoided. Instead, you can work with `Interruption`s and handle this error.

Take a look to the following class:
```Scala
// Define a class that implements Runnable and handles interruptions
class StoppableTask extends Runnable {
  override def run(): Unit = {
    println(s"Thread ${Thread.currentThread().getName} started")
    try {
      while (!Thread.currentThread().isInterrupted) {
        // Perform your task here
        println(s"Thread ${Thread.currentThread().getName} is running")
        Thread.sleep(500) // Simulate some work
      }
    } catch {
      case _: InterruptedException =>
        println(s"Thread ${Thread.currentThread().getName} was interrupted")
    }
    println(s"Thread ${Thread.currentThread().getName} has stopped")
  }
}
``` 
It is similar to your previous classes, but it implements `InterruptedException` to handle termination case. Now, try to use it in your `Main` program. What happens? Does your thread have time to run before being interrupted? How can I fix this?

<details><summary>Solution</summary>

If you did nothing, your main program will start the thread and interrupt it right away. You need to keep the `Main` thread busy in order to let some time to your `stoppableThread`. For instance, you can put the `Main` thread to sleep: 
```Scala
// Create and start a thread with a stoppable task
val stoppableThread = new Thread(StoppableTask())
stoppableThread.start()

// Let the thread run for some time (by putting the Main thread to sleep for a while)
Thread.sleep(2000)

// Stop the thread gracefully
stoppableThread.interrupt()
```
</details>

## Don't Leave It on Its Own ⭐️

Creating threads is fun, but for now, they are all running sequentially, which is a long way from the original goal. We are now going to generate multiple threads, running at the same time. To do so, create a new task that takes as parameter an `Int`, the number of time the thread is supposed to sleep. Then, thanks to a `For` loop, launch multiple threads with random sleeping times, and observe the result. 

<details><summary>How can I generate a random number?</summary>

In order to generate a random number, you need `scala.util.Random`. Then, you can generate a random `Int` between `[0, max[` with `.nextInt(max)`.

```Scala
import scala.util.Random

...
val random = new Random()
val myRandomNumber = random.nextInt(100)
```
</details>


<details><summary>Solution</summary>

Your code in the `Main` function should look like this:
```Scala
// Create and start multiple threads using a for loop
val threads = for (i <- 1 to 5) yield {
	val task = new MyTaskWithSleepingTime(/* random time */)
	val thread = new Thread(task)
	thread.start()
	thread
}

// Wait for all threads to complete
threads.foreach(_.join())
```
</details>



## The Sum of Many ⭐️

You are now able to manage multiple threads, congrats! Let us make them cooperate now. We want to make them collaborate to increase a counter. To do so, we are going to create a *shared variable* `sum`, accessible by all threads. Each thread has to add `1` to `sum`. Launch them and print the result. What is the value of `sum`? 

<details><summary>Shared variables?</summary>

In Scala, `Int` are by default pass by value, so if you create an `Int` and try to pass it to your thread, it won't be incremented. In order to properly share the variable, you can either:
* define `class IncrementTask` in the main
* Use an Array (which will be passed by references): `val sum = Array(0)`
* Define a mutable class for your `Int`
  
In our correction, we will use the second option. Thus, the `class` code is located in `MyThread.Scala` with the following parameters: 

```Scala
class IncrementTask(sum: Array[Int]) extends Runnable {
	???
}
```

While `Main.scala` contains:
```Scala
// Shared variable
val sum = Array(0)
```
</details>  
<br/>

You should see that your final value can not be equal to 10 000 (if not, try with more threads, or a random sleep time before to delay the increment). This is because all the threads try to access to the same variable at the same time, resulting in some conflicts. In order to properly manage collaborative work, we need to *synchronize* our threads. There are multiple ways to do it, but today we are going to present you *lock* and *safe data structure*. 

### Locks

A lock is designed to protect a certain portion of code, ensuring that only one thread can modify it at a time. A lock is shared between multiple threads, and only the one that has the lock can unlock the critical code portion and modify it, the others remaining is a waiting state. 
See the example code below to learn how to manipulate a `lock`. 

```Scala
import java.util.concurrent.locks.ReentrantLock

...
// Create a ReentrantLock
val lock = new ReentrantLock()

...
lock.lock() // Start of the critical section
try {
	// Increment the shared variable
} finally {
	// Release the lock
	lock.unlock() // End of the critical section
}
```

Try to modify your code by removing the `unlock` instruction. What's happening? This situation is called a *deadlock*, since other threads are waiting for the lock to be free, but the current one did not want to give it back. You should be very careful and try to avoid deadlocks, which can prevent your program from terminating!  


### Thread-Safe Data-Structure

Another way to avoid concurrency issues in Scala is to use thread-safe data-structure, as `AtomicInteger`s. An `AtomicInteger` only allow one atomic operation at a time, making it thread-safe and providing a convenient way to perform thread-safe operations on integer variables without the need for explicit locking. It's generally more efficient than using locks for simple atomic operations like incrementing or updating a counter.

```Scala
import java.util.concurrent.atomic.AtomicInteger

val sum = new AtomicInteger(0)
sum.incrementAndGet()
```

## Communication is the Key ⭐️

Let us see how threads can communicate. Message passing between threads is a common concurrency pattern used for communication and coordination. In Scala, you can achieve message passing using the `Akka` actor-based concurrency framework for the JVM. Note that actors are not native to Scala, and thus you need to include the dedicated library into [build.sbt](build.sbt):

```Scala
resolvers += "Akka library repository".at("https://repo.akka.io/maven"),
libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.13",
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.15" % Test
  )

lazy val akkaVersion = sys.props.getOrElse("akka.version", "2.9.3")

// Run in a separate JVM, to make sure sbt waits until all threads have
// finished before returning.
// If you want to keep the application running while executing other
// sbt tasks, consider https://github.com/spray/sbt-revolver/
fork := true
```
The previous code ensure that `Akka` is part of your project. If you want to include other libraries, you will have to follow the same pattern. 

An actor skeleton is available in [src/main/scala/threads/MyActor.scala](src/main/scala/threads/MyActor.scala). Take a look at the following code:

```Scala
case object StartCommunication

class ThreadActor1(actor2: ActorRef) extends Actor {
  def receive: Receive = {
    case StartCommunication =>
      println("ThreadActor1: Sending message to ThreadActor2")
      actor2 ! "Hello from ThreadActor1"
    case message: String =>
      println(s"ThreadActor1: Received message from ThreadActor2: $message")
      context.system.terminate()
  }
}

class ThreadActor2 extends Actor {
  def receive: Receive = {
    case message: String =>
      println(s"ThreadActor2: Received message from ThreadActor1: $message")
      sender() ! "Hello from ThreadActor2"
  }
}
```

This code defines two `Actor` classes, `ThreadActor1` and `ThreadActor2`, as well as an object `StartCommunication`. Now, take a look to the `Main`:

```Scala
// Create an ActorSystem
val system = ActorSystem("ThreadCommunicatorSystem")

// Create ThreadActor2
val actor2 = system.actorOf(Props(classOf[ThreadActor2]), "threadActor2")

// Create ThreadActor1 and pass the reference of ThreadActor2 to it
val actor1 = system.actorOf(Props(new ThreadActor1(actor2)), "threadActor1")

// Start the communication
actor1 ! StartCommunication
```

The main thread send a message `StartCommunication` to `actor1` thanks to the instruction `!`. Then, `actor1` prints the message and send a message to `actor2`, who send a message back. Try to play with this code to get familiar with these notions.

## Rendez-vous 🔥

Now, you are going to implement a famous synchronization paradigm: a rendez-vous. In a rendez-vous, each participant performs its own task, wait for the other to complete their task too, and then resume their work. We want to follow the following scheme: 

1. Every thread perform a given task (sleep for a random amount of time)
2. Once completed, it switches to a stalled state, waiting for the other threads to complete their own task
3. When all threads have reached the rendez-vous point, each thread resumes execution  

You should ask you the good questions: how does a thread know when to restart? How can it tell others that its task has finished? 
Try to do it in two different way: with shared variables and with message exchanges, with all threads doing the same thing or with one different one supervising everyone. Which one is the most easier for you? Why? 
