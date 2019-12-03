package chess.model;

import java.io.Serializable;

/**
 * In this class the chess has been defined. The chess is 8 long and 8 wight.
 */
public class GameField implements Serializable {
  private static final long serialVersionUID = 495L;

  public static final int SIZE = 8;
  private Pawn[][] field;

  /**
   * to initialize the 8*8 chess. At the beginning the above is "black" and the below is "White".
   */
  public GameField() {
    field = new Pawn[SIZE][SIZE];
    for (int i = 0; i < SIZE; i++) {
      this.field[i][0] = new Pawn(Player.WHITE);
    }
    for (int j = 0; j < SIZE; j++) {
      this.field[j][7] = new Pawn(Player.BLACK);
    }
    for (int m = 0; m < SIZE; m++) {
      for (int n = 1; n < SIZE - 1; n++) {
        this.field[m][n] = null;
      }
    }
  }


  /**
   * to get the pawn.
   *
   * @param cell the pawn in this position will be returned.
   * @return <code>pawn</code> in this position.
   */
  // to get the pawn in a certain position.
  public Pawn get(Cell cell) {
    throwErrorWhenOutOfBounds(cell);
    return this.field[cell.getColumn()][cell.getRow()];
  }

  /**
   * Set pawn on the given cell. Any pawns already on that cell will be overridden.
   *
   * @param cell cell to set pawn on
   * @param newValue new value (pawn) to set on the cell
   * @throws IllegalArgumentException if given cell is out of field bounds
   */
  void set(Cell cell, Pawn newValue) {
    throwErrorWhenOutOfBounds(cell);
    //
    this.field[cell.getColumn()][cell.getRow()] = newValue;
  }

  /**
   * Remove pawn from the given cell. This method only has to work if there is a pawn on the cell.
   *
   * @param cell cell to remove any pawn from
   * @return the pawn that was removed
   * @throws IllegalArgumentException if given cell is out of field bounds
   */

  Pawn remove(Cell cell) {
    throwErrorWhenOutOfBounds(cell);
    // TODO
    Pawn re = this.field[cell.getColumn()][cell.getRow()];
    this.field[cell.getColumn()][cell.getRow()] = null;
    return re;
  }

  private void throwErrorWhenOutOfBounds(Cell cell) {
    if (!isWithinBounds(cell)) {
      throw new IllegalArgumentException("Coordinates of cell are out of bounds: " + cell);
    }
  }

  boolean isWithinBounds(Cell cell) {
    return cell.getColumn() >= 0 && cell.getColumn() < SIZE && cell.getRow() >= 0
        && cell.getRow() < SIZE;
  }
}
