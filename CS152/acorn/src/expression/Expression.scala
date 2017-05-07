package expression

/**
  * Created by drproduck on 4/6/17.
  */

import value._

trait Expression {
  def execute(): Value
}

class Literal extends Expression with Value {
  def execute() = this
}

class And(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute = {
    val value1 = operand1.execute
    if (!value1.isInstanceOf[Boole]) throw new Exception("type mismatch: must be of type boolean")
    else {
      val bol1 = value1.asInstanceOf[Boole]
      if (!bol1.value) Boole(false)
      else {
        val value2 = operand2.execute
        if (!value2.isInstanceOf[Boole]) throw new Exception("type mismatch: must be of type boolean")
        else {
          val bol2 = value2.asInstanceOf[Boole]
          bol1 & bol2
        }
      }
    }
  }

  override def toString = "(" + operand1.toString + " && " + operand2.toString + ")"
}

object And {
  def apply(operand1: Expression, operand2: Expression) =
    new And(operand1, operand2)
}

class Divide(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute() = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (!value1.isInstanceOf[Number] || !value2.isInstanceOf[Number])
      throw new Exception("type mismatch: only numbers can be divided")
    else {
      val num1 = value1.asInstanceOf[Number]
      val num2 = value2.asInstanceOf[Number]
      num1 / num2
    }

  }

  override def toString = "(" + operand1.toString + " / " + operand2.toString + ")"
}

object Divide {
  def apply(operand1: Expression, operand2: Expression) =
    new Divide(operand1, operand2)
}

class Equal(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (value1.isInstanceOf[Number] && value2.isInstanceOf[Number]) {
      val num1 = value1.asInstanceOf[Number]
      val num2 = value2.asInstanceOf[Number]
      Boole(num1.value == num2.value)
    }
    else if (value1.isInstanceOf[Boole] && value2.isInstanceOf[Boole]) {
      val bol1 = value1.asInstanceOf[Boole]
      val bol2 = value2.asInstanceOf[Boole]
      Boole(bol1.value == bol2.value)
    } else {
      throw new Exception("type mismatch")
    }
  }

  override def toString = "(" + operand1.toString + " = " + operand2.toString + ")"
}

object Equal {
  def apply(operand1: Expression, operand2: Expression) =
    new Equal(operand1, operand2)
}

class Less(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (!value1.isInstanceOf[Number] || !value2.isInstanceOf[Number])
      throw new Exception("type mismatch: only numbers can be compared")
    else {
      val num1 = value1.asInstanceOf[Number]
      val num2 = value2.asInstanceOf[Number]
      num1 < num2
    }

  }

  override def toString = "(" + operand1.toString + " < " + operand2.toString + ")"
}

object Less {
  def apply(operand1: Expression, operand2: Expression) =
    new Less(operand1, operand2)
}

class Not(val operand1: Expression) extends Expression {

  def execute = {
    val value = operand1.execute
    if (!value.isInstanceOf[Boole]) throw new Exception("type mismatch: must be of type boolean")
    else {
      val bol = value.asInstanceOf[Boole]
      Boole(!bol.value)
    }
  }

  override def toString = "(~" + operand1.toString + ")"
}

object Not {
  def apply(operand: Expression) =
    new Not(operand)
}

class Or(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (!value1.isInstanceOf[Boole]) throw new Exception("type mismatch: must be of type boolean")
    else {
      val bol1 = value1.asInstanceOf[Boole]
      if (bol1.value) Boole(true)
      else {
        if (!value2.isInstanceOf[Boole]) throw new Exception("type mismatch: must be of type boolean")
        else {
          val bol2 = value2.asInstanceOf[Boole]
          bol1 || bol2
        }
      }
    }
  }

  override def toString = "(" + operand1.toString + " || " + operand2.toString + ")"
}

object Or {
  def apply(operand1: Expression, operand2: Expression) =
    new Or(operand1, operand2)
}

class Subtract(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute() = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (!value1.isInstanceOf[Number] || !value2.isInstanceOf[Number])
      throw new Exception("type mismatch: only numbers can be subtracted")
    else {
      val num1 = value1.asInstanceOf[Number]
      val num2 = value2.asInstanceOf[Number]
      num1 - num2
    }

  }

  override def toString = "(" + operand1.toString + " - " + operand2.toString + ")"
}

object Subtract {
  def apply(operand1: Expression, operand2: Expression) =
    new Less(operand1, operand2)
}

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

class Times(val operand1: Expression, val operand2: Expression) extends Expression {

  def execute() = {
    val value1 = operand1.execute
    val value2 = operand2.execute
    if (!value1.isInstanceOf[Number] || !value2.isInstanceOf[Number])
      throw new Exception("type mismatch: only numbers can be multiplied")
    else {
      val num1 = value1.asInstanceOf[Number]
      val num2 = value2.asInstanceOf[Number]
      num1 * num2
    }

  }

  override def toString = "(" + operand1.toString + " * " + operand2.toString + ")"
}

object Times {
  def apply(operand1: Expression, operand2: Expression) =
    new Times(operand1, operand2)
}