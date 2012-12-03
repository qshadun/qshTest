object SimSum {
  
  def simSum(as: Array[Int]) = {
    val m = scala.collection.mutable.Map[Int, Int]()
    for (x <- as) {
      if (m.contains(x)) m(x) = m(x) + 1
      else m(x) = 1
    }
    m.toList.map{t => t._1 * t._2}
  }
  
  def main(args: Array[String]) {
    println(simSum(Array(1,2,3,3,2,1,4,3)))
  }

}