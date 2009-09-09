package simple_game_engine.example


import java.awt.event.KeyEvent._
import java.awt.{Color}
import GameActorEvent._

object AsteroidGame extends SimpleGameEngine {
  val gameActors = new GameActor {
    xSpeed = random(10.0)
    ySpeed = random(10.0)
    reactions += {
      case Step =>
      case Paint(g) =>
        g.setColor(Color.YELLOW)
        g.drawRect(x.toInt, y.toInt, 10, 10)
      case KeyTyped(VK_UP) =>
        xSpeed *= -1
    }

  } :: Nil
}