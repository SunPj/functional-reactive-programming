package frp

object Test extends App {
  val x = Var(10)
  val y = Var(5)
  val z = Var(7)

  val sum = Var(x() + 10 + y())
  val sumX2 = Var(sum() * 2)

  println(sum())
  println(sumX2())

  x() = y() * 5
  println(sum())
  println(sumX2())

  sum() = x() * y()
  println(sum())
  println(sumX2())
}
