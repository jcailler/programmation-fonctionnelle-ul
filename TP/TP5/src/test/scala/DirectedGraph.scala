package poly

import poly.MyList.*

class DirectedGraph extends munit.FunSuite:

  def isRotation(x1: Triangle, x2: Triangle) =
    x1 == x2 || (x1._1 == x2._2 && x1._2 == x2._3 && x1._3 == x2._1) || (x1._1 == x2._3 && x1._2 == x2._1 && x1._3 == x2
      ._2)

  test("triangles: find a triangle in the 3 edges example"):
    val edges = Cons((1, 2), Cons((2, 3), Cons((3, 1), Nil)))
    assertEquals(triangles(edges).tail.isEmpty, true)
    assert(isRotation(triangles(edges).head, (1, 2, 3)))

  test("triangles: find a triangle in the 5 edges example"):
    val edges = Cons((1, 2), Cons((2, 3), Cons((3, 1), Cons((1, 4), Cons((4, 2), Cons((2, 1), Nil))))))
    val res = triangles(edges)
    assertEquals(res.tail.tail, Nil)
    val t0 = res.head
    val t1 = res.tail.head
    assert(isRotation(t0, (1, 2, 3)) && isRotation(t1, (1, 4, 2)) || isRotation(t0, (1, 4, 2)) && isRotation(
      t1,
      (1, 2, 3)
    ))
