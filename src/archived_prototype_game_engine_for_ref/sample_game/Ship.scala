package archived_prototype_game_engine_for_ref.sample_game


import archived_prototype_game_engine_for_ref.actions.{WrapGameAction}
import archived_prototype_game_engine_for_ref.{GameActor, SpriteActor}
import java.awt.event.KeyEvent._
import java.util.concurrent.TimeUnit._
import scala.Math._
import java.lang.System._

class Ship(val initX: Int, val initY: Int)
        extends SpriteActor("plane.png") {
  val health = new HealthMeter(100, 10)
  val ROTATE_SHIP_BY_DEGREES = 5


  override def created() {
    addToRoom(health)
    x = initX
    y = initY

    whilePressed(VK_UP, new Runnable() {
      def run() {
        addThrust(faceAngle, 0.5)
        //todo: move to thrustAction class
        val MAX_VELOCITY = 5
        dx = min(abs(dx), MAX_VELOCITY) * signOf(dx)
        dy = min(abs(dy), MAX_VELOCITY) * signOf(dy)
      }
    })
    whilePressed(VK_LEFT, new Runnable() {
      def run() {
        faceAngle -= ROTATE_SHIP_BY_DEGREES
      }
    })
    whilePressed(VK_RIGHT, new Runnable() {
      def run() {
        faceAngle += ROTATE_SHIP_BY_DEGREES
      }
    })
    whilePressed(VK_SPACE, new Runnable() {
      var lastFiredAt: Long = 0

      def run() {
        if (nanoTime() - lastFiredAt > MILLISECONDS.toNanos(100)) {
          addToRoom(new Bullet(bounds, faceAngle))
          lastFiredAt = nanoTime()
        }
      }
    })
  }

  private def signOf(dx: Double) = abs(dx) / dx


  override def onCollisionWith(that: GameActor) {
    if (that.isInstanceOf[Asteroid]) {
      health.dSet(-5)
    }
  }

  override def onOutsideRoom {
    wrap
  }
}
