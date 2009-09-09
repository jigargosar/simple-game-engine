package simple_game_engine


import actors.{TIMEOUT, Actor}
import java.awt.event.KeyEvent
import java.awt.image.ImageObserver
import java.awt.{Color, Graphics2D, Rectangle}
import scala.actors.Actor._
import swing.Publisher

trait GameLoopComponent {
  self: GameUIComponent =>

  val gameLoop: GameLoop

  object GameLoop {
    case class Paint(g: Graphics2D, imageObserver: ImageObserver)
    case class Start(gameActors: List[GameActor])
    case object Step
    case class KeyPressed(e: KeyEvent)
    case class KeyReleased(e: KeyEvent)
  }

  class GameLoop extends Actor with Publisher {
    val bounds = new Rectangle(0, 0, 800, 600)
    private var gameActors: List[GameActor] = Nil
    private var pressedKeys = Set.empty[Int]
    private var typedKeys = Set.empty[Int]

    def start(gameActors: List[GameActor]) {
      this.gameActors = gameActors
      this.gameActors.foreach(_.listenTo(this))
      actor {
        loop {
          Actor.reactWithin(30) {
            case TIMEOUT =>
              GameLoop.this ! GameLoop.Step
          }
        }
      }
      super.start
    }

    def act() {
      loop {
        react {
          case GameLoop.KeyPressed(e: KeyEvent) =>
            typedKeys += e.getKeyCode
            pressedKeys += e.getKeyCode
          case GameLoop.KeyReleased(e: KeyEvent) =>
            pressedKeys -= e.getKeyCode
          case GameLoop.Step =>
            for (typedKey <- typedKeys) {
              publish(GameActorEvent.KeyTyped(typedKey))
            }
            typedKeys = Set.empty[Int]

            for (pressedKey <- pressedKeys) {
              publish(GameActorEvent.PressedKey(pressedKey))
            }

            publish(GameActorEvent.UpdatePositions)
            publish(GameActorEvent.Step)

            def publishCollisions(gameActors: List[GameActor]) {
              gameActors match {
                case head :: tail =>
                  for (next <- tail; if head.doesCollideWith(next)) {
                    head.reactions(GameActorEvent.CollidesWith(next))
                    next.reactions(GameActorEvent.CollidesWith(head))
                  }
                  publishCollisions(tail)
                case Nil =>
              }
            }
            publishCollisions(gameActors)

            gameUI.repaint
          case GameLoop.Paint(g, imageObserver) =>
            paint(g, imageObserver)
            reply("done")
        }
      }
    }

    private def paint(g: Graphics2D, imageObserver: ImageObserver) {
      g.setColor(Color.BLACK)
      g.fill(bounds)
      publish(GameActorEvent.Paint(g))
    }
  }
}