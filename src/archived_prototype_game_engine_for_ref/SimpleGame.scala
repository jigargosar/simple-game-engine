package archived_prototype_game_engine_for_ref

import actors.{Actor}
import java.awt.{Graphics, Graphics2D, RenderingHints}
import java.util.{List => JList}
import java.util.Arrays._
import scala.swing._

trait SimpleGame extends SimpleGUIApplication {
  def actors: List[GameActor]

  implicit def scalaListToJavaList[T](list: List[T]): JList[T] = asList(list.toArray[T]: _*)


  def top = new MainFrame {
    val mainFrame = this
    title = "Pappu game engine"
    val gamePanel = new Component {
      var room: GameRoom = _
      peer.setFocusable(true)

      override def paint(g: Graphics) {
        val g2 = g.asInstanceOf[Graphics2D]
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        if (room != null) {
          room.paint(g2, null)
        }
      }

      def setCurrentRoom(room: GameRoom) = {
        this.room = room
        preferredSize = room.bounds.getSize
        //next line required on mac
        size = room.bounds.getSize
        peer.addKeyListener(room)
        mainFrame.pack
      }
    }
    contents = gamePanel


    val room = new GameRoom(actors, gamePanel)
    gamePanel.setCurrentRoom(room)
  }

}

