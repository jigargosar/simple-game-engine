package archived_prototype_game_engine_for_ref.sample_game


import archived_prototype_game_engine_for_ref.{GameActor, SpriteActor}
import java.util.Random

class Asteroid extends SpriteActor("asteroid.png") {
  val random = Asteroid.random
  val random2 = Asteroid.random2

  override def created() {
    x = random.nextInt(roomRect.getWidth.toInt)
    y = random.nextInt(roomRect.getHeight.toInt)
    dx = random.nextDouble() * 5.0
    dy = random.nextDouble() * 5.0
    val scl = 0.5 + random.nextDouble() * 2.0
    scale(scl, scl)
    faceAngle = random.nextInt(360)
  }

  override def update {
    super.update
    if (random2.nextInt(1000) < 20) {
      //            gameRoom.add(new Asteroid())
    }
  }

  override def onOutsideRoom {
    wrap
  }

  override def onCollisionWith(that: GameActor) {
    if (that.isInstanceOf[Bullet]) {
      markForRemoval
    }

    if (that.isInstanceOf[Ship]) {
      markForRemoval
    }
  }
}

object Asteroid {
  val random: Random = new Random()
  val random2: Random = new Random(0)
}