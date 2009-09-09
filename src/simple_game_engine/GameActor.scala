package simple_game_engine


import java.awt.{Rectangle, Graphics2D}
import swing.event.Event
import swing.{Reactor}

object GameActorEvent {
  case class PressedKey(keyCode: Int) extends Event
  case class KeyTyped(keyCode: Int) extends Event
  case object UpdatePositions extends Event
  case object Step extends Event
  case class CollidesWith(ga: GameActor) extends Event
  case object OutsideScreen extends Event
  case class Paint(g: Graphics2D) extends Event
}


class GameActor(val sprite: Sprite) extends Reactor {
  def this() = this (EmptySprite)

  private val _random = new scala.util.Random
  private var _x = 0.0

  protected def x = _x

  protected def x_=(value: Double) = {
    xPrevious = _x
    _x = value
  }

  private var _y = 0.0

  protected def y = _y

  protected def y_=(value: Double) = {
    yPrevious = _y
    _y = value
  }

  protected var xPrevious = x
  protected var yPrevious = y
  protected var xSpeed = 0.0
  protected var ySpeed = 0.0
  //  protected var speed = 0.0
  //  protected var direction = 0.0

  def random(double: Double) = _random.nextDouble * double

  def boundingBox = new Rectangle(x.toInt, y.toInt, sprite.width, sprite.height)

  def doesCollideWith(that: GameActor) = this.boundingBox.intersects(that.boundingBox)

  import GameActorEvent._
  reactions += {
    case UpdatePositions =>
      x += xSpeed
      y += ySpeed
    case Paint(g) =>
      sprite.paint(x.toInt, y.toInt, g)

  }

}