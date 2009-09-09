package understanding_scala

trait FooBar {
  println("In FooBar")
  //  val foo: String = "foo"
  //  val bar: String = "bar"
  val foo: String
  val bar: String
}

trait FooComponent {
  self: FooBar =>
  println("In FooComponent")
  println(bar)
}

trait BarComponent {
  self: FooBar =>
  println("In BarComponent")
  println(foo)
}



object Main extends FooBar with FooComponent with BarComponent {
  println("In Main")
  lazy val foo = "foo"
  lazy val bar = "bar"

  def main(args: Array[String]) {}
}