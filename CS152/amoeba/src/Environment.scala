import scala.collection.mutable

/**
  * Created by drproduck on 3/7/17.
  */
class Environment(var extension: Environment = null) extends mutable.HashMap[String, Variable] {
  put("return", new Variable(0))
  override def apply(name:String) : Variable = {
    if (contains(name)) super.apply(name)
    else if (extension != null) extension(name)
    else throw new Exception("undefined variable: "+name)
  }
}

