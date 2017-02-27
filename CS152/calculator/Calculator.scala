/**
  * Created by drproduck on 2/11/17.
  */

import scala.math;
object Calculator {

  // = 1 * 2 * 3 * ... * n
  def fact(n: Int) : Int = {
    if (n<0)
      throw new Exception("integer must be at least 0")
    var prod : Int = 1;
    var nn : Int = n;
    while (nn>0) {
      prod *= nn;
      nn-=1;
    }
    prod
  }

  // = 1 + 2 + 3 + ... + n
  def tri(n: Integer) : Int = {
    var sum : Int = 0;
    var nn : Int = n;
    while (nn>0) {
      sum += nn;
      nn-=1;
    }
    return sum

  }

  // = 2^n
  def exp(n: Integer) : Int = {
    var res : Int = 1;
    var m : Int = n;
    while(m>0) {
      res *= 2;
      m-=1;
    }
    return res;
  }

  // = true if n >= 2 and has no smaller divisors
  def isPrime(n: Integer) : Boolean = {
    if (n<=1) {
      return false;
    }
    var m : Int = n;
    val bound = math.sqrt(m);
    var l = 2;
    while(l <= bound) {
      if (m%l == 0){
        return false;
      }
      l+=1;
    }
    return true;
  }

  def main(args: Array[String]): Unit = {
    println("enter 3 integers x, y, and z on separate lines: ")
    var x = readInt()
    var y = readInt()
    var z = readInt()
    println("fact(x) = " + fact(x))
    println("fact(y) = " + fact(y))
    println("fact(z) = " + fact(z))
    println("tri(x) = " + tri(x))
    println("tri(y) = " + tri(y))
    println("tri(z) = " + tri(z))
    println("exp(x) = " + exp(x))
    println("exp(y) = " + exp(y))
    println("exp(z) = " + exp(z))
    println("isPrime(x) = " + isPrime(x))
    println("isPrime(y) = " + isPrime(y))
    println("isPrime(z) = " + isPrime(z))
  }

}
