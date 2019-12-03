package chess.view;

import java.beans.PropertyChangeListener;

/**
 * The main interface of the view. It gets the state it displays directly from the {@link Model}.
 */
public interface View extends PropertyChangeListener {

  /** Show the graphical user interface of the chess game. */
  void showGame();
}
