package chess.model;

public enum Player {
  WHITE("White"),
  BLACK("Black");

  private final String playerName;

  Player(String playerName) {
    this.playerName = playerName;
  }

  public Player changePlayer(){
    if(this==Player.BLACK){
      return Player.WHITE;
    }
    else
      return Player.BLACK;
  }

  @Override
  public String toString() {
    return playerName;
  }
}
