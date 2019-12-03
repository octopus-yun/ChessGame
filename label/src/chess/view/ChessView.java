package chess.view;

import static java.util.Objects.requireNonNull;

import chess.model.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * create a frame.
 */
public class ChessView extends JFrame implements View {

  private static final long serialVersionUID = 1L;

  private final DrawBoard drawBoard;

  private Model model;
  private Controller controller;
  private JFrame jf = new JFrame();

  /**
   * Draw a pawn on the current selected cell.
   *
   * @param model It includes the current chess, when the model changed, the frame should
   *              also corresponding change.
   * @param controller to show the corresponding view with model.
   *
   */
  public ChessView(Model model, Controller controller) {
    this.model = requireNonNull(model);
    this.controller = requireNonNull(controller);
    this.setTitle("Pawn Game");
    this.setBackground(Color.BLACK);
    this.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
    this.setSize(500, 500);
    this.setLayout(new BorderLayout());

    setLocationRelativeTo(null);

    drawBoard = new DrawBoard(this, model);

  }

  @Override
  public void showGame() {
    this.setVisible(true);
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        handleChangeEvent(event);
      }
    });
  }

  /**
   * The observable (= model) has just published that it has changed its state. The GUI needs to be
   * updated accordingly here.
   *
   * @param event The event that has been fired by the model.
   */
  private void handleChangeEvent(PropertyChangeEvent event) {

    // the next lines are for demonstration purposes
    if (event.getPropertyName().equals(Model.STATE_CHANGED)) {
      System.out.println("Model has changed its state.");
    }

    // TODO insert code here
  }

  // TODO insert code here

}
