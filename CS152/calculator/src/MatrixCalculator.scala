/**
  * Created by drproduck on 2/11/17.
  */
object MatrixCalculator {

  // converts matrix to a string
  def toString(matrix: Array[Array[Int]]) = {
    var a = 0;
    var b = 0;
    for (a <- 0 until matrix.length) {
      for (b <- 0 until matrix(a).length) {
        print(matrix(a)(b))
        if (b!=matrix(a).length-1){
          print(" ")
        }
      }
      if (a!=matrix.length-1)
      print("\n")
    }
  }

  // returns the sum of the diagonal entries of a matrix
  def trace(m: Array[Array[Int]]) : Int = {
    var a = 0
    var sum = 0;
    for (a <- 0 until m.length) {
      sum += m(a)(a)
    }
    return sum
  }

  // returns a dim x dim matrix with i/j entry = 3 * i + 2 * j % cap
  def makeArray(dim: Int, cap: Int = 100) : Array[Array[Int]] = {
    var matrix = Array.ofDim[Int](dim, dim)
    var i = 0;
    var j = 0;
    for (i <- 0 until dim) {
      for (j <- 0 until dim)
      matrix(i)(j) = 3*i + 2*j % cap
    }
    return matrix
  }

  def main(args: Array[String]): Unit = {
    print("Enter a positive integer: ")
    var n = readInt
    var m = makeArray(n)
    println(toString(m))
    println("trace = " + trace(m))
  }

}
