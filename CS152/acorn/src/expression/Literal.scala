package expression

/**
  * Created by drproduck on 4/6/17.
  */
import value.Value

class Literal extends Expression with Value {
  def execute() = this
}
