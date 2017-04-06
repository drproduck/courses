object worksheet {

  def conjunct(elems: Array[Boolean]): Boolean = {
    def helper(index: Int, res: Boolean): Boolean = {
      if (!res) res
      else if (index == elems.length) true
      else helper(index + 1, res && elems(index))
    }

    helper(0, true)
  }

  conjunct(Array(true, true, false, {
    println("see me once?");
    true
  }))

  def blender[A, B, C, D](f: (A, B) => C, g: D => A, h: D => B): D => C = {
    def k(x: D): C = f(g(x), h(x))

    k _
  }

  val encode = blender((a: Int, b: Int) => a + b, (c: String) => 2 * c.length, (d: String) => 3 * d.toInt)
  encode("100")

  def scopeRule {
    val x = 5

    def help: Int = {
      x + 5
    }

    def me: Int = {
      val x = 3
      help
    }

    if (me == 10) println("static")
    else if (me == 8) println("dynamic")
  }

  scopeRule
}