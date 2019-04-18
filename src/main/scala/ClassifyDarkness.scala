import akka.actor.{ActorSystem, Props}
import utils.{DarkClassifier, MeanClassifier}

case class Config(nrOfThreads: Int,
                  acceptedExtensions: List[String],
                  darkClassifier: DarkClassifier,
                  bright_treshold: Int,
                  resultHandler: ResultHandler)

object ClassifyDarkness {
  //TODO this should be done using some JSON parsing
  def extractConfig(pathToConfig: String) =
    new Config(
      nrOfThreads = 2,
      List("jpg", "png"),
      new MeanClassifier,
	    bright_treshold = 80,
      new PrintResultHandler(80)
    )

  def classify(inputDir: String, outputDir: String) = {
    val system = ActorSystem("DarknessClassyfyingSystem")
    val classifierHub =
      system.actorOf(Props(new ClassifierHub(extractConfig(""))))
    classifierHub ! BeginClassifying(inputDir, outputDir)
  }

  def main(args: Array[String]): Unit = {
    val inputDir = args(0)
    val outputDir = args(1)
    println("Classyfing pictures from " + inputDir + " to " + outputDir)
    classify(inputDir, outputDir)
  }

}
