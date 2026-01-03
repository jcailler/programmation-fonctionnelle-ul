package patmat
import  IntList.* 
import IntIntList.*

enum ExtractResult:
  case SecondElem(i: Int)
  case NotLongEnough
  case EmptyList

def extractSecond(l: IntList): ExtractResult =
  ???

def zip(l1: IntList, l2: IntList): IntIntList =
  ???

def unzip(l: IntIntList): (IntList, IntList) =
  ???

def movingWindow(l: IntList): IntIntList =
  ???
