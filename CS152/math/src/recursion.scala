/**
  * Created by drproduck on 2/28/17.
  */
object recursion {
  def main(args: Array[String]): Unit = {
    println(addtail(4,3))
    println(sub(5,3))
    println(mul(5,3))
    println(mul(4, 0))
    println(mul(0,4))
    println(mul(20,1))
    println(mul(1,20))
    println(mul(6,7))
    println(exp2(5))
    //var start : Long  = System.nanoTime()
    println(hyperExp(3))
    //var end : Long = System.nanoTime()
    //println(end - start)
    //start = System.nanoTime()
    println(hyperExptail(3))
    //end = System.nanoTime()
    //println(end - start)
    println(exp2tail(5))
  }
  def add(a:Int, b:Int) : Int = {
    if (b==0) {
      a
    }
    else inc(add(a,dec(b)))
  }

  def addtail(a:Int, b:Int) :Int = {
    def helper (res:Int, count:Int): Int = if (count == b) res else helper(inc(res), count +1)
    helper(a, 0)
  }

  def inc(a:Int) = a+1
  def dec(a:Int) = a-1

  def sub(a:Int,b:Int) : Int = {
    if (b==0) {
      a
    }
    else dec(sub(a,dec(b)))
  }

  def subtail(a:Int, b:Int) : Int = {
    def helper(res: Int, count: Int):Int = if (count == b) res else helper(dec(res), count + 1)
    helper(a, 0)
  }

  def mul(a:Int, b:Int) : Int = {
    if (b==0) 0
    else if (b==1) a
    else add(mul(a,dec(b)), a)
  }

  def exp2(m:Int) : Int = {
    if (m==0) 1
    else mul(exp2(m-1), 2)
  }

  def exp2tail(m:Int) : Int = {
    def helper(res:Int, count:Int) : Int = {
      if (count==0) res
      else helper(mul(res, 2) , count-1)
    }
    helper(1, m)
  }

  def hyperExp (n:Int) : Int = {
    if (n==0) {
      1
    }
    else exp2(hyperExp(n-1))
  }

  def hyperExptail (n:Int) : Int = {
    def helper(res:Int, count:Int) : Int = {
      if (count == 0) res
      else helper(exp2(res), dec(count))
    }
    helper(1, n)
  }

  def expN(base : Int, exp : Int) : Int = {
    if (exp==0) 1
    else mul(expN(base, exp-1), base)
  }

  def ackermann (N: Int , m : Int) : Int = {
      if (m==0) 1
      else expN(N, ackermann(N, m-1))
  }
}
