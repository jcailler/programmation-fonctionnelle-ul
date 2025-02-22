package futures.parallel

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration.Inf

case class ParList[+T](data: List[T]):
  def map[U](f: T => U): ParList[U] =
    ???

  def flatMap[U](f: T => ParList[U]): ParList[U] =
    ???

  def filter(p: T => Boolean): ParList[T] =
    ???
