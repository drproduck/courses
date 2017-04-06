package expression

import value._

/**
  * Created by drproduck on 4/6/17.
  */
class Sum(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute() = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (!value1.isInstanceOf[Number] || !value2.isInstanceOf[Number])
      throw new Exception("type mismatch: only numbers can be added")
    else {
      val num1 = value1.asInstanceOf[Number]
      val num2 = value2.asInstanceOf[Number]
      num1 + num2
    }

  }

  override def toString = "(" + operand1.toString + " + " + operand2.toString + ")"
}

object Sum {
  def apply(operand1: Expression, operand2: Expression) =
    new Sum(operand1, operand2)
}
