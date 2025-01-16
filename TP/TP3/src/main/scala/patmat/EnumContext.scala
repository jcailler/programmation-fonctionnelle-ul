package patmat

object EnumContext:
  enum Context:
    case Empty
    case Cons(name: String, value: Int, tail: Context)

  enum LookupResult:
    case Ok(v: Int)
    case NotFound

  import Context.*
  import LookupResult.*

  def empty: Context =
    ???

  def cons(name: String, value: Int, rem: Context) =
    ???

  def lookup(ctx: Context, name: String): LookupResult =
    ???

  def erase(ctx: Context, name: String): Context =
    ???
