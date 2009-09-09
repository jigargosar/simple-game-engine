package archived_prototype_game_engine_for_ref.sample_game


import archived_prototype_game_engine_for_ref.{GameActor}
import java.awt.{Graphics2D, Color}
import java.awt.image.ImageObserver

class HealthMeter(val initX: Int, val initY: Int) extends GameActor {
  val MAX_HEALTH: Double = 50;
  var currentHealth = MAX_HEALTH;
  x = initX
  y = initY
  //  dx = 1
  override def paint(g: Graphics2D, imageObserver: ImageObserver) {
    g.setColor(Color.CYAN);
    g.drawString("Shields Left: " + (currentHealth / MAX_HEALTH * 100).toInt + "%", x.toFloat, y.toFloat);
  }

  override def onOutsideRoom {
    wrap
  }

  def dSet(i: Int) {
    currentHealth += i;
    if (currentHealth <= 0) {
      currentHealth = 0;
      gameOver
    }
  }
}
