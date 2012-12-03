
object TicTacToe extends Enumeration {
  type TicTacToe = Value
  type TList = List[TicTacToe]  
  type TMatrix = List[TList]

  val X = Value("X")
  val O = Value("O")
  val B = Value(" ")


  
//  def same[T](t: (T, T, T)): Boolean = {
//    t._1 == t._2 && t._1 == t._3
//  }
//
//  def whoWin(board: TMatrix) = {
//    var winner = B
//    // check row and column
//    for (
//      i <- 0 to 2 if winner == B
//    ) if (board(i)(0) != B && same(board(i)(0), board(i)(1), board(i)(2)))
//      winner = board(i)(0)
//    else if (board(0)(i) != B && same(board(0)(i), board(1)(i), board(2)(i)))
//      winner = board(0)(i)
//    // check diagonal
//    if (winner == B && board(1)(1) != B)
//      if (same(board(0)(0), board(1)(1), board(2)(2)) ||
//          same(board(0)(2), board(1)(1), board(2)(0)))
//        winner = board(1)(1)
//    winner
//  }

  def toTicTacToeRow(s: String): List[TicTacToe] = {
    if (s.length == 0) Nil
    else TicTacToe.withName(s.head.toString) :: toTicTacToeRow(s.tail)
  }

  def toTicTacToeBoard(b: List[String]) = b.map(toTicTacToeRow(_))
}

import TicTacToe._
object TicTacToeTest {

  def main(args: Array[String]) {
    val board = List("X O ", "OX  ", "OXX ", "OXOX")
    val game = new ConnectedGame(4,4)
    val winner = game.getWinner(toTicTacToeBoard(board))
    if (winner == B) println("Tie")
    else println("Winner is: " + winner)
//    val cords = getAllDiagonalCords(4)
//    println(cords)
    
  }
}

