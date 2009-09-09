package archived_prototype_game_engine_for_ref


import java.awt.geom.AffineTransform
import java.awt.Graphics2D
import java.awt.image.{BufferedImage, ImageObserver}

class Sprite(bufferedImage: BufferedImage) {
  val at = new AffineTransform
  var scaleX = 1.0
  var scaleY = 1.0

  def paint(g: Graphics2D, imageObserver: ImageObserver) {
    g.drawImage(bufferedImage, at, imageObserver);
  }

  def update(x: Double, y: Double, angdeg: Double) {
    at.setToIdentity();
    at.translate(x + width / 2, y + height / 2);
    at.rotate(Math.toRadians(angdeg));
    at.translate(-width / 2, -height / 2);
    at.scale(scaleX, scaleY);
  }

  def width = (bufferedImage.getWidth() * scaleX).toInt

  def height = (bufferedImage.getHeight() * scaleY).toInt

  def scale(sx: Double, sy: Double) {
    scaleX = sx
    scaleY = sy
  }
}
