abstract class ResultHandler {
  def handle(filename: String, brightness: Int)
}

class CopyResultHandler(treshold: Int, inputDir: String, outputDir: String)
    extends ResultHandler {
  def handle(filename: String, brightness: Int) = {
    if (brightness >= treshold) {

    } else {}
  }
}

class PrintResultHandler(treshold: Int) extends ResultHandler {
  def handle(filename: String, brightness: Int) = {
    println(
      "Classified " + filename + " brignthness as " + brightness + " too dark:" + (brightness >= treshold)
    )
  }
}
