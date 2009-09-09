package archived_prototype_game_engine_for_ref


import actors.{TIMEOUT, Actor}
import actors.Actor._
import java.awt.event.KeyEvent
import java.awt.image.ImageObserver
import java.awt.{Font, Graphics2D, Color, Rectangle}
import scala.collection.mutable
import mutable.HashSet

class GameRoom(val gas: List[GameActor], repainter: {def repaint()})
        extends UIEventHandler with Actor {
  val bounds = new Rectangle(0, 0, 800, 600)
  var gameOver = false
  val gameActors = new mutable.Queue[GameActor]
  val pressedKeys = new HashSet[Int]()

  val adhocGameLoopActions = new mutable.Queue[AdhocGameLoopAction]

  gas.foreach(this ! Add(_))
  start
  actor {
    loop {
      Actor.reactWithin(20) {
        case TIMEOUT =>
          GameRoom.this ! "ALARM"
      }
    }
  }



  case class PaintRequest(g: Graphics2D, imageObserver: ImageObserver)
  case class KeyPressed(e: KeyEvent)
  case class KeyReleased(e: KeyEvent)
  class AdhocGameLoopAction private(val handler: () => Unit) {
    def apply() = handler()
  }
  object AdhocGameLoopAction {
    def apply(eventHandler: => Unit) = new AdhocGameLoopAction(() => eventHandler)
  }


  def act() {
    loop {
      react {
        case KeyPressed(e) =>
          addAdHocAction {
            gameActors.foreach(_.keyPressed(e.getKeyCode))
            pressedKeys += e.getKeyCode
          }
        case Add(ga) =>
          addAdHocAction {
            gameActors.enqueue(ga)
            ga.init(bounds, this)
          }
        case eventHandler: AdhocGameLoopAction =>
          addAdHocAction(eventHandler)
        case "ALARM" =>
          step
        case "GAMEOVER" =>
          gameOver = true
        case PaintRequest(g, imageObserver) =>
          _paint(g, imageObserver)
          reply("Done")
      }
    }
  }

  private def addAdHocAction(f: => Unit) = adhocGameLoopActions += AdhocGameLoopAction(f)

  private def step {
    def handleCollisions = {
      for (i <- 0 until gameActors.size;
           j <- i + 1 until gameActors.size;
           first = gameActors(i);
           second = gameActors(j);
           if (first.checkCollisionWith(second))) {
        first.onCollisionWith(second);
        second.onCollisionWith(first);
      }
    }

    adhocGameLoopActions.foreach(_())
    adhocGameLoopActions.clear

    for (ga <- gameActors) {
      ga.keysPressed(pressedKeys)
      ga.update
    }

    handleCollisions

    gameActors.dequeueAll(_.isMarkedForRemoval)
    repainter.repaint
  }

  private def _paint(g: Graphics2D, imageObserver: ImageObserver) {
    g.setColor(Color.BLACK)
    g.fill(bounds)
    gameActors.foreach(_.paint(g, imageObserver))
    if (gameOver) {
      g.setFont(new Font("Courier", Font.BOLD, 124))
      g.setColor(Color.YELLOW)
      g.drawString("GAME OVER", 50, bounds.height / 2)
    }
  }


}

case class Add(ga: GameActor)



