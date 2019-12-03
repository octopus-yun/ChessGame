package chess.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

/**
 * The main interface of the chess model. It provides all necessary methods for accessing and
 * manipulating the data such that a game can be played successfully.
 *
 * <p>When something changes in the model, the model notifies its observers by firing a
 * {@link PropertyChangeEvent change-event}.
 */
public interface Model {

  static final String STATE_CHANGED = "State changed";

  /**
   * Add a {@link PropertyChangeListener} to the model that will be notified about the changes made
   * to the chess board.
   *
   * @param pcl the view that implements the listener.
   */
  void addPropertyChangeListener(PropertyChangeListener pcl);

  /**
   * Remove a listener from the model, which will then no longer be notified about any events in the
   * model.
   *
   * @param pcl the view that then no longer receives notifications from the model.
   */
  void removePropertyChangeListener(PropertyChangeListener pcl);

  /**
   * Move pawn from one cell to another and deal with the consequences. Moving a pawn only works if
   * their is currently a game running, if the given cell contains a pawn of the current player, and
   * if moving to the other cell is a valid chess move. After the move, it will be the turn of the
   * next player, unless he has to miss a turn because he can't move any pawn or the game is over.
   *
   * @param from cell to move pawn from
   * @param to cell to move pawn to
   * @return <code>true</code> if the move was successful, <code>false</code> otherwise
   */
  boolean move(Cell from, Cell to);

  boolean checkifsame (GameState a, GameState b);
  void print();

  boolean ifmiss();

  Pawnposition minmax(int deepth, Player player);


  /**
   * To set a new game for the chess.
   */
  void setChess();

  GameState getState();

  /**
   * Computes all possible moves for a selected cell. There are in total four moves possible for a
   * single pawn, depending on the current position of the pawn as well as the position of pawns
   * from the opponent player.
   *
   * @param cell The {@link Cell cell} that the {@link Pawn pawn} is currently positioned.
   * @return A set of cells with all possible moves for the current selected pawn.
   */
  Set<Cell> getPossibleMovesForPawn(Cell cell);
}
