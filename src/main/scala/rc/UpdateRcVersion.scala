import java.io.File
import scala.io.Source
import java.io.PrintWriter
import QUtils._
object UpdateRcVersion {
    val ASSET_DIR = "C:/devl/projects/rc920"
    val OLD_VERSION = "9.20.0000"
    val NEW_VERSION = "9.20.0001"

  // for escalante
//  val ASSET_DIR = "C:/devl/projects/rc913"
//  val OLD_VERSION = "9.13.0001"
//  val NEW_VERSION = "9.13.0003"
  val otherFiles =
    List(
      "deployer.properties",
      "products.properties",
      """sdi\next-gen\sdi-configurator\src\main\groovy\com\mercury\onyx\sdi\folder\heuristics\FoldersLocationsHeuristics.groovy""",
      """sdi\next-gen\sdi-configurator\src\test\groovy\com\mercury\onyx\sdi\deploy\war\WarCreationTest.groovy""",
      """deployer\src\main\resources/utilities\patchUpgrade\patchUpgrade.bat""")

  val specFile = "tools/rpm/release-control.spec"

  def main(args: Array[String]) {
    // Update version in pom files
    println("Update version in pom files")
    val pomFiles = recursiveListFile(new File(ASSET_DIR))(skipMetaDataFile)(isPom)
    println("Found " + pomFiles.length + " pom files...")
    pomFiles.foreach(updateVersion(_))

    // Update additional files
    println("Update version in additional files")
    val additionalFiles = otherFiles.map { new File(ASSET_DIR, _) }
    additionalFiles.foreach(updateVersion(_))

    // Update spec file
    println("Update version in spec file")
    updateVersionInSpec

  }

  def skipMetaDataFile(file: File) = file.getName().startsWith(".")
  def isPom(file: File) = file.getName() == "pom.xml"
  def updateVersion(file: File, ov: String = OLD_VERSION, nv: String = NEW_VERSION, isUnix: Boolean = false) = {
    val in = Source.fromFile(file)
    val newLines = in.getLines().map { line =>
      line.replaceAll(ov, nv)
    }.toList
    using(new PrintWriter(file, "UTF-8")) { pw =>
      if (isUnix) newLines.foreach { line => pw.print(line + "\n") }
      else newLines.foreach(pw.println)
    }
  }

  // There is no snapshot version in spec file
  def updateVersionInSpec() {
    val ov = OLD_VERSION.replace("-SNAPSHOT", "")
    val nv = NEW_VERSION.replace("-SNAPSHOT", "")
    val f = new File(ASSET_DIR, specFile)
    updateVersion(f, ov, nv, true)
  }
}