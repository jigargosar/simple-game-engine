package archived_prototype_game_engine_for_ref


import java.awt.image.ImageObserver
import java.awt.{Graphics2D}
import archived_prototype_game_engine_for_ref.ImageFactory.imageFactory

abstract class SpriteActor(relativePath: String) extends GameActor {
  protected val sprite = imageFactory.createSprite("res/" + relativePath)

  override def height = sprite.height

  override def width = sprite.width

  protected def scale(sx: Double, sy: Double) = sprite.scale(sx, sy)

  override def update {
    super.update
    sprite.update(x, y, faceAngle);
  }

  override def paint(g: Graphics2D, imageObserver: ImageObserver) {
    sprite.paint(g, imageObserver)
    super.paint(g, imageObserver)
  }
}
