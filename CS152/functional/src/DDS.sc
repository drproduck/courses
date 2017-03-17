object dds {
  println("Welcome")
  def controlLoop[S](state: S, cycle: Int, halt: (S, Int) => Boolean, update: (S,Int) => S): S = {
    if (halt(state, cycle)) state
    else controlLoop(update(state, cycle), cycle+1, halt, update)
  }

  def ha(a:Int, b:Int) : Boolean = a > 1e5
  def up(a:Int, b:Int) : Int = math.pow(a,2).toInt
  controlLoop(1, 0, ha _, up _)

  val delta = 1e-5
  def solve(f:Double=> Double):Double ={
    def df(f:Double=>Double): Double => Double = {
      def d(x:Double) = (f(x+delta) - f(x))/delta
      d _
    }
    def gud(guess:Double, cycle:Int) = math.abs(f(guess)) <= delta
    def improve(guess: Double, cycle:Int) = guess -f(guess) / df(f)(guess)
    controlLoop(1, 0, gud _, improve _)
  }

  def sqrt(x: Double) = {
    def f(z:Double) = z*z - x
    solve(f)
  }

  sqrt(64)
}