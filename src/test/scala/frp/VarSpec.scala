package frp

import org.scalatest._

class VarSpec extends FlatSpec with Matchers {
  val x = Var(10)
  val y = Var(5)
  val z = Var(7)

  val sum = Var(x() + 10 + y())
  val sumX2 = Var(sum() * 2)

  "Sum" should "be 25" in {
    sum() === 25
  }

  "sumX2" should "be 50" in {
    sumX2() === 50
  }

  "sum and sumX2" should "be recalculated implicitly on changing X" in {
    x() = y() * 5
    sum() === 40
    sumX2() === 80
  }

  "sumX2" should "be recalculated implicitly on changing sum" in {
    sum() = x() * y()
    sum() === 125
    sumX2() === 250
  }
}
