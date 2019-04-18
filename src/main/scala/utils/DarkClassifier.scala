package utils
abstract class DarkClassifier {
  def calculateBrightness(pixels: IndexedSeq[RGBPoint]): Int
}

class MeanClassifier extends DarkClassifier {
  override def calculateBrightness(pixels: IndexedSeq[RGBPoint]) = {
    val floatList: IndexedSeq[Float] =
      pixels.map(rgb => rgb.r + rgb.g + rgb.b).map(_.toFloat)
    val mean = floatList.sum / floatList.length
    val meanScaled = (mean * 100 / (256 * 3)).round
    100-meanScaled
  }
}
