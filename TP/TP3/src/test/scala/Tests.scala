package patmat 

class WeekdayOpsTest extends munit.FunSuite {

    test("next(Weekday.Monday) should be Tuesday"):
        assertEquals(next(Weekday.Monday), Weekday.Tuesday)
    test("next(Weekday.Tuesday) should be Wednesday"):
        assertEquals(next(Weekday.Tuesday), Weekday.Wednesday)
    test("next(Weekday.Wednesday) should be.Thursday"):
        assertEquals(next(Weekday.Wednesday), Weekday.Thursday)
    test("next(Weekday.Thursday) should be Friday"):
        assertEquals(next(Weekday.Thursday), Weekday.Friday)
    test("next(Weekday.Friday) should be Saturday"):
        assertEquals(next(Weekday.Friday), Weekday.Saturday)
    test("next(Weekday.Saturday) should be Sunday"):
        assertEquals(next(Weekday.Saturday), Weekday.Sunday)
    test("next(Weekday.Sunday) should be Monday"):
        assertEquals(next(Weekday.Sunday), Weekday.Monday)

    test("prev(Weekday.Monday) should be Sunday"):
        assertEquals(prev(Weekday.Monday), Weekday.Sunday)
    test("prev(Weekday.Tuesday) should be Monday"):
        assertEquals(prev(Weekday.Tuesday), Weekday.Monday)
    test("prev(Weekday.Wednesday) should be.Tuesday"):
        assertEquals(prev(Weekday.Wednesday), Weekday.Tuesday)
    test("prev(Weekday.Thursday) should be Wednesday"):
        assertEquals(prev(Weekday.Thursday), Weekday.Wednesday)
    test("prev(Weekday.Friday) should be Thursday"):
        assertEquals(prev(Weekday.Friday), Weekday.Thursday)
    test("prev(Weekday.Saturday) should be Friday"):
        assertEquals(prev(Weekday.Saturday), Weekday.Friday)
    test("prev(Weekday.Sunday) should be Saturday"):
        assertEquals(prev(Weekday.Sunday), Weekday.Saturday)
}



class TriBoolOpsTest extends munit.FunSuite {

    test("neg(TriBool.Yes) should be No"):
        assertEquals(neg(TriBool.Yes), TriBool.No)
    test("neg(TriBool.No) should be Yes"):
        assertEquals(neg(TriBool.No), TriBool.Yes)
    test("neg(TriBool.Maybe) should be Maybe"):
        assertEquals(neg(TriBool.Maybe), TriBool.Maybe)

    test("and(TriBool.Yes, Tribool.Yes) should be Yes"):
        assertEquals(and(TriBool.Yes, TriBool.Yes), TriBool.Yes)
    test("and(TriBool.Yes, Tribool.No) should be No"):
        assertEquals(and(TriBool.Yes, TriBool.No), TriBool.No)
    test("and(TriBool.Yes, Tribool.Maybe) should be Maybe"):
        assertEquals(and(TriBool.Yes, TriBool.Maybe), TriBool.Maybe)
    test("and(TriBool.No, Tribool.No) should be No"):
        assertEquals(and(TriBool.No, TriBool.No), TriBool.No)
    test("and(TriBool.No, Tribool.Maybe) should be No"):
        assertEquals(and(TriBool.No, TriBool.Maybe), TriBool.No)
    test("and(TriBool.Maybe, Tribool.Maybe) should be Maybe"):
        assertEquals(and(TriBool.Maybe, TriBool.Maybe), TriBool.Maybe)

    test("or(TriBool.Yes, Tribool.Yes) should be Yes"):
        assertEquals(or(TriBool.Yes, TriBool.Yes), TriBool.Yes)
    test("or(TriBool.Yes, Tribool.No) should be Yes"):
        assertEquals(or(TriBool.Yes, TriBool.No), TriBool.Yes)
    test("or(TriBool.Yes, Tribool.Maybe) should be Yes"):
        assertEquals(or(TriBool.Yes, TriBool.Maybe), TriBool.Yes)
    test("or(TriBool.No, Tribool.No) should be No"):
        assertEquals(or(TriBool.No, TriBool.No), TriBool.No)
    test("or(TriBool.No, Tribool.Maybe) should be Maybe"):
        assertEquals(or(TriBool.No, TriBool.Maybe), TriBool.Maybe)
    test("or(TriBool.Maybe, Tribool.Maybe) should be Maybe"):
        assertEquals(or(TriBool.Maybe, TriBool.Maybe), TriBool.Maybe)

    test("nand(TriBool.Yes, Tribool.Yes) should be No"):
        assertEquals(nand(TriBool.Yes, TriBool.Yes), TriBool.No)
    test("nand(TriBool.Yes, Tribool.No) should be Yes"):
        assertEquals(nand(TriBool.Yes, TriBool.No), TriBool.Yes)
    test("nand(TriBool.Yes, Tribool.Maybe) should be Maybe"):
        assertEquals(nand(TriBool.Yes, TriBool.Maybe), TriBool.Maybe)
    test("nand(TriBool.No, Tribool.No) should be Yes"):
        assertEquals(nand(TriBool.No, TriBool.No), TriBool.Yes)
    test("nand(TriBool.No, Tribool.Maybe) should be Yes"):
        assertEquals(nand(TriBool.No, TriBool.Maybe), TriBool.Yes)
    test("nand(TriBool.Maybe, Tribool.Maybe) should be Maybe"):
        assertEquals(nand(TriBool.Maybe, TriBool.Maybe), TriBool.Maybe)
}



class EnumContextTest extends munit.FunSuite {

    test("Empty should return Empty"):
        assertEquals(empty, Context.Empty)
    
    test("cons should add a new key-value pair to an existing context"):
        val ctx1 = cons("x", 1, empty)
        val ctx2 = cons("y", 2, ctx1)
        assertEquals(lookup(ctx2, "x"), LookupResult.Ok(1))
        assertEquals(lookup(ctx2, "y"), LookupResult.Ok(2))

    test("lookup should return the value associated with a key if found, or NotFound if not found"):
        val ctx = cons("x", 1, empty)
        assertEquals(lookup(ctx, "x"), LookupResult.Ok(1))
        assertEquals(lookup(ctx, "y"), LookupResult.NotFound)

    test("erase should remove all occurrences of a key from the context"):
        val ctx1 = cons("x", 1, empty)
        val ctx2 = cons("y", 2, ctx1)
        val ctx3 = cons("x", 3, ctx2)
        val ctx4 = erase(ctx3, "x")
        assertEquals(lookup(ctx4, "x"), LookupResult.NotFound)
        assertEquals(lookup(ctx4, "y"), LookupResult.Ok(2))
}

class IntListOpsTest extends munit.FunSuite {

    test("The values [-1, 2, 3] should return 5"):
        val list = IntCons(-1, IntCons(2, IntCons(3, IntNil())))
        assertEquals(polishEval(list)._1, 5)
    test("The values [-2, 2, 3] should return 10"):
        val list = IntCons(-2, IntCons(2, IntCons(3, IntNil())))
        assertEquals(polishEval(list)._1, 6)

    test("A appropriate list should return SecondElem(2)"):
        val list = IntCons(1, IntCons(2, IntCons(3, IntNil())))
        assertEquals(extractSecond(list), ExtractResult.SecondElem(2))
    test("A too short list should return NotLongEnough"):
        val list = IntCons(1, IntNil())
        assertEquals(extractSecond(list), ExtractResult.NotLongEnough)
    test("A empty list should return Emptylist"):
        val list = IntNil()
        assertEquals(extractSecond(list), ExtractResult.EmptyList)

    test("Zipping two lists should return a list of pairs"):
        val list1 = IntCons(1, IntCons(2, IntCons(3, IntNil())))
        val list2 = IntCons(4, IntCons(5, IntCons(6, IntNil())))
        assertEquals(zip(list1, list2), IntIntList.IntIntCons((1, 4), IntIntList.IntIntCons((2, 5), IntIntList.IntIntCons((3, 6), IntIntList.IntIntNil))))
    test("If one of the two lists is empty, IntIntNil should be returned"):
        val list1 = IntCons(1, IntNil())
        val list2 = IntNil()
        assertEquals(zip(list1, list2), IntIntList.IntIntNil)
    test("Unzipping a list of pairs should return two separate lists"):
        val input = IntIntList.IntIntCons((1, 4), IntIntList.IntIntCons((2, 5), IntIntList.IntIntCons((3, 6), IntIntList.IntIntNil)))
        val (result1, result2) = unzip(input)
        assertEquals(result1, IntCons(1, IntCons(2, IntCons(3, IntNil()))))
        assertEquals(result2, IntCons(4, IntCons(5, IntCons(6, IntNil()))))
    test("Unzipping a empty list should return IntNil"):
        val input = IntIntList.IntIntNil
        val (result1, result2) = unzip(input)
        assertEquals(result1, IntNil())
        assertEquals(result2, IntNil())
    test("movingwindow should return a consecutive list of pairs"):
        val input = IntCons(1, IntCons(2, IntCons(3, IntCons(4, IntNil()))))
        assertEquals(movingWindow(input), IntIntList.IntIntCons((1, 2), IntIntList.IntIntCons((2, 3), IntIntList.IntIntCons((3, 4), IntIntList.IntIntNil))))
    test("If the input list has less than two elements, IntIntNil should be returned"):
        val input = IntCons(1, IntNil())
        assertEquals(movingWindow(input), IntIntList.IntIntNil)
}

class IntTreeOpsTest extends munit.FunSuite {
    test("The treeMap functuion should return a tree like described in the task description"):
        val t = IntTree.Branch(IntTree.Branch(IntTree.Leaf(1), IntTree.Leaf(2)), IntTree.Leaf(3))
        assertEquals(treeMap(t, 1), IntTree.Branch(IntTree.Branch(IntTree.Leaf(2), IntTree.Leaf(3)), IntTree.Leaf(4)))
    test("The treeReduce functuion should return a tree like described in the task description"):
        val t = IntTree.Branch(IntTree.Branch(IntTree.Leaf(1), IntTree.Leaf(2)), IntTree.Leaf(3))
        assertEquals(treeReduce(t), 6)
}