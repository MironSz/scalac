import akka.actor.{ActorSystem, Props}
import utils._

//Main function. It takes paths to inputDir and outputDirs as arguments.
//In future optional third argument (path to JSON config) should be supported
object ClassifyDarkness {
  def extractConfig(pathToConfig: String) =
    Config.JSONtoConfig(pathToConfig)

  def classify(config:Config) = {

    val system = ActorSystem("DarknessClassyfingSystem")
    val classifierHub =
      system.actorOf(
        Props(new ClassifierHub(config))
      )
    classifierHub ! BeginClassifying(config)
  }

  def main(args: Array[String]): Unit = {
	  val config = extractConfig("this path is currently unused")
    val inputDir = config.inputDir
    val outputDir = config.outputDir
    println("Classyfing pictures from " + inputDir + " to " + outputDir)
    classify(config)
  }
}
