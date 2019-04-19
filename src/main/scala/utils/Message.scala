package utils
import java.io.File


case class ClassifyRequest(file: File,
                           outputDir: String,
                           darkClassifier: DarknessMetric)
case class Result(filename: String, brightness: Int)
case class BeginClassifying(config: Config)
