package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) { case (block, move) => move match {
        case Left => block.left
        case Right => block.right
        case Up => block.up
        case Down => block.down
      }
    }
  }

  trait Level0 extends SolutionChecker {
    val level =
    """ST
      |oo
      |oo""".stripMargin

      val optsolution = List(Down, Right, Up)
  }

  test("terrain function level 0") {
    new Level0 {
      assert(terrain(Pos(0,0)), "0,0")
      assert(!terrain(Pos(0,2)), "0,2")
    }
  }

  test("findChar level 0") {
    new Level0 {
      assert(startPos == Pos(0,0))
      assert(goal == Pos(0,1))
    }
  }

  test("optimal solution for level 0") {
    new Level0 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 0") {
    new Level0 {
      assert(solution.length == optsolution.length)
      assert(solution == optsolution)
    }
  }

  test("pathsFromStart for level 0"){
    new Level0 {
      assert(pathsFromStart == Stream((Block(Pos(0,0),Pos(0,0)),List()), 
                                  (Block(Pos(1,0),Pos(2,0)),List(Down)), 
                                  (Block(Pos(1,1),Pos(2,1)),List(Right, Down)), 
                                  (Block(Pos(0,1),Pos(0,1)),List(Up, Right, Down))))
    }
  }

  test("pathsToGoal for level 0"){
    new Level0 {
      assert(pathsToGoal == Stream((Block(Pos(0,1),Pos(0,1)),List(Up, Right, Down))))
    }
  }

  trait Level1 extends SolutionChecker {
      /* terrain for level 1*/

    val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0,0)), "0,0")
      assert(!terrain(Pos(4,11)), "4,11")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1,1))
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }
}
