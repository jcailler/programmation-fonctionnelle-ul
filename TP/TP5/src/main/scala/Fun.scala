package poly

// Currying and composition

// A polymorphic method:
def foo[A](xs: List[A]): List[A] = ???

// A polymorphic function value:
val bar = [A] => (xs: List[A]) => foo(xs)

val curriedZipWith =
    ???

// Polymorphic Composition

def compose[A, B, C](f: B => C, g: A => B): A => C = ???

def andLifter[A](f: A => Boolean, g: A => Boolean): A => Boolean =
    a => f(a) && g(a)

def orLifter[A](f: A => Boolean, g: A => Boolean): A => Boolean =
    a => f(a) || g(a)

def sumLifter[A](f: A => Int, g: A => Int): A => Int =
    a => f(a) + g(a)

def listConcatLifter[A, B](f: A => MyList[B], g: A => MyList[B]): A => MyList[B] =
    a => append(f(a),g(a))

def binaryLifter[A, B, C](op: (B, B) => C)(f: A => B, g: A => B): A => C =
    ???

def andLifter1[A](f: A => Boolean, g: A => Boolean) =
    ???

def orLifter1[A](f: A => Boolean, g: A => Boolean) =
    ???

def sumLifter1[A](f: A => Int, g: A => Int) =
    ???

def listConcatLifter1[A, B](f: A => MyList[B], g: A => MyList[B]) =
    ???

def unaryLifter[A,B,C](op: B => C)(f: A => B): A => C =
    ???