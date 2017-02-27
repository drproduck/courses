/**
  * Created by drproduck on 2/26/17.
  */
import scala.math._
object stringSession {
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
    println(ss)
    isPal(ss)
  }
  def main(args: Array[String]): Unit = {
    println(isPal("   sdus uds  "))
    println(isPal2("adf@!fda"))
    println(mkPal("mars"))
    for(i <- 0 until 100)
    println(mkWord())
    for (i <- 0 until 100)
    println(mkSentence())
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
}