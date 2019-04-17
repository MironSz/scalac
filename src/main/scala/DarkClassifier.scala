abstract class DarkClassifier {
	 def calculateBrightness(pixels :List[(Int,Int,Int)],width:Int,height:Int):Int
}

class MeanClassifier extends DarkClassifier{
  override  def calculateBrightness(pixels: List[(Int, Int, Int)],
                                            width: Int,
                                            height: Int)={
	  val floatList :List[Float] = pixels.map(rgb=>rgb._1+rgb._2+rgb._3).map(_.toFloat)
	  val mean = floatList.sum/floatList.length
	  val meanScaled = (mean*100/(256*3)).round
		meanScaled
  }
}