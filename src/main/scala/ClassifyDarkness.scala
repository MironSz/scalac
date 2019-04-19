import akka.actor.{ActorSystem, Props}
import utils._

//Main function. It takes paths to inputDir and outputDirs as arguments.
//In future optional third argument (path to JSON config) should be supported
object ClassifyDarkness {
  def extractConfig(pathToConfig: String, inputDir: String, outputDir: String) =
    Config.JSONtoConfig(pathToConfig, inputDir, outputDir)

  def classify(inputDir: String, outputDir: String) = {
    val system = ActorSystem("DarknessClassyfingSystem")
    val classifierHub =
      system.actorOf(
        Props(new ClassifierHub(extractConfig("", inputDir, outputDir)))
      )
    classifierHub ! BeginClassifying(inputDir, outputDir)
  }

  def main(args: Array[String]): Unit = {
    val inputDir = args(0)
    val outputDir = args(1)
    println("Classyfing pictures from " + inputDir + " to " + outputDir)
    classify(inputDir, outputDir)
  }
}
