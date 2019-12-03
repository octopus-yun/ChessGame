package chess;

import chess.model.Cell;
import chess.model.Chess;
import chess.model.Pawnposition;
import chess.model.Player;
import java.io.IOException;
import java.util.Scanner;

public class Shell {


  private static final char START_LETTER = 'A';
  private static final int START_ROW = 1;

  private Chess chess;

  /**
   * Read and process input until the quit command has been entered.
   *
   * @param args Command line arguments.
   * @throws IOException Error reading from stdin.
   */
  public static void main(String[] args) throws IOException {
    final Shell shell = new Shell();
    shell.run();
  }

  /** When the command is "get help", it will guide you handel the game.*/
  private void showHelp() {
    System.out.println("new: ");
    System.out.println("new chess and throw the chess bevor away");
    System.out.println("move from to:");
    System.out.println("take the pawn from position from to position to ");
    System.out.println("print:");
    System.out.println("print the chess now ");
    System.out.println("help:");
    System.out.println("show help");
    System.out.println("quit:");
    System.out.println("quit out");
  }

  /** Run the chess shell. Shows prompt 'Chess>', takes commands from the user and executes them. */
  public void run() {
    Scanner cin = new Scanner(System.in, "UTF-8");
    boolean quit = false;
    chess = new Chess();
    Player temp;
    boolean check = false;
    Pawnposition p = null;
    int checknew = 0;

    while (!quit) {
      System.out.print("Chess> ");
      String str = cin.nextLine();
      if (str.length() <= 0) {
        System.out.println("Error! Not support this command1!\n");
      }
      else {
        String[] cmd = str.split(" ");
        if ("new".startsWith(cmd[0]) && cmd.length == 2) {
          if(cmd[1].equals("SINGLE")||(cmd[1].equals("single"))){
            chess = new Chess();
            checknew = 1;
          }
          if(cmd[1].equals("HOTSEAT")){
            chess = new Chess();
          }
        }
        else if ("help".startsWith(cmd[0]) && cmd.length == 1) {
          this.showHelp();
        }
        else if ("quit".startsWith(cmd[0]) && cmd.length == 1) {
          return;
        }
        else if ("move".startsWith(cmd[0]) && cmd.length == 3) {
          Cell f = new Cell(this.inputChange(cmd[1].charAt(0)), this.inputChange(cmd[1].charAt(1)));
          Cell t = new Cell(this.inputChange(cmd[2].charAt(0)), this.inputChange(cmd[2].charAt(1)));

          check = chess.move(f, t);
          if (check == true) {
            if ((chess.getState().getWinner() == Player.BLACK) || (chess.getState().getWinner() == Player.WHITE)) {
                Player tempforwin = this.chess.getState().getCurrentPlayer();
                System.out.println(tempforwin + " moved " + cmd[1] + " to " + cmd[2]);
                System.out.println("Game over. " + tempforwin + " has won");
              continue;
            }
              if (this.chess.getState().getCurrentPlayer() == Player.WHITE) {
                temp = Player.BLACK;
                System.out.println(temp + " moved " + cmd[1] + " to " + cmd[2]);
              }
              if (this.chess.getState().getCurrentPlayer() == Player.BLACK) {
                temp = Player.WHITE;
                System.out.println(temp + " moved " + cmd[1] + " to " + cmd[2]);
                if(checknew == 1) {
                  p = this.chess.minmax(0, Player.BLACK);
                  if (p == null) {
                    System.out.println("Fehler bei p");
                  } else if (p.getresult() == 0) {
                    System.out.println("Fehler bei p.r");
                  } else if (p.getcellt() == null) {
                    System.out.println("Fehler bei p.getcellt");
                  } else if (p.getcellf() == null) {
                    System.out.println("Fehler bei p.getcellf");
                  } else {
                    this.chess.move(p.getcellf(), p.getcellt());
                  }
                }
              }
            }
            if (this.chess.ifmiss() == false && chess.getState().getWinner() == null) {
              System.out.println(this.chess.getState().getCurrentPlayer() + " must miss a turn");
              this.chess.getState().setCurrentPlayer(this.chess.getState().getCurrentPlayer());
            }


          }
          else if ("print".startsWith(cmd[0]) && cmd.length == 1) {
            if (chess == null) {
              System.out.println("Error! Fehler chess");
            }
            else {
              chess.print();
            }
          }
          else {
            System.out.println("Error! Not support this command!");
          }
      }
    }
  }

  /** to change the letter you have given to the letters that computer knows.*/
  private int inputChange(char c) {

    int co = 0;
    if ((c == 'A') || (c == 'a') || (c == '1')) {
      co = 0;
    }
    if ((c == 'B') || (c == 'b') || (c == '2')) {
      co = 1;
    }
    if ((c == 'C') || (c == 'c') || (c == '3')) {
      co = 2;
    }
    if ((c == 'D') || (c == 'd') || (c == '4')) {
      co = 3;
    }
    if ((c == 'E') || (c == 'e') || (c == '5')) {
      co = 4;
    }
    if ((c == 'F') || (c == 'f') || (c == '6')) {
      co = 5;
    }
    if ((c == 'G') || (c == 'g') || (c == '7')) {
      co = 6;
    }
    if ((c == 'H') || (c == 'h') || (c == '8')) {
      co = 7;
    }
    return co;
  }


  /**
   * Parse the row value of a given cell string and return the corresponding index from 0-7.
   * For example, <code>parseRowValue("C1") = 0</code>
   * @param value the string to parse
   * @return the corresponding row index, from 0-7
   */
  private int parseRowValue(String value) {
    char number = value.charAt(1);
    if (!Character.isDigit(number)) {
      throw new IllegalArgumentException("Char '" + number + "' is not a number.");
    }
    return Character.getNumericValue(number) - START_ROW;
  }

  /**
   * Parse the column value of a given cell string and return the corresponding index from 0-7.
   * For example, <code>parseColumnValue("C1") = 2</code>
   * @param value the string to parse
   * @return the corresponding row index, from 0-7
   */
  private int parseColumnValue(String value) {
    char letter = value.charAt(0);
    if (!Character.isLetter(letter)) {
      throw new IllegalArgumentException("Char '" + letter + "' is not a letter.");
    }
    return letter - START_LETTER;
  }
}
