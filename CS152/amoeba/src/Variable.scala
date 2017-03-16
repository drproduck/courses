/**
  * Created by drproduck on 3/7/17.
  */
class Variable(value : Int) {
    var content: Int = value.toInt
    def main(args: Array[String]): Unit = {
        val v = new Variable(5)
        println(v.content)
    }
}
