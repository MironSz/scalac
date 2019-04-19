package utils

//Config file
//If done correctly, it should support parsing JSON to Config.
//Pushing config though all method calls and signals is also a bit ugly
case class Config(nrOfThreads: Int,
                  acceptedExtensions: List[String],
                  darkClassifier: DarknessMetric,
                  bright_treshold: Int,
                  resultHandler: ResultHandler,
                  inputDir: String,
                  outputDir: String)
object Config {
  def getDefaultConfig = new Config(
    nrOfThreads = 2,
    List("jpg", "png"),
    new MeanDarknessMetric,
    bright_treshold = 80,
    new utils.CopyResultHandler,
//    new PrintResultHandler,
    inputDir = "/home/miron/scala/scalac/src/test/photos/test",
    outputDir = "/home/miron/scala/scalac/src/test/photos/test"
  )

  //TODO this should be parsing JSON into config
  def JSONtoConfig(pathToConfig: String): Config =
    getDefaultConfig
}
