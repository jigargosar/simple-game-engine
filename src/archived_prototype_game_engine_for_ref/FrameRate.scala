package archived_prototype_game_engine_for_ref

import java.awt.{Graphics, Dimension, Color}
import java.util.concurrent.TimeUnit._
import java.lang.System._

class FrameRate {
  @volatile
  var lastFrameUpdateTimeInNano = SECONDS.toNanos(1)
  var startTime: Long = _

  def startRecording = startTime = nanoTime

  def stopRecording = lastFrameUpdateTimeInNano = nanoTime - startTime

  def paint(g: Graphics, size: Dimension) {
    g.setColor(Color.WHITE)
    g.drawString(fps + " fps", 0, (size.getHeight - 50).toInt)
  }

  private def fps {
    var rate = "---"
    if (lastFrameUpdateTimeInNano > 0) {
      rate = String.valueOf(SECONDS.toNanos(1) / lastFrameUpdateTimeInNano)
    }
  }
}
