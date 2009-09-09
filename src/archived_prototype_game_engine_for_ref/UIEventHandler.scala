package archived_prototype_game_engine_for_ref


import java.awt.event.{KeyEvent, KeyListener}
import java.awt.Graphics2D
import java.awt.image.ImageObserver


trait UIEventHandler extends KeyListener {
  self: GameRoom =>

  def paint(g: Graphics2D, imageObserver: ImageObserver) {
    this !? PaintRequest(g, imageObserver)
  }

  override def keyTyped(e: KeyEvent) {}

  override def keyPressed(e: KeyEvent) {
    this ! KeyPressed(e)
  }

  override def keyReleased(e: KeyEvent) {
    this ! AdhocGameLoopAction {
      pressedKeys -= e.getKeyCode
    }
  }


}