package system

/**
  * Created by drproduck on 4/6/17.
  */
import expression._, value._

object AcornTest extends App {

  def execute(exp: Expression) {
    try {
      print(exp.toString + " = ")
      val value = exp.execute
      println(value.toString)
    } catch {
      case e: Exception => println(e)
    }
  }

  execute(Sum(Number(3.1), Number(4.2)))
  execute(And(Boole(true), Boole.TRUE))
  execute(And(Boole.FALSE, Number(3.1)))
  execute(And(Number(3.1), Boole.FALSE))
  execute(Sum(Number(3.1), Boole.FALSE))
  execute(Sum(Sum(Number(3.1), Number(4.2)), Sum(Number(3.5), Number(2.8))))
  execute(Equal(Sum(Number(1), Number(3)), Sum(Number(1), Number(2))));
  execute(Equal(Boole.FALSE, Boole.TRUE))
  execute(Not(Boole.FALSE))
  execute(And(Less(Number(5), Times(Number(2), Number(2))), Not(Number(6)))) // short-circuit
}
