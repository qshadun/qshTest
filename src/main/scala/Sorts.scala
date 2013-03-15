object Sorts {

  def qSort[A<%Ordered[A]](ls: List[A]): List[A] = ls match {
    case Nil => Nil
    case x :: xs =>
      val (less, more) = xs.partition(_ < x)
      qSort(less) ::: x :: qSort(more)
  }
  
  def main(args: Array[String]): Unit = {
    println(qSort(List(1,5,2,3,6)))
    println(qSort(List(1)))
    println(qSort(List.empty[Int]))
    println(qSort("abasdae".toList))
    
    val comp = for (x <- List(1,2,3); b <- List('a','b')) yield (x,b)
    println(comp)
  }

}