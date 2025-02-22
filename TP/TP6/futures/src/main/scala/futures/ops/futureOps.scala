package futures.ops

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Success, Failure}

extension [T](self: Future[T])
  def map[U](f: T => U): Future[U] =
    ???

extension [T](self: Future[T])
  def flatMap[U](f: T => Future[U]): Future[U] =
    ???

extension [T](self: Future[T])
  def zip[U](other: Future[U]): Future[(T, U)] =
    ???

def sequence[T](futures: List[Future[T]]): Future[List[T]] =
  ???

def race[T](futures: List[Future[T]]): Future[T] =
  ???
