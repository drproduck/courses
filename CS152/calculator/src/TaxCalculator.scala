/**
  * Created by drproduck on 2/11/17.
  */
object TaxCalculator {
  def tax(income: Double): Double = {
    if (income < 0) {
      throw new Exception("Income can't be negative")
    } else if (income < 20000) {
      return income
    } else if (income < 30000) {
      return income * (0.05)
    } else if (income < 40000) {
      return income * (0.11)
    } else if (income < 60000) {
      return income * (0.23)
    } else if (income < 100000) {
      return income * (0.32)
    } else return income * 0.5
  }

  def main(args: Array[String]): Unit = {
    var x = readInt()
    println(tax(x))
  }
}
