package chess.model;

import java.util.Objects;

/**
 * This class {@code Cell} is written about the position in the chess.
 */
public class Cell {

  private int column;
  private int row;


  /**
   * Every position has x and y in this class.
   * 
   * @param column is the position with x-axis.
   * @param row is the position with y-axis.
   *
   */
  public Cell(int column, int row) {
    this.column = column;
    this.row = row;
  }

  /**
   * Returns the column of this cell as integer index. Column values range from 0 to 7 and describe
   * chess columns A to H, respectively.
   *
   * @return the column of this cell, as integer index
   */

  public int getColumn() {
    return this.column;
  }

  /**
   * Returns the row of this cell as integer index. Row values range from 0 to 7 and describe chess
   * rows 1 to 8, respectively.
   *
   * @return the row of this cell, as integer index
   */
  public int getRow() {
    return this.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Cell)) {
      return false;
    }

    Cell other = (Cell) obj;
    return Objects.equals(column, other.column) && Objects.equals(row, other.row);
  }

  @Override
  public String toString() {
    return String.valueOf(column) + row;
  }
}
