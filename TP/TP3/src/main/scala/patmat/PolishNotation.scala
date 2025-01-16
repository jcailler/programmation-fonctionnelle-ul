package patmat

enum PolishNotationAtom:
  case Add
  case Multiply
  case Number(n: Int)

enum PolishNotation:
  case PNil
  case PCons(atom: PolishNotationAtom, rest: PolishNotation)

import PolishNotationAtom.*
import PolishNotation.*

def plusOneTwo: PolishNotation = // + 1 2
  ???

def plusTwoTimesThreeFour: PolishNotation = // + 2 * 3 4
  ???

def polishEval(l: PolishNotation): (Int, PolishNotation) =
  ???
