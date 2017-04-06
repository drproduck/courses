package value

/**
  * Created by drproduck on 4/6/17.
  */

import expression.Literal

class Number(val value: Double) extends Literal {
  def +(other: Number) = Number(this.value + other.value)

  def -(other: Number) = Number(this.value - other.value)

  def /(other: Number) = Number(this.value / other.value)

  def <(other: Number) = Boole(this.value < other.value)

  def ==(other: Number) = Boole(this.value == other.value)

  override def toString = value.toString
}

object Number {
  def apply(value: Double) = new Number(value)
}
