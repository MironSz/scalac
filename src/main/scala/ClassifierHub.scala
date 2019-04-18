import java.io.File

import akka.actor.{Actor, Props}
import akka.routing.BalancingPool

case class BeginClassifying(inputDir: String, outputDir: String)

class ClassifierHub(config: Config) extends Actor {
  def checkExtension(file: File): Boolean = {
    val fileName = file.getName
    config.acceptedExtensions
      .map(ext => fileName.endsWith("." + ext))
      .foldLeft(false)((a, b) => a || b)
  }

  def beginClassyfing(inputDir: String, outputDir: String) = {
    val d = new File(inputDir)

    val workerRouter = context.actorOf(
      Props[ClassifyActor].withRouter(BalancingPool(config.nrOfThreads)),
      name = "workerRouter"
    )

    if (d.exists && d.isDirectory) {} else {
      //TODO throw some error
    }

    val files = d.listFiles.filter(_.isFile).toList.filter(checkExtension)
    println(files)

    for (file <- files)
      workerRouter ! ClassifyRequest(file, outputDir, config.darkClassifier)
  }

  override def receive: Receive = {
    case BeginClassifying(inputDir, outputDir) =>
      beginClassyfing(inputDir, outputDir)
    case Result(filename, brightness) =>
	    config.resultHandler.handle(filename,brightness)
  }
}
