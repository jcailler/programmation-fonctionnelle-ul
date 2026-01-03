package threads
import akka.actor.{Actor, ActorRef}

case class ThreadMessage(threadId: Int)
case class RegisterThread(threadId: Int, actor: ActorRef)
case object StartCommunicationRDV
case object RendezvousComplete

class ThreadActor(threadId: Int, rendezvousActor: ActorRef) extends Actor {
  def receive: Receive = {
    case StartCommunicationRDV =>
      println(s"Thread $threadId sending message to rendezvous")
      rendezvousActor ! ThreadMessage(threadId)

    case RendezvousComplete =>
      println(s"Thread $threadId received rendezvous complete message")
      context.system.terminate()
  }
}

class RendezvousActor extends Actor {
  var receivedMessages: Set[Int] = Set.empty
  var threads: Set[ActorRef] = Set.empty

  def receive: Receive = {
    case RegisterThread(threadId, actor) =>
      threads += actor
      println(s"Thread $threadId registered")
      if (threads.size == 5) {
        // All threads registered, start communication
        threads.foreach(_ ! StartCommunicationRDV)
      }

    case ThreadMessage(threadId) =>
      receivedMessages += threadId
      println(s"Thread $threadId has arrived at the rendezvous point")

      if (receivedMessages.size == 5) {
        // All threads have arrived, send back the rendezvous complete message
        threads.foreach(_ ! RendezvousComplete)
        context.system.terminate()
      }
  }
}

