/**
  * Created by drproduck on 2/23/17.
  */
object factorial_recursion {
  def fact1(n: Int): Int = {
    if (n == 1) 1
    else n * fact1(n - 1)
  }

  def fact2(n: Int): Int = {
    var nn: Int = n
    var res = 1;
    while (n > 0) {
      res *= n
      nn -= 1;
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

  println(fact2(a))
  println(fact3(a))
}