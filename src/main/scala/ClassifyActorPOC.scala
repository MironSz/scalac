import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO
import utils.{MeanClassifier, RGBPoint}

object ClassifyActorPOC{
  val acceptedExtensions: List[String] = List("jpg", "png")

  def checkExtension(file: File): Boolean = {
    val fileName = file.getName
    for (ext <- acceptedExtensions)
      if (fileName.endsWith("." + ext))
        true
    false
  }

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

  def classify(inputDir: String,outputDir:String,nrOfThreads:Int) = {
    val d = new File(inputDir)
    val classifier = new MeanClassifier()
    if (d.exists && d.isDirectory) {
      val files = d.listFiles.filter(_.isFile).toList.filter(checkExtension)

      for (file <- files) {
        println(file.getName)
        println(classifier.calculateBrightness(getPixelsFromPicture(file)))
      }
    } else {
//TODO
    }
  }
//  def main(args: Array[String]): Unit = {
//    classify("/home/miron/scala/scalac/photos/too_dark","",2)
//  }

}
