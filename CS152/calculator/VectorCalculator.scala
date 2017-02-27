/**
  * Created by drproduck on 2/11/17.
  */
object VectorCalculator {
  def add(v1: Array[Int], v2: Array[Int]) : Array[Int]= {
    if (v1.length != v2.length) {
      throw new Exception("Unable to add 2 vectors of different lengths")
    }
    var a = 0
    var v: Array[Int] = new Array[Int](v1.length)
    for (a <- 0 until v1.length) {
      v(a) = v1(a) + v2(a);
    }
    return v;
  }

  def dot(v1: Array[Int], v2: Array[Int]) : Int = {
    if (v1.length != v2.length) {
      throw new Exception("2 Vectors of different lengths")
    }
    var a = 0;
    var res = 0;
    for (a <- 0 until v1.length) {
      res += v1(a)*v2(a)
    }
    return res
  }

  def toString(v: Array[Int]) = {
    var result = "["
    for(e <- v) {
      result = result + " " + e
    }
    result = result + "]"
    result
  }

  def main(args: Array[String]): Unit = {
    try {
      print("Enter 3 integers: ")
      var x = readInt()
      var y = readInt()
      var z = readInt()
      val vec1 = Array(x, y, z)
      val vec2 = Array(1, 2, 3)
      val vec3 = add(vec1, vec2)
      println("sum = " + toString(vec3))
      println("dot = " + dot(vec1, vec2))
    } catch {
      case e: Exception => {println(e)}
    }

  }
}
