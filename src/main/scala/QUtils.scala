import java.io.File
object QUtils {
  /**
   * Used for reading/writing to database, files, etc.
   * Code From the book "Beginning Scala"
   * http://www.amazon.com/Beginning-Scala-David-Pollak/dp/1430219890
   */
  def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B =
    try { f(param) } finally { param.close() }

  def recursiveListFile(root: File)(skip: File => Boolean)(filter: File => Boolean): Array[File] = {
    val files = root.listFiles
    files.filter(!skip(_)).flatMap {
      case f if f.isFile() && filter(f) => List(f)
      case dir if dir.isDirectory() => recursiveListFile(dir)(skip)(filter)
      case _ => Nil
    }
  }
  
  def recursiveListFile(path: String): Array[File] = recursiveListFile(new File(path))(_ => false)(_ => true)
}