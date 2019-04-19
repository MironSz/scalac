import java.io.File

import akka.actor.{Actor, Props}
import akka.routing.BalancingPool
import utils.{BeginClassifying, ClassifyRequest, Config, Result}
//ClassifierHub is responsible for starting and coordinating ClassifierActors
//and delegating results' handling  to ResultHandler. It also shutdown whole system after everything is calculated.

class ClassifierHub(config: Config) extends Actor {
  def checkExtension(file: File): Boolean = {
    val fileName = file.getName
    config.acceptedExtensions
      .map(ext => fileName.endsWith("." + ext))
      .foldLeft(false)((a, b) => a || b)
  }
  var filesToClassify: Int = 0
  var allFilesQueued: Boolean = false

  def beginClassyfing(config:Config) = {
    val d = new File(config.inputDir)

    val workerRouter = context.actorOf(
      Props[ClassifierActor].withRouter(BalancingPool(config.nrOfThreads)),
      name = "workerRouter"
    )

    if (d.exists && d.isDirectory) {} else {
      //TODO throw some error
    }

    val files = d.listFiles.filter(_.isFile).toList.filter(checkExtension)
    filesToClassify = files.length
    for (file <- files)
      workerRouter ! ClassifyRequest(file, config.outputDir, config.darkClassifier)

  }

  override def receive: Receive = {
    case BeginClassifying(config) =>
      beginClassyfing(config)
    case Result(filename, brightness) => {
      config.resultHandler.handle(filename, brightness,config)
      //I think only one thread handles ClassifierHub,
      //therefore decrementing filesToClassify should be safe
      filesToClassify = filesToClassify - 1
      if (filesToClassify == 0) {
        context.system.terminate()
      }
    }
  }
}
