import scala.io.Source
import java.io.PrintWriter
import QUtils._
import java.io.File
object ConvertToUtf8 {
  val srcFolder = "g:/temp/src"  
  val targetFolder = "g:/temp/target"
  
  def main(args: Array[String]): Unit = {
    val files = recursiveListFile(srcFolder)
    files.foreach {f =>
      val pw = new PrintWriter(new File(targetFolder, f.getName), "UTF-8")
      println(f.getName)
      Source.fromFile(f).getLines.foreach(pw.println(_))
      pw.close()
    }
  }

}