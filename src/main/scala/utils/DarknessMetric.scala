package utils
//DarknessMetric assigns value from 0 to 100 to a picture.
//I planned od implementing WeightedMeanDarknessMetric (the closer to middle, the bigger the weight, because face is usually in the middle)
abstract class DarknessMetric {
  def calculateDarkness(pixels: IndexedSeq[RGBPoint]): Int
}

class MeanDarknessMetric extends DarknessMetric {
  override def calculateDarkness(pixels: IndexedSeq[RGBPoint]) = {
    val floatList: IndexedSeq[Float] =
      pixels.map(rgb => rgb.r + rgb.g + rgb.b).map(_.toFloat)
    val mean = floatList.sum / floatList.length
    val meanScaled = (mean * 100 / (256 * 3)).round
    100 - meanScaled
  }
}
