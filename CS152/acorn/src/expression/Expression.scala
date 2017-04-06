package expression

/**
  * Created by drproduck on 4/6/17.
  */
import value._

trait Expression {
  def execute(): Value
}
