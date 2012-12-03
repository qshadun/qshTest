import TicTacToe._
import scala.collection.mutable.ListBuffer

class ConnectedGame(val dimension: Int, val streak: Int) {
  type TicBoard = List[List[TicTacToe]]
  val diagonalCords: List[List[(Int, Int)]] = getAllDiagonalCords(dimension)
  
  def createEmptyBoard = List.fill(dimension)(List.fill(dimension)(B))
  
  def play {
    while (true) {
	    var board = createEmptyBoard
	    var winner = getWinner(board)
	    println("TicTacToe game begins:")
	    println(toPrintString(board))
	    var i = 0
	    while (!isFull(board) && winner == B) {
	      val player = if (i % 2 == 0) X else O
	      println(player + "'s turn, please enter a move x, y:")
	      var move = retry { readMove }
	      while (!validMove(board, move))
	        move = retry { readMove }
	      board = applyMove(board, move, player)
	      println(toPrintString(board))
	      winner = getWinner(board)
	      i = i + 1
	    }
	    if (winner == B) println("Tie")
	    else {
	    	println("Winner is " + winner) 
	      println("===" * dimension)
	    }
    }
  }
  
  def toPrintString(board: TicBoard) = {
    val lineHead = (1 to board.length).map{n =>
      if(n < 10) " " + n + " "
      else n + " "
    }.mkString
    var i = 0
    val ident = if (board.length < 10) "" else " "
    val matrixString = board.map { row =>
      i = i + 1
      val rowString = row.map("(" + _ + ")").mkString
      if (i < 10) ident + i + rowString
      else i + rowString
    }.mkString("\n")
    ident + " " + lineHead + "\n" + matrixString
  }

  def isFull(board: TicBoard) = {
    val blanks = board.map(row => row.filter(_ == B))
    blanks.forall(_.length == 0)
  }

  def validMove(board: TicBoard, cord: (Int, Int)) = {
    val (y, x) = cord
    x > 0 && x <= board.length &&
    y > 0 && y <= board.length &&
    board(x - 1)(y - 1) == B
  }

  def applyMove(board: TicBoard, cord: (Int, Int), player: TicTacToe) = {
    val (y, x) = cord // x is column, y is row
    val (pre, remain) = board.splitAt(x - 1)
    val (rowPre, rowRemain) = remain.head.splitAt(y - 1)
    val changedRow = rowPre ::: (player :: rowRemain.tail)
    pre ::: (changedRow :: remain.tail)
  }

  def readMove(): (Int, Int) = {
    val l = Console.readLine().split(',').map(_.toInt)
    require(l.length >= 2)
    (l(0), l(1))
  }

  def retry[T](expr: => T): T = {
    var i: Option[T] = None
    do {
      i = try { Some(expr) } catch { case _ => None }
    } while (i == None)
    i.get
  }
  
  private def findConnected(l: TList):TicTacToe = {
    if (l.length < streak) B
    else if (l.head != B && l.takeWhile(_ == l.head).length >= streak) l.head
    else findConnected(l.tail)
  }
  
  private def findConnectedInMatrix(m: TMatrix):TicTacToe = {
    var result = B
    for ( l <- m if result == B) result = findConnected(l)
    result
  }
  
  // Transform the matrix: row -> column
  private def rowToColumn(m: TMatrix): TMatrix = m match{
    case Nil => Nil
    case List(Nil, _*) => Nil
    case _ => val heads = m.map(_.head)
    	val tails = m.map(_.tail)
    	heads :: rowToColumn(tails)
  } 
  
  private def getAllDiagonalCords(n: Int): List[List[(Int, Int)]] = {
  	var cords = new ListBuffer[List[(Int, Int)]]()
  	for (i <- 0 until n) {
  	  val downRight1 = for(y <- 0 until n-i) yield (y+i, y)
  	  val downRight2 = for(y <- n - 1 -i until n) yield (i - (n - 1 -y) ,y)
  	  val upRight1 = for(y <- 0 to i) yield(i-y, y)
  	  val upRight2 = for(y <- n-1-i until n) yield(n- 1 - i + (n - 1 - y), y)
  	  
  	  if (downRight1.length > 1 && i != 0) // only add longest diagonal once
  	  	cords += downRight1.toList
  	 
  	  if (downRight2.length > 1)
  	  	cords += downRight2.toList
  	  
  	  if (upRight1.length > 1 && i != n - 1)
  	  	cords += upRight1.toList
  	  	
  	  if (upRight2.length > 1)
  	  	cords += upRight2.toList
  	}
  	cords.toList  
  }
   
  private def getDiagonalMatrix(m: TMatrix): TMatrix = {
    diagonalCords.map{aCords =>
      aCords.map{cord =>
        m(cord._1)(cord._2)
      }
    }
  }
  
  def getWinner(board: TMatrix):TicTacToe = {
    var result = findConnectedInMatrix(board) //check row first
    if (result == B)
      result = findConnectedInMatrix(rowToColumn(board)) // check column second
    if (result == B)
      result = findConnectedInMatrix(getDiagonalMatrix(board)) // check diagonal last
    result
  }
}

object PlayTicTacToe {
  def main(args: Array[String]) {
    val five = new ConnectedGame(9, 5)
    five.play
  }
}