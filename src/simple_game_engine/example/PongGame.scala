package simple_game_engine.example


import java.awt.event.KeyEvent
import java.awt.{Graphics2D, Color}


object PongGame extends SimpleGameEngine {
  import GameActorEvent._
  import KeyEvent._
  class Paddle(val initX: Double)
          extends GameActor(new Sprite(10, 100) {
            def paint(x: Int, y: Int, g: Graphics2D) {
              g.setColor(Color.WHITE)
              g.fillRect(x, y, width, height)
            }
          }) {
    x = initX
    val displacement = 20.0
    reactions += {
      case PressedKey(VK_UP) =>
        if (y > 0)
          y -= displacement
      case PressedKey(VK_DOWN) =>
        if (y < 500)
          y += displacement
      case Step =>

    }
  }

  class Ball
          extends GameActor(new Sprite(10, 10) {
            def paint(x: Int, y: Int, g: Graphics2D) {
              g.setColor(Color.WHITE)
              g.fillOval(x.toInt, y.toInt, width, height)
            }
          }) {
    x = 400
    y = 300
    xSpeed = 5
    ySpeed = 1
    reactions += {
      case CollidesWith(paddle: Paddle) =>
        xSpeed *= -1
        ySpeed *= -1
    }
  }

  val gameActors = new Paddle(10) :: new Paddle(780) :: new Ball :: Nil

}