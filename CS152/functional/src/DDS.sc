def id[T](x: T) = x

def compose[T, A, B](f: A => T, g: B => A): B => T = {
  def r(x: B): T = f(g(x));
  r _
}

def len(s: String) = s.length
def isEven(x: Int) = x % 2 == 0
compose(isEven, len)("abcc")
compose(isEven, len)("abc")

def selfIter[T](f: T => T, n: Int): T => T = {
  def helper(g: T => T, n: Int): T => T = {
    if (n == 0) id _
    else if (n > 1) helper(compose(f, g), n - 1)
    else g
  }

  helper(f, n)
}
def selfIter2[T](f: T => T, n: Int): T => T = {
  def r(x: T) = {
    var res = x
    for (i <- 1 to n) res = f(res)
    res
  }

  r _
}

def inc(a: Int) = a + 1
def double(a: Int) = a*2
selfIter(inc, 5)(3)
selfIter2(inc, 5)(3)
selfIter(inc, 0)(3)
selfIter(double, 3)(4)
selfIter2(double, 3)(4)

def countPass[T](array: Array[T],f: T => Boolean): Int = {
  var count = 0
  for (i <- 0 until array.length){
    if (f(array(i)))
      count += 1
  }
  count
}
import scala.math._
countPass(Array(2,3,4,5,6,7,8), (x: Int) => x%2==0)
def makeRay(x:Int): Array[Double] = {
  val a = new Array[Double](x)
  for (i <- 0 until x) a(i) = random
  a
}

countPass(makeRay(22222222), (x:Double) => x>0.5)

def controlLoop[S](state: S, cycle: Int, halt: (S, Int) => Boolean, update: (S, Int) => S): S = {
  if (halt(state, cycle)) state
  else controlLoop(update(state, cycle), cycle + 1, halt, update)
}

def growth(i: Int): Int = {
  def ha(a: Int, b: Int): Boolean = a > 100000

  def up(a: Int, b: Int): Int = a * 2

  controlLoop(i, 0, ha, up)
}
growth(1)
growth(10000)

val delta = 1e-5
def solve(f: Double => Double): Double = {
  def df(f: Double => Double): Double => Double = {
    def d(x: Double) = (f(x + delta) - f(x)) / delta

    d _
  }

  def gud(guess: Double, cycle: Int) = math.abs(f(guess)) <= delta

  def improve(guess: Double, cycle: Int) = guess - f(guess) / df(f)(guess)

  controlLoop[Double](1, 0, gud, improve)
}

val sqrt = (x: Double) => {
  def f(z: Double) = z * z - x

  solve(f)
}
sqrt(64)

def cubeRoot(x: Double) = {
  def f(z: Double) = z * z * z - x

  solve(f)
}
cubeRoot(1000)

def nthRoot(x: Double, n:Int) = {
  def power(x: Double, n:Int): Double = {
    if (n==0) 1
    else if (n==1) x
    else {
      val v = power(x, n/2)
      if (n%2==1) v * v * x
      else v * v
    }
  }
  def f(z:Double) = power(z, n) - x

  solve(f)
}
nthRoot(100000, 5)
nthRoot(228886641, 4)

