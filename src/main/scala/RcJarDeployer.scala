import org.apache.commons.io.FileUtils
import java.io.File

object RcJarDeployer {
  val jarFileName = """D:\devl\rc920\onyx-commons\onyx-commons-lwsso\target\onyx-commons-lwsso-1.48.jar"""
  val rcFolderName = """D:\devl\rc920\target-deployer\ccm"""

  def calcTargetDirs(rcFolderName: String): List[File] = {
    val appsFolder = new File(rcFolderName, "apps")
    assert(appsFolder.isDirectory())
    
    val libFolder = new File(rcFolderName, "lib")
    
    val appLibFolder = appsFolder.listFiles() filter (_.isDirectory()) map (x => new File(x, "WEB-INF\\lib"))
    
    libFolder :: appLibFolder.toList
  }
  def main(args: Array[String]): Unit = {
    val jarFile = new File(jarFileName)
    val jarName = jarFile.getName()
    
    val targetDirs = calcTargetDirs(rcFolderName)
    targetDirs map {folder =>
      val f = new File(folder, jarName)
      if (f.exists) {
        FileUtils.copyFile(jarFile, f, true)
        println("Source file is: " + jarFileName)
        println("Copied to " + f.getAbsolutePath())
      }
    }
    
  }

}