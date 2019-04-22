package utils
import spray.json.{JsValue, _}
import scala.io.Source

//Config class. Config is read from give file.
//Pushing config though all method calls and signals seems a bit ugly,
case class Config(nrOfThreads: Int,
                  acceptedExtensions: List[String],
                  darkClassifier: DarknessMetric,
                  bright_treshold: Int,
                  resultHandler: ResultHandler,
                  inputDir: String,
                  outputDir: String)

//Needed for serializing and deserializing Config to/from JSON
object ConfigProtocol extends DefaultJsonProtocol {
  implicit object MetricJsonFormat extends RootJsonFormat[DarknessMetric] {
    def write(metric: DarknessMetric) =
      JsObject("metric" -> JsString("MeanDarknessMetric"))
    def read(value: JsValue) = {
      value.asJsObject.getFields("metric") match {
        case Seq(JsString("MeanDarknessMetric")) =>
          new MeanDarknessMetric
        case _ => deserializationError("Metric expected")
      }
    }
  }
  implicit object ResultHandlerJsonFormat
      extends RootJsonFormat[ResultHandler] {
    def write(resultHandler: ResultHandler) =
      JsObject("resultHandler" -> JsString(resultHandler.toString))
    def read(value: JsValue) = {
      value.asJsObject.getFields("resultHandler") match {
        case Seq(JsString("PrintResultHandler")) =>
          new PrintResultHandler
        case Seq(JsString("CopyResultHandler")) =>
          new CopyResultHandler
        case _ => deserializationError("Metric expected")
      }
    }
  }
  implicit val configFormat = jsonFormat7(Config.apply)
}

import ConfigProtocol._

object Config {
  def getDefaultConfig = new Config(
    nrOfThreads = 2,
    List("jpg", "png"),
    new MeanDarknessMetric,
    bright_treshold = 80,
    new utils.CopyResultHandler,
    inputDir = "/home/miron/scala/scalac/src/test/photos/test",
    outputDir = "/home/miron/scala/scalac/src/test/photos/test"
  )

  def JSONtoConfig(pathToConfig: String): Config = {
    val jsonConfigAsString = Source.fromFile(pathToConfig).getLines.mkString
    val config: Config = jsonConfigAsString.parseJson.convertTo[Config]
	  println(jsonConfigAsString)
    config
  }
}
