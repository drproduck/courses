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
      while(!halt) {
        try {
          // load ir with opcode and operands of program(ip) (hint: use split)
          // increment ip
          // skip empty instructions?
          // based on opcode, execute the instruction (hint: use opcode match ...)
          // if opcode = "halt" then halt = true
        }
        println("bye ... ")
      }

      def main(args: Array[String]): Unit = {
        val programFile = readLine("Enter program name: ")
        preProcess(programFile)
        fetchExecute
      }

    }

}
