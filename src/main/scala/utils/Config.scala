package utils

//Config file
//If done correctly, it should support parsing JSON to Config.
case class Config(nrOfThreads: Int,
                  acceptedExtensions: List[String],
                  darkClassifier: DarknessMetric,
                  bright_treshold: Int,
                  resultHandler: ResultHandler)
object Config {
  def getDefaultConfig(inputDir: String, outputDir: String) = new Config(
    nrOfThreads = 2,
    List("jpg", "png"),
    new MeanDarknessMetric,
    bright_treshold = 80,
    new utils.CopyResultHandler(80, inputDir, outputDir)
//    new PrintResultHandler(80)
  )

  //TODO this should be parsing JSON into config
  def JSONtoConfig(pathToConfig: String,
                   inputDir: String,
                   outputDir: String): Config =
    getDefaultConfig(inputDir, outputDir)
}
