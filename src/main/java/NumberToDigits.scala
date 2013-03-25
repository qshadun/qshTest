

import scala.math.ScalaNumber
object NumberToDigits {
  def toDigits(l: BigInt): List[Int] = {
    def toDigitsR(lo: BigInt): List[Int] = {
      val quotient  = lo / 10
      val remainder = (lo % 10).toInt
      if (quotient == 0) List(remainder)
      else  remainder :: toDigitsR(quotient)
    }
    
    toDigitsR(l).reverse
  }
  
  def findNumber(): BigInt = {
    var n: BigInt = 1024
    var steps = 1;
    def isQualified(l: BigInt) = toDigits(l).take(5) == List(1,2,3,4,5)
    while(!isQualified(n) && steps <= 100) {n = n * 2; println(n); steps = steps + 1}
    n
  }
  def main(args: Array[String]): Unit = {
    assert(toDigits(0) == List(0), {println(toDigits(0))})
    assert(toDigits(10) == List(1, 0), {println(toDigits(10))})
    assert(toDigits(12345) == List(1, 2, 3, 4, 5), {println(toDigits(12345))})
    
    println(findNumber)
  }

}