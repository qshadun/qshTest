object Test {

  def main(args: Array[String]): Unit = {
    println(Runtime.getRuntime().availableProcessors())
    println(Runtime.getRuntime().freeMemory())
  }

}