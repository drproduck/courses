

/**
  * Created by drproduck on 3/14/17.
  */
object Lab {
  def id[T](x:T) = x

  def compose[T, A, B](f: A=>T, g: B=>A ) : B=> T = {
    def r(x: B) : T = f(g(x));
    r _
  }

  def selfIter[T](f:T=>T, n:Int): T=>T = {
    def helper(g: T=>T, n:Int): T=>T = {
      if (n==0) id _
      else if (n > 1) helper(compose(f, g), n - 1)
      else g
    }
    helper(f, n)
  }
  def selfIter2[T](f:T=>T, n:Int): T=>T = {
    def r(x:T) = {
      var res = x
      for(i <- 1 to n) res = f(res)
      res
    }
    r _
  }

  def main(args: Array[String]): Unit = {
    def g(a:String): Int = {
      a.hashCode
    }
    def f(a:Int): Double = {
      a.toDouble
    }
    def len(s:String) = s.length
    def isEven(x:Int) = x% 2 == 0
    println(compose(f, g)("abc"))
    println(compose(isEven, len)("abcc"))
    println(compose(isEven, len)("abc"))

    def inc(a:Int) = a+1
    println(selfIter(inc, 5)(3))
    println(selfIter2(inc, 5)(3))
    println(selfIter(inc, 0)(3))
  }
}
