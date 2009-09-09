package archived_prototype_game_engine_for_ref.actions

trait ThrustAction {
  self: GameActor =>
  def addThrust(angle: Double, speed: Double) {
    dx += calcAngleMoveX(angle) * speed;
    dy += calcAngleMoveY(angle) * speed;
  }

  private def calcAngleMoveX(angle: Double) = {
    Math.cos((angle - 90) * Math.Pi / 180);
  }

  private def calcAngleMoveY(angle: Double) = {
    Math.sin((angle - 90) * Math.Pi / 180);
  }
}
