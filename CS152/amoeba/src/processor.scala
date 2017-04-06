import scala.collection.mutable
import scala.collection.mutable.HashMap

object processor {

  var program: Array[String] = null
  var ir: Array[String] = null
  val labels = new HashMap[String, Int]

  def preProcess(fileName: String) {
    program = io.Source.fromFile(fileName).getLines.toArray
    for (i <- 0 until program.length) {
      if (!program(i).isEmpty()) {
        ir = program(i).split("\\s+")
        if (ir(0) == "label:") labels.put(ir(1), i)
      }
    }
  }

  val mainEnv = new Environment

  def fetchExecute {
    def procedure(env: Environment, ip: Int) {
      def get(term: String): Int = if (term.matches("\\d+")) term.toInt else env(term).content

      var halt = false
      var ret = false;
      var cip = ip
      while (!halt && !ret) {
        try {
          ir = program(cip).split("\\s+")
          ir(0) match {
            case "" =>
            case "comment:" =>
            case "label:" =>
            case "def" => env.put(ir(1), new Variable(ir(2).toInt))
            case "load" => {
              val variable = env(ir(1))
              if (variable != null) variable.content = get(ir(2))
            }
            case "add" => env(ir(1)).content = (get(ir(2)) + get(ir(3)))
            case "mul" => env(ir(1)).content = get(ir(2)) * get(ir(3))
            case "sub" => env(ir(1)).content = (get(ir(2)) - get(ir(3)))
            case "div" => env(ir(1)).content = (get(ir(2)) / get(ir(3)))
            case "and" => env(ir(1)).content = if (get(ir(2)) == 0 || get(ir(3)) == 0) 0 else 1
            case "or" => env(ir(1)).content = if (get(ir(2)) == 0 && get(ir(3)) == 0) 0 else 1
            case "equal" => env(ir(1)).content = if (get(ir(2)) == get(ir(3))) 1 else 0
            case "not" => env(ir(1)).content = if (get(ir(2)) == 0) 1 else 0
            case "goto" => cip = labels(ir(1))
            case "if" => if (env(ir(1)).content != 0) cip = labels(ir(2))
            case "halt" => halt = true
            case "printmsg" => println(program(cip).substring(9, program(cip).length))
            case "read" => env.put(ir(1), new Variable(readLine().toInt))
            case "print" => println(env(ir(1)).content)
            case "call" => {
              val subEnv = new Environment(env)
              for (i <- 2 until ir.length) {
                subEnv.put("arg" + (i - 2), new Variable(get(ir(i)).toInt))
              }
              procedure(subEnv, labels(ir(1)))
              env("return").content = subEnv("return").content
            }
            case "return" => {
              ret = true
              env("return").content = env(ir(1)).content
            }
            case _ => throw new Exception("unrecognized opcode: " + ir(0))
          }
        }
        catch {
          case e: Exception => println(e); println(e.printStackTrace())
        }
        cip = cip + 1
      }
    }

    procedure(mainEnv, 0)
    println("bye ... ")
  }

  def main(args: Array[String]): Unit = {
    val programFile = readLine("Enter program name: ")
    preProcess(programFile)
    fetchExecute
  }
}

class Variable(value : Int) {
  var content: Int = value.toInt
  def main(args: Array[String]): Unit = {
    val v = new Variable(5)
    println(v.content)
  }
}

class Environment(var extension: Environment = null) extends mutable.HashMap[String, Variable] {
  put("return", new Variable(0))
  override def apply(name:String) : Variable = {
    if (contains(name)) super.apply(name)
    else if (extension != null) extension(name)
    else throw new Exception("undefined variable: "+name)
  }
}
