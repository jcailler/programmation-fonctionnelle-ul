package threads
import akka.actor.{Actor, ActorRef}

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

