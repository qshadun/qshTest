import org.apache.commons.io.FileUtils
import java.io.File

object RcJarDeployer {
  val jarFileName = """C:\devl\projects\rc920\onyx-commons\onyx-commons-lwsso\target\onyx-commons-lwsso-1.48.jar"""
  val rcFolderName = """C:\devl\projects\rc920\target-deployer\ccm"""

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
    
    
  }

}