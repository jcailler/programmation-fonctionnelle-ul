import recursion.*

2 + 2

val oneTwoMinusThreeFour = IntCons(1, IntCons(2, IntCons(-3, IntCons(4, IntNil()))))

oneTwoMinusThreeFour.isEmpty

oneTwoMinusThreeFour.head

oneTwoMinusThreeFour.tail

oneTwoMinusThreeFour.tail.head

// The call below currently fails with a `NotImplementedError`. Go to
// `src/main/scala/recursion/listOps.scala`, implement the `length` function,
// and uncomment the call below to test your implementation.

// length(oneTwoMinusThreeFour)
