package chess.model;

import java.io.Serializable;

public class Pawn implements Serializable {
  private static final long serialVersionUID = 495L;


  private final Player player;

  Pawn(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
