/**
  * Created by drproduck on 2/28/17.
  */
object recursion {
  println("Welcome to Scala worksheet!")

  println(addtail(4,3))
  println(sub(5,3))
  println(mul(5,3))
  println(mul(4, 0))
  println(mul(0,4))
  println(mul(20,1))
  println(mul(1,20))
  println(mul(6,7))
  println(exp2(5))
  println(hyperExp(3))
  println(hyperExptail(3))
  println(exp2tail(5))
  println(newton_sqrt(152399025))
  printCombination(5,3)
  for (i <- 0 to 10) {
    println(fib(i))
    println(fibtail(i))
  }
  println(choose(10, 2))

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
  def isZero(a:Int) : Boolean = if (a==0) true else false

  def sub(a:Int,b:Int) : Int = {
    if (b==0) {
      a
    }
    else dec(subtail(a,dec(b)))
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

  def multail(a:Int, b:Int) : Int = {
    def help(res: Int, count: Int): Int = {
      if (count == b) res else help(addtail(res, b), count + 1)
    }

    help(a, 0)
  }

  def exp2(m:Int) : Int = {
    if (m==0) 1
    else mul(exp2(m-1), 2)
  }

  def exp2tail(m:Int) : Int = {
    def helper(res:Int, count:Int) : Int = {
      if (count==0) res
      else helper(multail(res, 2) , count-1)
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
      else helper(exp2tail(res), dec(count))
    }
    helper(1, n)
  }

  def expN(base : Int, exp : Int) : Int = {
    if (exp==0) 1
    else multail(expN(base, exp-1), base)
  }

  def ackermann (N: Int , m : Int) : Int = {
    if (m==0) 1
    else expN(N, ackermann(N, m-1))
  }

  def newton_sqrt(a:Double) : Double = {
    var count = 2;
    var guess : Double = 1
    println("guess 1 = "+guess)
    while (math.abs(math.pow(guess,2) - a) > 0.001) {
      guess = guess - (math.pow(guess, 2) - a) / (2*guess)
      count += 1
      printf("guess %d = %f\n", count, guess)
    }
    guess
  }

  def fib(n:Int) :Int = {
    if (n==0) 1 else if (n==1) 1 else fib(n-1) + fib(n - 2)
  }

  def fibtail(n:Int) : Int = {
    def help(res1:Int, res2:Int, count:Int) : Int = {
      if (count==1) res2 else help(res2, res1 + res2, count - 1)
    }
    if (n==0) 1 else if (n==1) 1 else help(1,1,n)
  }

  def choose(n:Int, m:Int) : Int = {
    if (m==0) 1 else if (n==0) 0 else choose(n-1, m) + choose(n-1,m-1)
  }

  def printCombination(n:Int, k:Int) = {
    val b = new Array[Int](n)
    for (i <- 1 to n) {
      b(i-1) = i
    }
    val a = new Array[Int](n)

    def printArray(a:Array[Int]) = {
      print("[")
      for (i <- 0 until a.length) {
        print(a(i))
        if (i!=a.length-1) print(", ")
        else print("]\n")
      }
    }
    def help(a : Array[Int], bound:Int, count:Int) : Unit = {
      if (count==k) printArray(a)
      else {
        for (j <- bound until b.length) {
          val ax = new Array[Int](k)
          for (k <- 0 to count){
            ax(k) = a(k)
          }
          ax(count) = b(j)
          help(ax, j + 1, count + 1)
        }
      }
    }
    help(a,0,0)
  }
}