package chess.model;

import java.util.Map;
import java.io.Serializable;

public class GameState implements Serializable{
  private static final long serialVersionUID = 495L;

  private Phase currentPhase;
  private Player currentPlayer;
  private GameField gameField;
  private Player winner;



  /**
   * to initialize the state at beginning.
   */
  public GameState() {
    this.currentPhase = Phase.RUNNING;
    this.currentPlayer = Player.WHITE;
    this.gameField = new GameField();
    this.winner = null;
  }

  public void setgameState(GameState g){
    this.currentPlayer = g.currentPlayer;
    this.currentPhase = g.currentPhase;
    this.gameField = new GameField();
    this.winner = g.winner;
    for(int i=0;i<8;i++){
      for(int j=0;j<8;j++){
        Cell temp = new Cell(i,j);
        if(g.getField().get(temp)!=null){
          if(g.getField().get(temp).getPlayer()==Player.BLACK){
            this.getField().set(temp,new Pawn(Player.BLACK));
          }
          else{
            this.getField().set(temp,new Pawn(Player.WHITE));
          }
        }
        else{
          this.getField().set(temp,null);
        }
      }
    }

  }
  /**
   * Return the current phase of the game.
   *
   * @return the current phase.
   */

  public Phase getCurrentPhase() {
    return currentPhase;
  }

  public GameField getField() {
    return gameField;
  }

  /**
   * To find the player in a specific position.
   *
   * @return the relation.
   */
  public Map<Cell, Player> getCellsOccupiedWithPawns() {
    // TODO insert code here (new for exercise 2)

    return null;
  }

  /**
   * to set the current player. And also change to an other player's turn.
   */

  public void setCurrentPlayer(Player change) {
    if (change == Player.WHITE) {
      change = Player.BLACK;
    } else if (change == Player.BLACK) {
      change = Player.WHITE;
    } else {
      System.out.println("Error! Wrong with change funktion");
    }
    this.currentPlayer = change;
  }

  public Player getchangePlayer(){
    Player change = null;
    if (this.currentPlayer == Player.WHITE) {
      change = Player.BLACK;
    } else if (this.currentPlayer == Player.BLACK) {
      change = Player.WHITE;
    } else {
      System.out.println("Error! Wrong with change funktion");
    }
    return change;
  }
  void setCurrentPhase(Phase phase) {
    this.currentPhase = phase;
  }

  void setWinner(Player player) {
    this.winner = player;
  }

  /**
   * Return the player that is currently allowed to make a move.
   *
   * @return the current player
   */

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Return the winner of the current game. This method may only be called if the current game is
   * finished.
   *
   * @return the current winner.
   */

  public Player getWinner() {
    assert currentPhase == Phase.FINISHED;
    return winner;
  }
}
