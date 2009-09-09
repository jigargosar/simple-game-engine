package archived_prototype_game_engine_for_ref

import actions.{AllGameActorActions, WrapGameAction}
import actors.Actor
import java.awt.image.ImageObserver
import java.awt.{Graphics2D, Rectangle}
import scala.collection._

abstract class GameActor extends AllGameActorActions {
  var x = 0.0
  var dx = 0.0
  var y = 0.0
  var dy = 0.0
  var faceAngle = 0.0
  private val whileKeyPressMap = new mutable.HashMap[Integer, Runnable]
  private val onKeyPressMap = new mutable.HashMap[Integer, Runnable]
  private var markedForRemoval = false

  var roomRect: Rectangle = _
  protected var gameLoop: Actor = _

  def height = 0

  def width = 0

  def bounds = new Rectangle(x.toInt, y.toInt, width, height)

  def init(roomRect: Rectangle, gameLoop: Actor) {
    this.roomRect = roomRect
    this.gameLoop = gameLoop
    created
  }

  def addToRoom(gameActor: GameActor) = gameLoop ! Add(gameActor)

  def gameOver = gameLoop ! "GAMEOVER"

  def created {}

  def update() {
    x += dx
    y += dy
    if (!bounds.intersects(roomRect)) {
      onOutsideRoom
    }
  }

  protected def onOutsideRoom {}


  def checkCollisionWith(that: GameActor) = bounds.intersects(that.bounds)

  def onCollisionWith(that: GameActor) {}

  def paint(g: Graphics2D, imageObserver: ImageObserver) {
    //        g.setColor(Color.RED);
    //        g.draw(rect());
  }

  def whilePressed(keyCode: Int, runnable: Runnable) {
    whileKeyPressMap.put(keyCode, runnable)
  }

  def keysPressed(pressedKeys: Set[Int]) {
    for (pressedKey <- pressedKeys;
         runnable <- whileKeyPressMap.get(pressedKey)) {
      runnable.run
    }
  }

  protected def onKeyPress(keyCode: Int, runnable: Runnable) = onKeyPressMap(keyCode) = runnable

  def keyPressed(keyCode: Int) = onKeyPressMap.get(keyCode).foreach(_.run)

  protected def markForRemoval = this.markedForRemoval = true

  def isMarkedForRemoval = this.markedForRemoval

  def setCenterX(x: Double) = this.x = x - width / 2

  def setCenterY(y: Double) = this.y = y - height / 2
}


