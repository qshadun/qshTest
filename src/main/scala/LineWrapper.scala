import java.io.File
import java.io.PrintWriter
object LineWrapper extends Application {
  var WidthLimit = 60
  var inFile = "symptoms.txt"
  val outFile = inFile + ".w"

  def wrapLine(s: String): List[String] = s match {
    case "" => List("")
    case t if t.length <= WidthLimit => List(s)
    case _ => {
      val head = s.substring(0, WidthLimit)
      val lastSpace = head.lastIndexOf(" ")
      head.substring(0, lastSpace) :: (if (lastSpace < head.length) wrapLine(s.substring(lastSpace + 1))
      else Nil)
    }
  }

  /**
   * Used for reading/writing to database, files, etc.
   * Code From the book "Beginning Scala"
   * http://www.amazon.com/Beginning-Scala-David-Pollak/dp/1430219890
   */
  def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B =
    try { f(param) } finally { param.close() }

  import scala.io._

  val in = Source.fromFile(inFile)
  val out = new java.io.PrintWriter(outFile)

  val wrappedLines = in.getLines().flatMap(wrapLine(_))
  using(new PrintWriter(outFile)) {
    pw => wrappedLines.foreach(pw.println(_))
  }
}