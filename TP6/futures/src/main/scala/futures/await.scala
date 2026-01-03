package futures

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt

extension [T](self: Future[T])
  def await: T = Await.result(self, 3.seconds)
