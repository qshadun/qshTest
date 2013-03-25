import org.apache.commons.io.FileUtils
import java.io.File

object RcJarDeployer {
  //val jarFileName = """D:\devl\rc920\onyx-commons\onyx-commons-lwsso\target\onyx-commons-lwsso-1.48.jar"""
  //val jarFileName = """D:\devl\rc920\app\server\businesslogic\services-impl\target\ccm-services-impl-9.20.0001.jar"""
//  val JAR_FILES= List(
//      """D:\devl\rc920\app\server\mam\mam90\target\ccm-mam-mam90-9.20.0001.jar""",
//      """D:\devl\rc920\app\server\mam\mam80\target\ccm-mam-mam80-9.20.0001.jar"""
//      )
  //val JAR_FILES = List("""D:\devl\rc920\sdi\remedy7\target\ccm-sdi-remedy7-9.20.0001.jar""")
  val JAR_FILES = List(
      """D:\devl\rc920\app\management\cli\target\ccm-management-cli-9.20.0001.jar"""
      )
  val RC_FOLDER_NAME = """D:\devl\rc920\target-deployer\ccm"""

  def calcTargetDirs(rcFolderName: String): List[File] = {
    val appsFolder = new File(rcFolderName, "apps")
    assert(appsFolder.isDirectory())
    
    val libFolder = new File(rcFolderName, "lib")
    
    val appLibFolder = appsFolder.listFiles() filter (_.isDirectory()) map (x => new File(x, "WEB-INF\\lib"))
    
    libFolder :: appLibFolder.toList
  }
  def deployOneJar(jarFileName: String, rcFolderName: String) = {
    val jarFile = new File(jarFileName)
    val jarName = jarFile.getName()
    
    val targetDirs = calcTargetDirs(rcFolderName)
    println("Source file is: " + jarFileName)
    targetDirs map {folder =>
      val f = new File(folder, jarName)
      if (f.exists) {
        FileUtils.copyFile(jarFile, f, true)
        println("Copied to " + f.getAbsolutePath())
      }
    }
  }
  def main(args: Array[String]): Unit = {
    JAR_FILES.foreach(deployOneJar(_, RC_FOLDER_NAME))
  }

}