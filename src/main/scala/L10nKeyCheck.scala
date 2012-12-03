import java.util.ResourceBundle
import java.net.URLClassLoader
import java.io.File
import java.util.Locale
import java.util.Collections
import scala.collection.mutable
import java.io.PrintWriter
import java.io.PrintStream
object L10nKeyCheck {
  val BASE_PATH = "C:/devl/projects/rc920/app/l10n"
  val SUB_PATH = "src/main/resources"
//  val BASE_PATH = "C:/devl/projects/rc920/deployer/src/main/resources/utilities/Upgrade/current/l10nResources"
//  val SUB_PATH = ""
  val DEFAULT_FOLDER = "default"

  val DEFAULT_RESOURCE_FOLDER = concatPath(BASE_PATH, DEFAULT_FOLDER, SUB_PATH) 
  
  def getResourceUrl(basePath: String, lan: String, subPath: String) = 
    new File(concatPath(basePath, lan, subPath)).toURL
    
  def concatPath(s: String*) = s.mkString(File.separator)
  
  def getLanguageNames(basePath: String, p: String => Boolean) = {
    val files = new File(basePath).listFiles
    files.filter{f =>
      f.isDirectory() && p(f.getName())
    }.map(_.getName)
  }
  
  def isLanguage(s: String) = 
    s != null && ! s.isEmpty() && !s.startsWith(".") && s != DEFAULT_FOLDER && s != "support"
      
  def getResourceNames(path: String) = {
    val files = new File(path).listFiles
    files.filter{f =>
      f.getName.endsWith(".properties") && f.getName != "onyxClientStyle.properties"
    }.map{f =>
      val fn = f.getName
      fn.substring(0, fn.lastIndexOf('.'))
    }
  }
  
  def loadResourceKeys(localeName: String, ns: Array[String], classLoader: ClassLoader) = {
    val locale = new Locale(localeName)
    val lanKeysMap = scala.collection.mutable.Map.empty[String, Array[String]]
    ns.foreach{n =>
      val rb = ResourceBundle.getBundle(n, locale, classLoader)
      val keys = rb.keySet()
      lanKeysMap += n -> rb.keySet().toArray(new Array[String](keys.size))
    }
    lanKeysMap
  }
  
  def getSingleResourceLoader(basePath: String, lanFolder: String, subPath: String) = 
    new URLClassLoader(Array(getResourceUrl(basePath, lanFolder, subPath)))
  
  def compareKeys(lan: String, lanKeys: mutable.Map[String, Array[String]], defaultKeys: mutable.Map[String, Array[String]]) {
    def printDiff(diff: Array[String], n: String) = diff.length match {
      case 0 => 
      case _ =>
      	println("    " + n + ": " + diff.length)
      	diff.sortWith(_ < _).foreach(k => println("      " + k))
    }
    println(lan + ":")
    defaultKeys.foreach{
      case (rn, defaultkeys) => 
        if (!lanKeys.contains(rn)) println("  resource file " + rn + " is missing.")
        else {
          val keys = lanKeys(rn)
          keys.diff(defaultkeys)
          val missing = defaultkeys.filter(keys.indexOf(_) == -1)
          val extra = keys.filter(defaultkeys.indexOf(_) == -1)
          if (missing.length >0 || extra.length >0) {
            println("  " + rn + ":")
            printDiff(missing, "missing")
            printDiff(extra, "extra")
          }
        }
    }
  }
  
  def main(args: Array[String]) {
  	val resourceNames = getResourceNames(DEFAULT_RESOURCE_FOLDER)
  	val defaultResourceKeys = loadResourceKeys("", resourceNames, getSingleResourceLoader(BASE_PATH, DEFAULT_FOLDER, SUB_PATH))
  	
  	val languages = getLanguageNames(BASE_PATH, isLanguage)
//  	val reportPW = new PrintStream(new File("l10n_report.txt")) 
//  	System.setOut(reportPW)
  	for (l <- languages) {
  	  val lanResourceKeys = loadResourceKeys(l, resourceNames, getSingleResourceLoader(BASE_PATH, l, SUB_PATH))
  	  compareKeys(l, lanResourceKeys, defaultResourceKeys)
  	}
//  	reportPW.close()
  }

}