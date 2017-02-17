/**
  * Created by drproduck on 2/11/17.
  */
object TaxCalculator {
  def tax(income: Double) = {
    if (income < 0) throw new Exception("Income can't be negative")
    else if (income < 20000) income
    else if (income < 30000) income * (0.05)
    else if (income < 40000) income * (0.11)
    else if (income < 60000) income * (0.23)
    else if (income < 100000) income * (0.32)
    else income * 0.5
  }

  def main(args: Array[String]): Unit = {
    var x = readInt()
    println(tax(x))
  }
}
