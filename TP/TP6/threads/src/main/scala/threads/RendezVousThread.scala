package threads

import java.util.concurrent.atomic.AtomicInteger

class ThreadTaskRDV(threadId: Int, numThreads: Int, counter: AtomicInteger) extends Thread {
  override def run(): Unit = {
    counter.incrementAndGet()
    println(s"Thread $threadId arrived at the rendezvous point")
    if (counter.get() == numThreads) {
      println("All threads have arrived at the rendezvous point")
    } else {
      // Wait until all threads have arrived
      while (counter.get() < numThreads) {
        Thread.`yield`() // or sleep --- this is active wait, which is bad, we must prefer passive wait
      }
    }
  }
}