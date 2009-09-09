package simple_game_engine


import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{Graphics, Graphics2D, RenderingHints}
import swing.{Component, MainFrame, SimpleGUIApplication}

trait GameUIComponent {
  this: GameLoopComponent =>
  val gameUI: GameUI
  class GameUI extends SimpleGUIApplication {
    private val gamePanel = new Component {
      preferredSize = gameLoop.bounds.getSize
      size = gameLoop.bounds.getSize
      peer.setFocusable(true)
      peer.addKeyListener(new KeyListener() {
        override def keyPressed(e: KeyEvent) = gameLoop ! GameLoop.KeyPressed(e)

        override def keyReleased(e: KeyEvent) = gameLoop ! GameLoop.KeyReleased(e)

        override def keyTyped(e: KeyEvent) = {}
      })

      override def paint(g: Graphics) {
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        gameLoop !? GameLoop.Paint(g2, null)
      }
    }

    def top = new MainFrame {
      title = "Pappu game engine"
      contents = gamePanel
    }

    def repaint = gamePanel.repaint
  }
}