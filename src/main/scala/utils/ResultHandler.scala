package utils

import java.io.File
import java.nio.file.{Files, StandardCopyOption}

//ResultHandler decides what to do with picture after calculating brightness
abstract class ResultHandler {
  def handle(filename: String, brightness: Int)
}

class CopyResultHandler(treshold: Int, inputDir: String, outputDir: String)
    extends ResultHandler {
  override def handle(filename: String, brightness: Int) = {
    val d1 = new File(inputDir + "/" + filename).toPath
    val infix = (if (brightness >= treshold) "_dark_" else "_bright_") + brightness

    val resultPath = outputDir + "/" + filename.replaceFirst(
      "(.[a-zA-Z0-9])",
      infix + "$1"
    )
    val d2 = new File(resultPath).toPath
    println(resultPath)
    Files.move(d1, d2, StandardCopyOption.ATOMIC_MOVE)
  }
}

class PrintResultHandler(treshold: Int) extends ResultHandler {
  override def handle(filename: String, brightness: Int) = {
    println(
      "Classified " + filename + " brignthness as " + brightness + " too dark:" + (brightness >= treshold)
    )
  }
}
