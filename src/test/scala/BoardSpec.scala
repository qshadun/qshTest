
import TicTacToe._
import org.specs2.mutable._

class BoardSpecTest extends SpecificationWithJUnit {

  "A (5,3) board" should {
    "not win and not full when it's empty" in {
      val game = new ConnectedGame(5, 3)
      val board = game.createEmptyBoard
      game.getWinner(board) must be_==(B)
      game.isFull(board) must beFalse
    }
    
    "win with O when there is a column streak with 3 O " in {
      val game = new ConnectedGame(5, 3)
      val board = List(List(X, X, B, B, B),
        List(X, O, X, B, X),
        List(O, O, B, B, B),
        List(X, O, X, O, X),
        List(X, X, O, B, B))
      game.getWinner(board) must_== O
    }
    
    "win with X when there is a diagonal streak with 3 X" in {
      val game = new ConnectedGame(5, 3)
      val board = List(List(X, X, B, B, B),
        List(X, O, X, B, X),
        List(O, O, B, X, B),
        List(X, X, B, O, O),
        List(X, X, O, B, B))
      game.getWinner(board) must_== X
    }

    "not win and full when it's full no streak with 3 X/O exist" in {
      val game = new ConnectedGame(5, 3)
      val board = List(List(X, O, X, O, X),
        List(X, O, X, O, X),
        List(O, X, O, X, O),
        List(O, X, O, X, O),
        List(X, O, X, O, X))
      game.isFull(board) must beTrue
      game.getWinner(board) must_== B
    }
  }
}
