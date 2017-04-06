package value

/**
  * Created by drproduck on 4/6/17.
  */
import expression.Literal

class Boole(value:Boolean) extends Literal{

}

object Boole{
  def apply(value:Boolean): Boole = new Boole(value)
}
