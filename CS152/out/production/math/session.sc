/**
  * Created by drproduck on 2/21/17.
  */
import scala.math._
object worksheet {
  println("Welcom to the Scala worksheet")
  def solve (a : Double, b : Double, c : Double): Option[(Double, Double)] ={
    val delta : Double = b*b - 4*a*c
    if (delta < 0) {
      None
    } else if (delta == 0) {
      Some(-b / (2 * a), -b / (2 * a))
    } else Some((-b + math.sqrt(delta)) / (2 * a), (-b - math.sqrt(delta)) / (2 * a))
  }

  def dist( a: (Double, Double), b: (Double, Double)): Double ={ //type of dist is Double
    sqrt(pow((a._1 - b._1),2) + pow((a._2 - b._2), 2))
  }

  def dot(a:(Double, Double, Double), b:(Double,Double,Double)):Double={
    a._1*b._1 + a._2*b._2 + a._3*b._3
  }

  def force(m1 : Double, m2:Double, d:Double) : Double ={
    val G:Double = 6.67* pow(10, -11)
    m1*m2*G/ pow(d, 2)
  }
  //convert miles to meter and solve using the same formula above
  def isPrime(a:Integer) : Boolean={
    if (a<=1) {
      return false
    }
    val root: Int = sqrt(a.toDouble).toInt
    for (i <- 2 to root) {
      if (a%i==0){
        return false
      }
    }
    return true
  }

  def phi(a:Int) : Option[Int] = {
    if (a<=0) {
      None
    }
    var res:Int = a
    for(i <- 2 to a) {
      if (isPrime(i)&&a%i==0) {
        println(i)
        res = (res / i) * (i - 1)
      }
    }
    Some(res)
  }

  def rollDice : (Int, Int) = {
    (1+(6*random()).toInt, 1+(6*random()).toInt)
  }

  println(solve(1, 0, 1))
  println(solve(1, 2, 1))
  println(solve(1, -5, 6))
  println(solve(1, 4, 2))

  println(isPrime(2))
  println(isPrime(3))
  println(isPrime(5))
  println(isPrime(12))

  println(dist((0,3), (4,0)))
  println(dist((-1, 2000), (3, 4000)))
  println(dist((500, 345), (0.004, 7)))

  println(dot((1,2,3), (4,5,6)))
  println(dot((9,8,7), (0.1,0.2,0.4)))
  println(dot((2,5,8), (3,6,9)))

  println(force(3,4,5))
  println(force(6,7,8))
  println(force(0.1,5000,123456))

  println(phi(12))
  println(phi(7))
  println(phi(20))

  for(i <- 0 until 10)
  println(rollDice)

}