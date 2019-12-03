package chess.view;

import chess.model.Cell;

/**
 * The main controller interface of the chess game. It takes the actions from the user and handles
 * them accordingly. This is by either invoking the necessary model-methods, or by directly telling
 * the view to change its graphical user-interface.
 */
public interface Controller {

  /**
   * Set the view that the controller will use afterwards.
   *
   * @param view The {@link View}.
   */
  void setView(View view);

  /** Initializes and starts the user interface. */
  void start();

  /** Reset a game such that the game is in its initial state afterwards. */
  void resetGame();

  /**
   * Execute a step on the chess board.
   *
   * @param from The {@link Cell source cell}.
   * @param to The {@link Cell target cell}.
   * @return <code>true</code> if the move was executed successfully; <code>false</code> otherwise.
   */
  boolean move(Cell from, Cell to);

  /** Dispose any remaining resources. */
  void dispose();
}
