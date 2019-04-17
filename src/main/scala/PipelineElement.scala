import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

object PipelineElement {
  val acceptedExtensions: List[String] = List("jpg", "png")
  def checkExtension(file: File): Boolean = true
  def getPixelsFromPicture(pictureFile: File) = {
    def rgbToTriple(rgb: Int): (Int, Int, Int) =
      ((rgb >> 16) & 0x0ff, (rgb >> 8) & 0x0ff, rgb & 0x0ff)

    val img: BufferedImage = ImageIO.read(pictureFile)

    val w: Int = img.getWidth
    val h: Int = img.getHeight

//    for {
//      x <- 0 until w
//      y <- 0 until h
//    } yield ((x, y), rgbToTriple(img.getRGB(x, y)))
  }

  def classify(pathToDir: String) = {
    val d = new File(pathToDir)
    if (d.exists && d.isDirectory) {
      val files = d.listFiles.filter(_.isFile).toList.filter(checkExtension)

      for (file <- files) {
        println(file.getName)
        getPixelsFromPicture(file)
      }
    } else {
//TODO
    }
  }
  def main(args: Array[String]): Unit = {
    classify("/home/miron/scala/scalac/photos/bright")
  }

}
