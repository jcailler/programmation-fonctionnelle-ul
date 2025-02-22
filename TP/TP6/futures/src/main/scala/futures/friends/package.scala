package futures
package friends

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}
import scala.collection.concurrent.TrieMap

case class User(id: String, friends: List[String])

case class UserNotFound(id: String) extends Exception(s"User $id not found")

def makeFriendsWithCallback(
    user1Id: String,
    user2Id: String,
    callback: () => Unit
): Unit =
  ???

def makeFriendsMonadicFlatMap(
    user1Id: String,
    user2Id: String
): Future[Unit] =
  ???

def makeFriendsMonadicFor(
    user1Id: String,
    user2Id: String
): Future[Unit] =
  ???

def makeFriendsDirect(
    user1Id: String,
    user2Id: String
): Future[Unit] =
  ???

def makeFriendsMonadicParallel(
    user1Id: String,
    user2Id: String
): Future[Unit] =
  ???

def makeFriendsMonadicParallelFor(
    user1Id: String,
    user2Id: String
): Future[Unit] =
  ???

def makeFriendsDirectParallel(
    user1Id: String,
    user2Id: String
): Future[Unit] =
  ???

val database = TrieMap(
  "aisha" -> User("aisha", List("barbara")),
  "barbara" -> User("barbara", List("aisha", "carlos")),
  "carlos" -> User("carlos", List("barbara"))
)

def getUserWithCallback(id: String, callback: User => Unit): Unit =
  getUser(id).onComplete {
    case Success(user) => callback(user)
    case Failure(_)    => throw UserNotFound(id)
  }

def getUser(id: String): Future[User] =
  Future:
    Thread.sleep(1000)
    println(f"Get user $id")
    database.getOrElse(id, throw UserNotFound(id))

def updateUserWithCallback(user: User, callback: () => Unit): Unit =
  updateUser(user).onComplete {
    case Success(_) => callback()
    case Failure(_) => throw UserNotFound(user.id)
  }

def updateUser(user: User): Future[Unit] =
  Future:
    Thread.sleep(1000)
    println(f"Update user ${user.id}")
    database.update(user.id, user)
