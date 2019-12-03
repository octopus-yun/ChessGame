package chess.view;

import static java.util.Objects.requireNonNull;

import chess.model.Cell;
import chess.model.Chess;
import chess.model.Model;


public class ChessController implements Controller {

  private Model model;
  private View view;


  public ChessController(Model chess) {
    model = requireNonNull(chess);
  }

  @Override
  public void setView(View view) {
    this.view = requireNonNull(view);
  }

  @Override
  public void start() {
    view.showGame();
  }

  @Override
  public void resetGame() {
    this.model = new Chess();
    Controller controller = new ChessController(model);
    this.view = new ChessView(this.model, controller);
    controller.setView(view);
    model.addPropertyChangeListener(view);
    controller.start();
  }

  @Override
  public boolean move(Cell from, Cell to) {
    return false;
  }

  @Override
  public void dispose() {
    model.removePropertyChangeListener(view);
  }
}
