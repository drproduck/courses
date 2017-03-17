/**
  * Created by drproduck on 3/16/17.
  */
object DDS {
    def controlLoop[S](state: S, cycle: Int, halt: (S, Int) => Boolean, update: (S,Int) => S): S = {
      if (halt(state, cycle)) state
      else controlLoop(update(state, cycle), cycle+1, halt, update)
    }

    def ha(a:Int, b:Int) : Boolean = {a > 100000}
    def up(a:Int, b:Int) : Int = a*2

    val delta = 1e-5
    def solve(f:Double=> Double):Double ={
      def df(f:Double=>Double): Double => Double = {
        def d(x:Double) = (f(x+delta) - f(x))/delta
        d _
      }
      def gud(guess:Double, cycle:Int) = math.abs(f(guess)) <= delta
      def improve(guess: Double, cycle:Int) = guess -f(guess) / df(f)(guess)
      controlLoop[Double](1, 0, gud, improve)
    }

    def sqrt(x: Double) = {
      def f(z:Double) = z*z - x
      solve(f)
    }

  def main(args: Array[String]): Unit = {
    println(controlLoop(1,0,ha _,up _))

    println(sqrt(64))
  }

}
