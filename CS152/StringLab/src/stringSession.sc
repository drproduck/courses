import scala.math._
object string {
  def isPal(s:String) : Boolean = {
    val ss = s.trim
    val b = ss.length / 2 - 1
    for (i <- 0 to b) {
      if (!ss.charAt(i).equals(ss.charAt(ss.length-1-i)))
        return false
    }
    true
  }
  def isPal2(s:String) : Boolean = {
    val ss = s.replaceAll("[^A-z]+", "").toLowerCase()
    isPal(ss)
  }

  def mkPal(s:String) : String = {
    val ss = s.reverse
    s+ss
  }
  def mkWord(l : Int = 5) : String = {
    val sb = new StringBuilder(l)
    for (i <- 0 until l)
      sb.append(((26*random).toInt+97).toChar)
    return sb.toString
  }

  def mkSentence(l : Int  = 5): String = {
    if (l==0) {
      return ""
    }
    val sb = new StringBuilder()
    sb.append(((26*random).toInt+65).toChar)
    for (i <- 0 until l) {
      sb.append(mkWord((1 + 10*random).toInt))
      if (i<l-1)
        sb.append(" ")
    }
    sb.append(".")
    sb.toString()
  }

  def build_string(l:Int = 10) :String = {
    val s = new StringBuilder(l)
    for (i <- 1 to l)
      s.append((32+94*random).toInt.toChar)
    s.toString
  }

  val s1 = build_string()
  val s2 = build_string()

  println("first string: "+s1)
  println(isPal(s1))
  println(isPal2(s1))
  println(mkPal(s1))

  println("second string: "+s2)
  println(isPal(s2))
  println(isPal2(s2))
  println(mkPal(s2))

  println(isPal("   sdus uds  "))
  println(isPal2("adf@!fda"))
  println(mkPal("mars"))

  for(i <- 0 until 5)
    println(mkWord())
  for (i <- 0 until 5)
    println(mkSentence())
}