package archived_prototype_game_engine_for_ref.sample_game


import archived_prototype_game_engine_for_ref.SimpleGame

object AsteroidsGame extends SimpleGame {
  def actors = new Ship(100, 200) ::
          (1 to 20).map(_ => new Asteroid).toList :::
          Nil
}