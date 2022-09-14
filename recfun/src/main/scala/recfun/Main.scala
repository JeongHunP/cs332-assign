package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c==0 || c==r) 1
    else if(c<0 || r<0 || c>r) 0
    else pascal(c, r-1) + pascal(c-1, r-1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def numPair(num: Int, charList: List[Char]): Int = {
      if(num < 0) -1
      else if(charList.isEmpty) num
      else if(charList.head == '(' ) numPair(num+1, charList.tail)
      else if(charList.head == ')' ){
        if(num > 0) numPair(num-1, charList.tail)
        else -1
      } 
      else numPair(num, charList.tail)
    }

    numPair(0, chars) == 0
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money < 0) 0
    else if(money==0) 1
    else{
      if(coins.isEmpty) 0
      else if(money < coins.head) countChange(money, coins.tail)
      else countChange(money, coins.tail) + countChange(money-coins.head, coins)
    }
  }
}
