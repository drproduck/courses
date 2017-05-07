package value

/**
  * Created by drproduck on 4/6/17.
  */
trait Value {
}

import expression.Literal

class Number(val value: Double) extends Literal {
  def +(other: Number) = Number(this.value + other.value)

  def -(other: Number) = Number(this.value - other.value)

  def *(other: Number) = Number(this.value * other.value)

  def /(other: Number) = Number(this.value / other.value)

  def <(other: Number) = Boole(this.value < other.value)

  def ==(other: Number) = Boole(this.value == other.value)

  override def toString = value.toString
}

object Number {
  def apply(value: Double) = new Number(value)
}

class Boole(val value: Boolean) extends Literal {
  def &(other: Boole) = Boole(this.value && other.value)

  def ||(other: Boole) = Boole(this.value || other.value)

  override def toString = value.toString
}

object Boole {
  val TRUE = Boole(true)
  val FALSE = Boole(false)
  def apply(value: Boolean): Boole = new Boole(value)
}

