/**
  * Created by drproduck on 2/23/17.
  */
object factorial_recursion {
  def fact1(n: Int): Int = {
    if (n == 1) {
      1
    } else n * fact1(n - 1)
  }

  def fact2(n: Int): Int = {
    var nn: Int = n
    var res: Int = 1
    while (nn > 0) {
      res *= nn
      nn -= 1
    }
    res
  }

  def fact3(n: Int): Int = {
    def helper(res: Int, count: Int): Int = {
      if (count <= n) {
        helper(res * count, count + 1)
      }
      else res
    }

    helper(1, 1)
  }

  val a : Int = 5
  println(fact1(a))
  println(fact2(a))
  println(fact3(a))

}