package archived_prototype_game_engine_for_ref.sample_game


import archived_prototype_game_engine_for_ref.{SpriteActor, GameActor}

import java.awt.Rectangle

class Bullet(rectangle: Rectangle, degrees: Double)
        extends SpriteActor("bullet.png") {
  faceAngle = degrees
  setCenterX(rectangle.getCenterX())
  setCenterY(rectangle.getCenterY())
  addThrust(degrees, 5)



  override def onOutsideRoom {
    markForRemoval
  }

  override def onCollisionWith(that: GameActor) {
    //        if(that instanceof Asteroid){
    //            markForRemoval()
    //        }

  }
}