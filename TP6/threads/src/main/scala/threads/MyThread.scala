package threads
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.atomic.AtomicInteger

// Define a task by extending Runnable
class MyTask extends Runnable {
  override def run(): Unit = {
    println(s"Thread ${Thread.currentThread().getName} is running")
    // Perform your task here
    Thread.sleep(1000)  // Simulate some work
    println(s"Thread ${Thread.currentThread().getName} has completed its task")
  }
}

class MyTaskWithParameter() extends Runnable {
  override def run(): Unit = {
    ???
  }
}

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

class MyTaskWithSleepingTime() extends Runnable {
  override def run(): Unit = {
    ???
  }
}

// Task for each thread to increment the shared variable
class IncrementTask(sum: Array[Int]) extends Runnable {
	override def run(): Unit = {
		???
	}
}

// Task for each thread to increment the shared variable with a lock
class IncrementTaskLock(lock: ReentrantLock, sum: Array[Int]) extends Runnable {
  override def run(): Unit = {
    // Acquire the lock
    lock.lock()
    try {
      // Increment the shared variable
    } finally {
      // Release the lock
      lock.unlock()
    }
  }
}

// Task for each thread to increment the shared variable with an AtomicInteger
class IncrementTaskAtomicInteger(sum: AtomicInteger) extends Runnable {
  override def run(): Unit = {
    // Increment the shared variable in a thread-safe manner using AtomicInteger
    sum.incrementAndGet()
  }
}
