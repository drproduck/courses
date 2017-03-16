import scala.collection.mutable.HashMap

  object processor {

    var currentEnv = new Environment
    var program: Array[String] = null
    var ip = 0
    var ir:Array[String] = null
    val labels = new HashMap[String, Int]

    def preProcess(fileName: String) {
      program = io.Source.fromFile(fileName).getLines.toArray
      for(i <- 0 until program.length) {
        if (!program(i).isEmpty()) {
          ir = program(i).split("\\s+")
          if (ir(0) == "label:") labels.put(ir(1), i)
        }
      }
    }

    // useful for converting variables or literals to ints:
    def get(term: String): Int = if (term.matches("\\d+")) term.toInt else currentEnv(term).content


    def fetchExecute() {
      var halt = false
      var cip = 0
      while(!halt) {
        try {
          // load ir with opcode and operands of program(ip) (hint: use split)
          if (ip < program.length) {
            ir = program(ip).split("\\s+")
            cip = ip
            // increment ip
            ip += 1
            // skip empty instructions?
            while (ip < program.length && program(ip).isEmpty)
              ip += 1
          }
          // based on opcode, execute the instruction (hint: use opcode match ...)
          // if opcode = "halt" then halt = true
          ir(0) match {
            case "comment" =>
            case "def" => currentEnv.put(ir(1), new Variable(ir(2).toInt))
            case "load" => {
              if (ir.length > 3) throw new Exception("load supports 3 operands")
              val variable = currentEnv(ir(1))
              variable.content = get(ir(2))
            }
            case "add" => currentEnv.put(ir(1), new Variable(get(ir(2)) + get(ir(3))))
            case "mul" => currentEnv.put(ir(1), new Variable(get(ir(2)) * get(ir(3))))
            case "sub" => currentEnv.put(ir(1), new Variable(get(ir(2)) - get(ir(3))))
            case "div" => currentEnv.put(ir(1), new Variable(get(ir(2)) / get(ir(3))))
            case "and" => currentEnv.put(ir(1), new Variable(get(ir(2)) & get(ir(3))))
            case "or" => currentEnv.put(ir(1), new Variable(get(ir(2)) | get(ir(3))))
            case "equal" => currentEnv.put(ir(1), new Variable(if (get(ir(2)) == get(ir(3))) 1 else 0))
            case "not" => currentEnv.put(ir(1), new Variable(if(get(ir(2)) == 1) 0 else 1))
            case "goto" => ip = labels(ir(1))
            case "if" => if (currentEnv(ir(1)).content == 1) ip = labels(ir(2))
            case "halt" => halt = true
            case "printmsg" => println(program(cip).substring(9, program(cip).length))
            case "read" => readLine()
            case "print" => println(currentEnv(ir(1)).content)
          }
        }
      }
      println("bye ... ")
    }
    def main(args: Array[String]): Unit = {
      val programFile = readLine("Enter program name: ")
      preProcess(programFile)
      fetchExecute
    }
}
