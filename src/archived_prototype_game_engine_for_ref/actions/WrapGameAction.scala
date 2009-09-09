package archived_prototype_game_engine_for_ref.actions


import java.awt.Rectangle

object WrapGameAction {
  object Direction extends Enumeration {
    type Direction = Value
    val horizontal, vertical, horizontal_and_vertical = Value
  }
}
import WrapGameAction.Direction._

trait WrapGameAction {
  self: GameActor =>

  def wrap: Unit =
    wrap(horizontal_and_vertical, roomRect)

  def wrap(direction: Direction, wrapAroundRect: Rectangle) {
    if (direction == horizontal || direction == horizontal_and_vertical) {
      if (x > wrapAroundRect.getWidth()) {
        x = 0 - width + 1;
      } else if (x + width < 0) {
        x = wrapAroundRect.getWidth - 1;
      }
    }

    if (direction == vertical || direction == horizontal_and_vertical) {
      if (y > wrapAroundRect.getHeight()) {
        y = 0 - height + 1;
      } else if (y + height < 0) {
        y = wrapAroundRect.getHeight() - 1;
      }
    }
  }
}