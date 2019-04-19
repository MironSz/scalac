import java.awt.image.BufferedImage
import java.io.File

import akka.actor.Actor
import javax.imageio.ImageIO
import utils.{ClassifyRequest, DarknessMetric, RGBPoint, Result}
//ClassifierActor classifies brightness of picture using DarknessMetric's functionality.
class ClassifierActor extends Actor {

  def getPixelsFromPicture(pictureFile: File) = {
    def rgbToTriple(rgb: Int): (Int, Int, Int) =
      ((rgb >> 16) & 0x0ff, (rgb >> 8) & 0x0ff, rgb & 0x0ff)

    val img: BufferedImage = ImageIO.read(pictureFile)

    val w: Int = img.getWidth
    val h: Int = img.getHeight

    for {
      x <- 0 until w
      y <- 0 until h
    } yield {
      val rgb = rgbToTriple(img.getRGB(x, y))
      RGBPoint(x, y, rgb._1, rgb._2, rgb._3)
    }
  }

  def classify(file: File,
               outputDir: String,
               darkClassifier: DarknessMetric) = {
    val brightness =
      darkClassifier.calculateDarkness(getPixelsFromPicture(file))

    Result(file.getName, brightness)
  }

  override def receive: Receive = {
    case ClassifyRequest(file, outputDir, darkClassifier) => {
      sender ! classify(file, outputDir, darkClassifier)
    }
  }
}
