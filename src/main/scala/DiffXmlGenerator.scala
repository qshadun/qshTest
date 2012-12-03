import L10nKeyCheck._
import java.io.File
import java.util.Properties
import java.io.FileReader

object DiffXmlGenerator {
  val PROPERTY_FILE_BASE_PATH = "C:/devl/projects/rc920/deployer/src/main/resources/utilities/Upgrade/current/l10nResources"
  val DIFF_XML_FILE_PATH = "C:/devl/projects/rc920/deployer/src/main/resources/utilities/Upgrade/current/properties"
//  val PROPERTY_FILE_NAME = "customizable-labels"
//  val KEYS_TO_ADD = List("tenant.label.plural")
//  val ADD_AFTER_POSITION = "tenant.label.capital.plural"
  val PROPERTY_FILE_NAME = "enumeration-labels"
  val KEYS_TO_ADD = List("StatusEnum.DENIED", "StatusEnum.CANCELLED")
  val ADD_AFTER_POSITION = "StatusEnum.UNKNOWN"
    
    
  val PROPERTY_SUFFIX = ".properties"
  val DIFF_XML_SUFFIX = ".diff.xml"
  
  
    
  def generateDiffXML(pf: String, xf: String, after: String, keys: List[String], locale: String) {
    val p = new Properties()
    p.load(new FileReader(pf))
    val text = keys.map {key =>
      val value = p.getProperty(key) 
        if ( value == null) "" 
        else key + "=" + Native2AsciiUtils.native2Ascii(value)
    }.mkString("\n")
    val xml = 
<properties-diff xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="D:\TestUpgrade\conf\Schema\properties-diff.xsd">
    <add-entry>
            <insert where="after">{after}</insert>
            <text>
{text}
            </text>
    </add-entry>
</properties-diff>
    scala.xml.XML.save(xf, xml)
  }
  
  def getFileName(path: String, name: String, suffix: String)
  	= path + File.separator + name + suffix
  	
  def main(args: Array[String]): Unit = {
    val languages = getLanguageNames(BASE_PATH, isLanguage)
    languages.foreach {l =>
      val pf = getFileName(concatPath(PROPERTY_FILE_BASE_PATH, l), PROPERTY_FILE_NAME, "_" + l + PROPERTY_SUFFIX)
      val xf = getFileName(DIFF_XML_FILE_PATH, PROPERTY_FILE_NAME, "_" + l + PROPERTY_SUFFIX + DIFF_XML_SUFFIX)
      generateDiffXML(pf, xf, ADD_AFTER_POSITION, KEYS_TO_ADD, l)
    }
  }
}