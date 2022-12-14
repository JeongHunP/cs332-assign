package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("until") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(until(singleton, combine)(leaflist) === List(Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e','t','x'),7)))
  }

  test("createCodeTree") {
    val chars = List('e','t','t','x','x','x','x')
    assert(createCodeTree(chars) === Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e','t','x'),7))
  }

  test("decode secret code") {
    val code: List[Char] = List('h','u','f','f','m','a','n','e','s','t','c','o','o','l')
    assert(decodedSecret === code)
  }

  test("encode" ) {
    new TestTrees {
      //encode empty
      assert(encode(t1)("".toList) === List())
      //encode text
      assert(encode(t2)("abd".toList) === List(0,0,0,1,1))
    }
  }

  test("convert codetable which includes mergeCodeTables") {
    new TestTrees {
      assert(convert(t2) === List(('a',List(0, 0)), ('b',List(0, 1)), ('d',List(1))))
    }
  }

  test("empty table for codeBits") {
    new TestTrees {
      assert(codeBits(List(('b', List())))('a') === List())
    }
  }

  test("quickencode and decode which includes convert and codeBits") {
    new TestTrees {
      assert(decode(t1, quickEncode(t1)("ab".toList)) === "ab".toList)
      assert(decode(t2, quickEncode(t2)("abd".toList)) === "abd".toList)
      assert(decode(t2, quickEncode(t2)("adb".toList)) === "adb".toList)
    }
  }
}

