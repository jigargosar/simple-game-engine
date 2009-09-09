package simple_game_engine


trait SimpleGameEngine
        extends GameLoopComponent with GameUIComponent {
  val gameActors: List[GameActor]
  val gameLoop: GameLoop = new GameLoop
  val gameUI: GameUI = new GameUI

  def main(args: Array[String]) = {
    gameLoop.start(gameActors)
    gameUI.main(args)
  }
}