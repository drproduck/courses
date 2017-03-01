

/**
  * Created by drproduck on 2/26/17.
  */
object console {

  def execute(cmd: String): String = {
    val a = cmd.split("\\s+")
    if (a.length > 3) {
      throw new Exception("More operands than allowed")
    }
    try {
      a(1).toDouble
    } catch {
      case e: NumberFormatException => throw new Exception("Invalid argument: "+a(1))
    }
    try {
      a(2).toDouble
    } catch {
      case e: NumberFormatException => throw new Exception("Invalid argument: " + a(2))
    }
    val x = a(1).toDouble
    val y = a(2).toDouble
    a(0) match {
      case "add" => return (x + y).toString
      case "mul" => return (x * y).toString
      case "sub" => return (x - y).toString
      case "div" => return (x / y).toString
      case _ => throw new Exception("Unrecognized command: "+a(1))
      }
  }

  def repl {
    while (true) {
      print("-> ")
      val a = readLine()
      if (a.equals("quit")) {
        println("bye")
        return
      }
      else if (a.equals("help"))
        println("commands: add, mul, sub, div, quit, help")
      else {
        try {
          println(execute(a))
        }
        catch {
          case e: Exception => println(e.getMessage)
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    repl
  }
}
