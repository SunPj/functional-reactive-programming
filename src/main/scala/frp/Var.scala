package frp

class Var[A](v: => A) {
  import Var._

  private var observers = Set[Var[_]]()
  private var calculateFunction: () => A = _
  private var value: A = _

  update(v)

  def apply(): A = {
    stack = this :: stack
    value
  }

  private def observe(o: Var[_]): Unit = {
    observers += o
  }

  private def recalculate(): Unit = {
    value = calculateFunction()
    observers.foreach(_.recalculate())
  }

  def update(v: => A) = {
    stack = stack.empty
    calculateFunction = () => v
    value = calculateFunction()
    stack.foreach(_.observe(this))
    observers.foreach(_.recalculate())
  }
}

object Var {
  private var stack = List[Var[_]]()
  def apply[A](v: => A): Var[A] = new Var(v)
}
