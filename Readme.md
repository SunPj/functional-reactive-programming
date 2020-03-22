### My simplified implementation of Signal and Var 

That's my simplified implementation of Signal and Var for `Coursera's Functional Program Design in Scala` course  

Traditional way to process events is Observable pattern but it's quite cumbersome and clatters the code, 
one of alternative is to use something like Signal and Var

See `Test.scala`
```scala
  val x = Var(10) // define a variable X
  val y = Var(5) // define a variable Y 
  val z = Var(7) // define a variable Z

  val sum = Var(x() + 10 + y()) // define SUM variable as function of x and y
  val sumX2 = Var(sum() * 2) // define SUM variable as function of sum
 
  println(sum()) // 25
  println(sumX2()) // 50

  x() = y() * 5 // change x to be function of y
  println(sum()) // 40
  println(sumX2()) // 80  
```

As you can see here `sum` and `sumX2` are recalculated automatically on changing `x` value.
 
To summon up, we can achieve the observe/subscribe functionality in declarative way.    

## Where is magic?

Let's examine following expression `x() = y() * 5`
1. As it was said in course scala treats `F(x1, x2...) = A` as `F.update(x1, x2, ..., A)` so we end up with `x.update(y() * 5)`
2. If we look at declaration of `update` function's param `def update(v: => A) = ...`, we see `v` is evaluated by name, so we end up with `x.update(() => y() * 5)`  
3. Having `v` params evaluated by name we can invoke that so called function to get exact value at any time even `y` changes 
4. How to circle back to change `x` every time `y` changed? 
    1. When we evaluate `y() * 5` expression we evaluate `y.apply() * 5` where `y.apply()` has a side effect as adding itself to global variable stack.
    2. We evict stack before evaluating `y.apply() * 5` (see update function), so `stack = []` 
    3. We evaluate expression `y.apply() * 5`, that gives us `stack = [y]` as side effect.
    4. We iterate through `stack` and subscribe to `vars`, so every time they updated we recalculate current value
    


