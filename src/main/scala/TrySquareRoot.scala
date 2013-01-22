object TrySquareRoot {
	def trySqrt(guess: Double, n: Int, goodEnough: (Double, Int) => Boolean): Double = 
	  if (goodEnough(guess, n)) guess
	  else trySqrt(improve(guess, n), n, goodEnough)
	
	def improve(guess: Double, n: Int) = {
	    println(guess)
	    (guess + n / guess) / 2
	  }
	
	def sqrt(n: Int) = trySqrt(1, n, (x, y) => Math.abs(x - y / x) < 0.001)
	
  def main(args: Array[String]): Unit = {
	  println(sqrt(9))
	}

}