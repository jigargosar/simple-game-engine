package simple_game_engine


import java.awt.{Dimension, Graphics2D}

abstract class Sprite(val width: Int, val height: Int) {
  def bounds = new Dimension(width, height)

  def paint(x: Int, y: Int, g: Graphics2D)
}

object EmptySprite extends Sprite(0, 0) {
  def paint(x: Int, y: Int, g: Graphics2D) {}
}