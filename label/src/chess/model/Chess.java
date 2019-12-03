package chess.model;

import javax.swing.*;

import static java.util.Objects.requireNonNull;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Set;
import java.lang.*;

/**
 * To set all the rules of the game and give the program the corresponding orders.
 */
public class Chess implements Model {

    private final PropertyChangeSupport support;

    private GameState state;

    // TODO: add javadoc

    /**
     * To set a new chess for the game.
     */
    public Chess() {
        support = new PropertyChangeSupport(this);
        this.state = new GameState();

        // TODO: set game state with initial pawns
    }

    /**
     * To copy a chess which is the same as "this".
     */
    public Chess copychess() {
        Chess copy = new Chess();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell temp = new Cell(i, j);
                if (this.getState().getField().get(temp) != null) {
                    if (this.getState().getField().get(temp).getPlayer() == Player.BLACK) {
                        copy.getState().getField().set(temp, new Pawn(Player.BLACK));
                    } else {
                        copy.getState().getField().set(temp, new Pawn(Player.WHITE));
                    }
                } else {
                    copy.getState().getField().set(temp, null);
                }
            }
        }

        if (this.getState().getCurrentPlayer() == Player.BLACK) {
            copy.getState().setCurrentPlayer(Player.WHITE);
        } else {
            copy.getState().setCurrentPlayer(Player.BLACK);
        }

        if (this.getState().getWinner() != null && this.getState().getCurrentPhase() == Phase.FINISHED) {
            if (this.getState().getWinner() == Player.BLACK) {
                copy.getState().setWinner(Player.BLACK);
            } else {
                copy.getState().setWinner(Player.WHITE);
            }
            copy.getState().setCurrentPhase(Phase.FINISHED);
        }
        //copy.state = new GameState(this.state);
        //copy=this;
        return copy;
    }

    @Override
    public void setChess() {
        this.state = new GameState();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        requireNonNull(pcl);
        support.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        requireNonNull(pcl);
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public boolean move(Cell from, Cell to) {
        int fco = from.getColumn();
        int fr = from.getRow();
        int tc = to.getColumn();
        int tr = to.getRow();
        Cell tempw = new Cell(fco, fr + 1);
        Cell tempb = new Cell(fco, fr - 1);
        if (this.getState().getCurrentPhase() != Phase.FINISHED) {
            if (fco == tc) {
                if (fr + 1 == tr && state.getField().get(to) == null
                        && state.getCurrentPlayer().toString().equals("White")
                        && state.getField().get(from) != null) {
                    state.getField().set(to, state.getField().get(from));
                    state.getField().remove(from);
                    ifwin(tr);
                    if (this.getState().getWinner() == null) {
                        state.setCurrentPlayer(state.getCurrentPlayer());
                    }
                    return true;
                } else if (fr + 2 == tr && state.getField().get(to) == null
                        && state.getField().get(tempw) == null
                        && state.getCurrentPlayer().toString().equals("White") && fr == 0
                        && state.getField().get(from) != null) {
                    state.getField().set(to, state.getField().get(from));
                    state.getField().remove(from);
                    ifwin(tr);
                    if (this.getState().getWinner() == null) {
                        state.setCurrentPlayer(state.getCurrentPlayer());
                    }
                    return true;
                } else if (fr - 1 == tr && state.getField().get(to) == null
                        && state.getCurrentPlayer().toString().equals("Black")
                        && state.getField().get(from) != null) {
                    state.getField().set(to, state.getField().get(from));
                    state.getField().remove(from);
                    ifwin(tr);
                    if (this.getState().getWinner() == null) {
                        state.setCurrentPlayer(state.getCurrentPlayer());
                    }
                    return true;
                } else if (fr - 2 == tr && state.getField().get(to) == null
                        && state.getField().get(tempb) == null
                        && state.getCurrentPlayer().toString().equals("Black") && fr == 7
                        && state.getField().get(from) != null) {
                    state.getField().set(to, state.getField().get(from));
                    state.getField().remove(from);
                    ifwin(tr);
                    if (this.getState().getWinner() == null) {
                        state.setCurrentPlayer(state.getCurrentPlayer());
                    }
                    return true;
                } else {
                    System.out.println("Error! you have given a wrong value");
                    return false;
                }
            } else if ((fco == tc + 1 || fco == tc - 1) && fr + 1 == tr
                    && state.getField().get(to) != state.getField().get(from)
                    && state.getCurrentPlayer().toString().equals("White")
                    && state.getField().get(from) != null
                    && (state.getField().get(to) != null && state.getField().get(to).getPlayer() == Player.BLACK)) {
                return this.ifschlag(from, to, tr);
            } else if ((fco == tc - 1 || fco == tc + 1) && fr - 1 == tr
                    && state.getField().get(to) != state.getField().get(from)
                    && state.getCurrentPlayer().toString().equals("Black")
                    && state.getField().get(from) != null
                    && state.getField().get(to).getPlayer() == Player.WHITE) {
                return this.ifschlag(from, to, tr);
            } else {
                System.out.println("Error! You have given a wrong value.");
                return false;
            }
        } else {
            System.out.println("Error! The game is already finished.");
            return false;
        }

    }

    /**
     * ifschlag.
     *
     * @param f Cell
     * @param t Cell
     * @param m int
     * @return ifschlag ifschlag
     */
    public boolean ifschlag(Cell f, Cell t, int m) {
        if (state.getField().get(t) != null) {
            state.getField().set(t, state.getField().get(f));
            state.getField().remove(f);
            ifwin(m);
            if (this.getState().getWinner() == null) {
                state.setCurrentPlayer(state.getCurrentPlayer());
            }
            return true;
        } else {
            System.out.println("Error! you have given a wrong value");
            return false;
        }
    }

    /**
     * to get the other Player.
     *
     * @return the other Player.
     */
    // This Function is to change the player to an other.
    public Player changeplayer(Player now) {
        if (now == Player.WHITE) {
            return Player.BLACK;
        } else if (now == Player.BLACK) {
            return Player.WHITE;
        } else {
            return null;
        }
    }

    @Override
    public boolean ifmiss() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell temp = new Cell(i, j);
                Cell temp1 = new Cell(i, j + 1);
                Cell temp2 = new Cell(i - 1, j + 1);
                Cell temp3 = new Cell(i + 1, j + 1);
                Cell temp4 = new Cell(i + 1, j - 1);
                Cell temp5 = new Cell(i - 1, j - 1);
                Cell temp6 = new Cell(i, j - 1);
                if (this.state.getField().get(temp) != null
                        && this.state.getField().get(temp).getPlayer() == Player.WHITE
                        && this.state.getCurrentPlayer() == Player.WHITE) {
                    if (null != this.state.getField().get(temp).getPlayer()
                            && this.getState().getCurrentPlayer() != null && this.state.getField().get(temp)
                            .getPlayer() == this.getState().getCurrentPlayer()) {
                        if (j < 7) {
                            if (this.state.getField().get(temp1) == null) {
                                return true;
                            }
                        }
                        if (i > 0 && j < 7) {
                            if (this.state.getField().get(temp2) != null) {
                                if (this.state.getField().get(temp2).getPlayer() != null
                                        && this.state.getField().get(temp2).getPlayer() == this
                                        .changeplayer(this.getState().getCurrentPlayer())) {
                                    return true;
                                }
                            }
                            if (i < 7 && j < 7) {
                                if (this.state.getField().get(temp3) != null) {
                                    if (this.state.getField().get(temp3).getPlayer() != null
                                            && this.state.getField().get(temp3).getPlayer() == this
                                            .changeplayer(this.getState().getCurrentPlayer())) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.state.getField().get(temp) != null
                        && this.state.getField().get(temp).getPlayer() == Player.BLACK
                        && this.state.getCurrentPlayer() == Player.BLACK) {
                    if (null != this.state.getField().get(temp).getPlayer()
                            && this.getState().getCurrentPlayer() != null && this.state.getField().get(temp)
                            .getPlayer() == this.getState().getCurrentPlayer()) {
                        if (j > 0) {
                            if (this.state.getField().get(temp6) == null) {
                                return true;
                            }
                        }

                        if (i < 7 && j > 0 && this.state.getField().get(temp4) != null) {
                            if (this.state.getField().get(temp4).getPlayer() != null
                                    && this.state.getField().get(temp4).getPlayer() == this
                                    .changeplayer(this.getState().getCurrentPlayer())) {
                                return true;
                            }
                        }

                        if (i > 0 && j > 0 && this.state.getField().get(temp5) != null) {
                            if (this.state.getField().get(temp5).getPlayer() != null
                                    && this.state.getField().get(temp5).getPlayer() == this
                                    .changeplayer(this.getState().getCurrentPlayer())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * ifwin.
     *
     * @param r r
     */
    public void ifwin(int r) {
        if (this.getState().getCurrentPlayer() == Player.WHITE && r == 7) {
            this.getState().setWinner(Player.WHITE);
            this.getState().setCurrentPhase(Phase.FINISHED);
        }
        if (this.getState().getCurrentPlayer() == Player.BLACK && r == 0) {
            this.getState().setWinner(Player.BLACK);
            this.getState().setCurrentPhase(Phase.FINISHED);
        }
    }

    /**
     * print.
     */
    @Override
    public void print() {
        for (int i = 7; i >= 0; i--) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < 8; j++) {
                Cell position = new Cell(j, i);
                if (this.state.getField().get(position) != null) {
                    String name = this.state.getField().get(position).getPlayer().toString();
                    if (name.equals("White")) {
                        name = "w";
                    } else if (name.equals("Black")) {
                        name = "b";
                    } else {
                        System.out.print("Error! There is something wrong");
                    }
                    System.out.print(name + "");
                } else {
                    System.out.print(".");
                }
            }
            System.out.print("\n");
        }
        System.out.print("  ");
        System.out.print("A");
        System.out.print("B");
        System.out.print("C");
        System.out.print("D");
        System.out.print("E");
        System.out.print("F");
        System.out.print("G");
        System.out.println("H");
        System.out.println("Player's turn: " + this.getState().getCurrentPlayer().toString());
    }

    @Override
    public Set<Cell> getPossibleMovesForPawn(Cell cell) {
        // TODO insert code here (new for exercise 2)
        Set<Cell> set = new HashSet<Cell>();
        if (this.getState().getCurrentPlayer() == Player.BLACK) {
            Cell tempu = new Cell(cell.getColumn(), (cell.getRow() - 1));
            Cell tempu2 = new Cell(cell.getColumn(), (cell.getRow() - 2));
            Cell templ = new Cell(cell.getColumn() - 1, cell.getRow() - 1);
            Cell tempr = new Cell(cell.getColumn() + 1, cell.getRow() - 1);
            if (cell.getRow() != 0) {
                if (this.getState().getField().get(tempu) == null) {
                    set.add(tempu);
                }
                if (cell.getColumn() != 0) {
                    if (this.getState().getField().get(templ) != null) {
                        if (this.getState().getField().get(templ).getPlayer() == Player.WHITE) {
                            set.add(templ);
                        }
                    }
                }
                if (cell.getColumn() != 7) {
                    if (this.getState().getField().get(tempr) != null) {
                        if (this.getState().getField().get(tempr).getPlayer() == Player.WHITE) {
                            set.add(tempr);
                        }
                    }
                }
            }
            if (cell.getRow() == 7) {
                if (this.getState().getField().get(tempu2) == null) {
                    set.add(tempu2);
                }
            }
        }
        if (this.getState().getCurrentPlayer() == Player.WHITE) {
            Cell tempo = new Cell(cell.getColumn(), (cell.getRow() + 1));
            Cell tempo2 = new Cell(cell.getColumn(), (cell.getRow() + 2));
            Cell templw = new Cell(cell.getColumn() - 1, cell.getRow() + 1);
            Cell temprw = new Cell(cell.getColumn() + 1, cell.getRow() + 1);
            if (cell.getRow() != 7) {
                if (this.getState().getField().get(tempo) == null) {
                    set.add(tempo);
                }
                if (cell.getColumn() != 0) {
                    if (this.getState().getField().get(templw) != null) {
                        if (this.getState().getField().get(templw).getPlayer() == Player.BLACK) {
                            set.add(templw);
                        }
                    }
                }
                if (cell.getColumn() != 7) {
                    if (this.getState().getField().get(temprw) != null) {
                        if (this.getState().getField().get(temprw).getPlayer() == Player.BLACK) {
                            set.add(temprw);
                        }
                    }
                }

            }
            if (cell.getRow() == 0) {
                if (this.getState().getField().get(tempo2) == null) {
                    set.add(tempo2);
                }
            }
        }
        return set;
    }


    /**
     * In this function it will calculate the value every step to predict the possibilities. For the Node which is a pawn of
     * machine it would give the max. value back, and for the human, it will give back the min. value.
     *
     * @param deepth It's the deepth, in which the Node is.
     * @param player It is the machine pawn for this chess.
     * @return a " Pawnposition" type value. Which includes not only the value of this point but also the coordinate of the best pawn
     * of it.
     */
    @Override
    public Pawnposition minmax(int deepth, Player player) {
        double temp;
        double[] sum = new double[]{-2000, -2000, -2000, -2000, -2000, -2000, -2000, -2000};
        Cell[] positionf = new Cell[8];
        Cell[] positiont = new Cell[8];
        int checkpawn = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell check = new Cell(i, j);
                if (this.getState().getField().get(check) != null && this.getState().getField().get(check).getPlayer() == this.state.getCurrentPlayer()) {
                    Chess copy = new Chess();
                    copy = this.copychess();
                    for (Cell cell : this.getPossibleMovesForPawn(check)) {
                        if (copy.getState().getField().get(check) != null && this.getState().getField().get(check).getPlayer() == copy.getState().getCurrentPlayer()) {
                            double ifwin = copy.valuev(player, deepth);
                            temp = copy.value(player) + copy.valuec(player) + copy.valuei(player) + ifwin;
                            copy.move(check, cell);
                            //copy.print();
                            if (deepth == 0) {
                                if ((ifwin != 0 && j == 1 && this.getState().getCurrentPlayer() == Player.BLACK) ||
                                        (ifwin != 0 && j == 6 && this.state.getCurrentPlayer() == Player.WHITE)) {
                                    sum[checkpawn] = temp;
                                    positionf[checkpawn] = check;
                                    positiont[checkpawn] = cell;
                                    Cell endf = positionf[checkpawn];
                                    Cell endt = positiont[checkpawn];
                                    Pawnposition result = new Pawnposition(endf, sum[checkpawn], endt);
                                    return result;
                                } else {
                                    if (copy.minmax(deepth + 1, player) != null && sum[checkpawn] <= temp + copy.minmax(deepth + 1, player).getresult()) {
                                        sum[checkpawn] = temp + copy.minmax(deepth + 1, player).getresult();
                                        positionf[checkpawn] = check;
                                        positiont[checkpawn] = cell;
                                    }
                                }
                            }
                            if (deepth == 2) {
                                if (ifwin == 0) {
                                    if (sum[checkpawn] <= temp) {
                                        sum[checkpawn] = temp;
                                        positionf[checkpawn] = check;
                                        positiont[checkpawn] = cell;
                                    }
                                } else {
                                    if ((j == 1 && this.getState().getCurrentPlayer() == Player.BLACK) ||
                                            (j == 6 && this.state.getCurrentPlayer() == Player.WHITE)) {
                                        sum[checkpawn] = copy.value(player) + copy.valuec(player) + copy.valuei(player) + ifwin;
                                        positionf[checkpawn] = check;
                                        positiont[checkpawn] = cell;
                                        Cell endf = positionf[checkpawn];
                                        Cell endt = positiont[checkpawn];
                                        Pawnposition result = new Pawnposition(endf, sum[checkpawn], endt);
                                        return result;
                                    }
                                }

                            }
                            if (deepth == 1) {
                                if (ifwin == 0 && copy.minmax(deepth + 1, player) != null) {
                                    if (sum[checkpawn] != -2000) {
                                        double temp1 = temp + copy.minmax(deepth + 1, player).getresult();
                                        if (sum[checkpawn] >= temp1) {
                                            sum[checkpawn] = temp1;
                                            positionf[checkpawn] = check;
                                            positiont[checkpawn] = cell;
                                        }
                                    } else {
                                        sum[checkpawn] = temp + copy.minmax(deepth + 1, player).getresult();
                                        positionf[checkpawn] = check;
                                        positiont[checkpawn] = cell;
                                    }
                                } else {
                                    if ((j == 1 && this.getState().getCurrentPlayer() == Player.BLACK) ||
                                            (j == 6 && this.state.getCurrentPlayer() == Player.WHITE)) {
                                        sum[checkpawn] = temp;
                                        positionf[checkpawn] = check;
                                        positiont[checkpawn] = cell;
                                        Cell endf = positionf[i];
                                        Cell endt = positiont[i];
                                        Pawnposition result = new Pawnposition(endf, sum[checkpawn], endt);
                                        return result;
                                    }
                                }
                            }
                        }
                    }
                    checkpawn++;
                }
            }
        }
        if (/*deepth == 0 || deepth == 2*/this.state.getCurrentPlayer() == player) {
            double sum1 = -2000;
            Cell endf = null;
            Cell endt = null;
            for (int i = 0; i < sum.length; i++) {
                if (sum[i] != (-2000)) {
                    if (sum1 <= sum[i]) {
                        sum1 = sum[i];
                        endf = positionf[i];
                        endt = positiont[i];
                    }
                }
            }
            Pawnposition result = new Pawnposition(endf, sum1, endt);
            return result;
        }
        if (/*deepth == 1*/this.state.getCurrentPlayer() != player) {
            double sum1 = 2000;
            Cell endf = null;
            Cell endt = null;
            for (int i = 0; i < sum.length; i++) {
                if (sum[i] != (-2000)) {
                    if (sum1 >= sum[i]) {
                        sum1 = sum[i];
                        endf = positionf[i];
                        endt = positiont[i];
                    }
                }
            }
            Pawnposition result = new Pawnposition(endf, sum1, endt);
            return result;
        }
        return null;
    }

    /**
     * value
     *
     * @param player the machine side in the chess.
     * @return the value of N + D.
     */
    public double value(Player player) {
        double value = 0;
        double n = 0, nm = 0, nh = 0;
        double d = 0, dm = 0, dh = 0, numm = 0, numh = 0;
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                Cell temp = new Cell(i, j);
                if (this.getState().getField().get(temp) != null) {
                    if (this.getState().getField().get(temp).getPlayer() == player) {
                        nm = nm + 1;
                        numm = numm + 1;
                    } else {
                        nh = nh + 1;
                        numh = numh + 1;
                    }
                }
            }
            dm = dm + numm * (7 - j);
            dh = dh + numh * j;
            d = dm - 1.5 * dh;
            numm = 0;
            numh = 0;
        }
        n = nm - 1.5 * nh;
        value = n + d;
        return value;
    }

    /**
     * valuei
     *
     * @param player the machine side in the chess.
     * @return the value of I.
     */
    public double valuei(Player player) {
        double value = 0;
        double isum = 0, im = 0, ih = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell temp = new Cell(i, j);
                if (i > 0 && i < 7 && j > 0 && j < 7) {
                    if (this.getState().getField().get(temp) != null) {
                        Cell upright = new Cell(i + 1, j + 1);
                        Cell right = new Cell(i + 1, j);
                        Cell downright = new Cell(i + 1, j - 1);
                        Cell down = new Cell(i, j - 1);
                        Cell downleft = new Cell(i - 1, j - 1);
                        Cell left = new Cell(i - 1, j);
                        Cell upleft = new Cell(i - 1, j + 1);
                        Cell up = new Cell(i, j + 1);
                        if (this.getState().getField().get(upright) == null &&
                                this.getState().getField().get(right) == null &&
                                this.getState().getField().get(downleft) == null &&
                                this.getState().getField().get(downright) == null &&
                                this.getState().getField().get(down) == null &&
                                this.getState().getField().get(left) == null &&
                                this.getState().getField().get(upleft) == null &&
                                this.getState().getField().get(up) == null) {

                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }
                if (i > 0 && j == 0 && i < 7) {
                    Cell upright = new Cell(i + 1, j + 1);
                    Cell right = new Cell(i + 1, j);
                    Cell left = new Cell(i - 1, j);
                    Cell upleft = new Cell(i - 1, j + 1);
                    Cell up = new Cell(i, j + 1);
                    if (this.getState().getField().get(upright) == null &&
                            this.getState().getField().get(right) == null &&
                            this.getState().getField().get(left) == null &&
                            this.getState().getField().get(upleft) == null &&
                            this.getState().getField().get(up) == null) {
                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }

                if (i > 0 && j == 7 && i < 7) {
                    Cell right = new Cell(i + 1, j);
                    Cell downright = new Cell(i + 1, j - 1);
                    Cell down = new Cell(i, j - 1);
                    Cell downleft = new Cell(i - 1, j - 1);
                    Cell left = new Cell(i - 1, j);
                    if (this.getState().getField().get(right) == null &&
                            this.getState().getField().get(downleft) == null &&
                            this.getState().getField().get(downright) == null &&
                            this.getState().getField().get(down) == null &&
                            this.getState().getField().get(left) == null) {

                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }

                if (i == 0 && j > 0 && j < 7) {
                    Cell upright = new Cell(i + 1, j + 1);
                    Cell right = new Cell(i + 1, j);
                    Cell downright = new Cell(i + 1, j - 1);
                    Cell down = new Cell(i, j - 1);
                    Cell up = new Cell(i, j + 1);
                    if (this.getState().getField().get(upright) == null &&
                            this.getState().getField().get(right) == null &&
                            this.getState().getField().get(downright) == null &&
                            this.getState().getField().get(down) == null &&
                            this.getState().getField().get(up) == null) {

                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }

                if (i == 7 && j > 0 && j < 7) {
                    Cell down = new Cell(i, j - 1);
                    Cell downleft = new Cell(i - 1, j - 1);
                    Cell left = new Cell(i - 1, j);
                    Cell upleft = new Cell(i - 1, j + 1);
                    Cell up = new Cell(i, j + 1);
                    if (this.getState().getField().get(downleft) == null &&
                            this.getState().getField().get(down) == null &&
                            this.getState().getField().get(left) == null &&
                            this.getState().getField().get(upleft) == null &&
                            this.getState().getField().get(up) == null) {

                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }

                if (i == 0 && j == 0) {
                    Cell up = new Cell(i, j + 1);
                    Cell upright = new Cell(i + 1, j + 1);
                    Cell right = new Cell(i + 1, j);
                    if (this.getState().getField().get(upright) == null &&
                            this.getState().getField().get(right) == null &&
                            this.getState().getField().get(up) == null) {

                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }

                if (i == 7 && j == 0) {
                    Cell up = new Cell(i, j + 1);
                    Cell left = new Cell(i - 1, j);
                    Cell upleft = new Cell(i - 1, j + 1);
                    if (this.getState().getField().get(left) == null &&
                            this.getState().getField().get(upleft) == null &&
                            this.getState().getField().get(up) == null) {

                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }
                if (i == 0 && j == 7) {
                    Cell right = new Cell(i + 1, j);
                    Cell downright = new Cell(i + 1, j - 1);
                    Cell down = new Cell(i, j - 1);
                    if (this.getState().getField().get(right) == null &&
                            this.getState().getField().get(downright) == null &&
                            this.getState().getField().get(down) == null) {
                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }
                if (i == 7 && j == 7) {
                    Cell left = new Cell(i - 1, j);
                    Cell downleft = new Cell(i - 1, j - 1);
                    Cell down = new Cell(i, j - 1);
                    if (this.getState().getField().get(downleft) == null &&
                            this.getState().getField().get(down) == null &&
                            this.getState().getField().get(left) == null) {

                        if (this.getState().getField().get(temp) != null) {
                            if (this.getState().getField().get(temp).getPlayer() == player) {
                                im = im + 1;
                            } else {
                                ih = ih + 1;
                            }
                        }
                    }
                }

            }
        }
        value = ih - 1.5 * im;
        return value;
    }

    /**
     * valuec
     *
     * @param player the machine side in the chess.
     * @return the value of C.
     */
    public double valuec(Player player) {
        double value = 0;
        double cm = 0, ch = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell temp = new Cell(i, j);
                if (this.getState().getField().get(temp) != null) {
                    if (this.getState().getField().get(temp).getPlayer() == player) {
                        if (player == Player.BLACK) {
                            if (i > 0 && i < 7 && j > 0 && j < 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                Cell upright = new Cell(i + 1, j + 1);
                                if (((this.getState().getField().get(downleft) != null && this.getState().getField().get(downleft).getPlayer() == Player.WHITE) ||
                                        (this.getState().getField().get(downright) != null && this.getState().getField().get(downright).getPlayer() == Player.WHITE))
                                        ) {
                                    if (this.getState().getField().get(upleft) == null &&
                                            this.getState().getField().get(upright) == null) {
                                        cm = cm + 1;
                                    }
                                }
                            }
                            if (i != 0 && i != 7 && j == 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                Cell downleft = new Cell(i - 1, j - 1);
                                if ((this.getState().getField().get(downleft) != null && this.getState().getField().get(downleft).getPlayer() != null && this.getState().getField().get(downleft).getPlayer() != player)
                                        || (this.getState().getField().get(downright) != null && this.getState().getField().get(downright).getPlayer() != null && this.getState().getField().get(downright).getPlayer() != player)) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 0 && j > 0 && j != 7) {
                                Cell upright = new Cell(i + 1, j + 1);
                                Cell downright = new Cell(i + 1, j - 1);
                                if (this.getState().getField().get(downright) != null && this.getState().getField().get(downright).getPlayer() != null &&
                                        this.getState().getField().get(downright).getPlayer() != player &&
                                        this.getState().getField().get(upright) == null) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 7 && j > 0 && j != 7) {
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                if (this.getState().getField().get(downleft) != null && this.getState().getField().get(downleft).getPlayer() != null &&
                                        this.getState().getField().get(downleft).getPlayer() != player &&
                                        this.getState().getField().get(upleft) == null) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 0 && j == 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                if (this.getState().getField().get(downright) != null &&
                                        this.getState().getField().get(downright).getPlayer() != player) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 7 && j == 7) {
                                Cell downleft = new Cell(i - 1, j - 1);
                                if (this.getState().getField().get(downleft) != null &&
                                        this.getState().getField().get(downleft).getPlayer() != player) {
                                    cm = cm + 1;
                                }
                            }

                        } else {
                            if (i > 0 && i < 7 && j > 0 && j < 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                Cell upright = new Cell(i + 1, j + 1);
                                if ((this.getState().getField().get(upleft) != null && this.getState().getField().get(upleft).getPlayer() == Player.BLACK) ||
                                        (this.getState().getField().get(upright) != null && this.getState().getField().get(upright).getPlayer() == Player.BLACK)
                                        ) {
                                    if (this.getState().getField().get(downleft) == null &&
                                            this.getState().getField().get(downright) == null) {
                                        cm = cm + 1;
                                    }
                                }
                            }
                            if (i != 0 && i != 7 && j == 0) {
                                Cell upright = new Cell(i + 1, j + 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                if ((this.getState().getField().get(upleft) != null && this.getState().getField().get(upleft).getPlayer() != null && this.getState().getField().get(upleft).getPlayer() != player)
                                        || (this.getState().getField().get(upright) != null && this.getState().getField().get(upright).getPlayer() != null && this.getState().getField().get(upright).getPlayer() != player)) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 0 && j > 0 && j != 7) {
                                Cell upright = new Cell(i + 1, j + 1);
                                Cell downright = new Cell(i + 1, j - 1);
                                if (this.getState().getField().get(upright) != null && this.getState().getField().get(upright).getPlayer() != null &&
                                        this.getState().getField().get(upright).getPlayer() != player &&
                                        this.getState().getField().get(downright) == null) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 7 && j > 0 && j != 7) {
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                if (this.getState().getField().get(upleft) != null && this.getState().getField().get(upleft).getPlayer() != null &&
                                        this.getState().getField().get(upleft).getPlayer() != player &&
                                        this.getState().getField().get(downleft) == null) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 0 && j == 0) {
                                Cell upright = new Cell(i + 1, j + 1);
                                if (this.getState().getField().get(upright) != null &&
                                        this.getState().getField().get(upright).getPlayer() != player) {
                                    cm = cm + 1;
                                }
                            }
                            if (i == 7 && j == 0) {
                                Cell upleft = new Cell(i - 1, j + 1);
                                if (this.getState().getField().get(upleft) != null &&
                                        this.getState().getField().get(upleft).getPlayer() != player) {
                                    cm = cm + 1;
                                }
                            }

                        }
                    } else {

                        //Here is the pawn not the same with player, so hier the pawn is black
                        if (player == Player.WHITE) {
                            if (i > 0 && i < 7 && j > 0 && j < 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                Cell upright = new Cell(i + 1, j + 1);
                                if ((this.getState().getField().get(downleft) != null && this.getState().getField().get(downleft).getPlayer() == Player.WHITE) ||
                                        (this.getState().getField().get(downright) != null && this.getState().getField().get(downright).getPlayer() == Player.WHITE)
                                        ) {
                                    if (this.getState().getField().get(upleft) == null &&
                                            this.getState().getField().get(upright) == null) {
                                        ch = ch + 1;
                                    }
                                }
                            }
                            if (i != 0 && i != 7 && j == 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                Cell downleft = new Cell(i - 1, j - 1);
                                if ((this.getState().getField().get(downleft) != null && this.getState().getField().get(downleft).getPlayer() != null && this.getState().getField().get(downleft).getPlayer() != player.changePlayer())
                                        || (this.getState().getField().get(downright) != null && this.getState().getField().get(downright).getPlayer() != null && this.getState().getField().get(downright).getPlayer() != player.changePlayer())) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 0 && j > 0 && j != 7) {
                                Cell upright = new Cell(i + 1, j + 1);
                                Cell downright = new Cell(i + 1, j - 1);
                                if (this.getState().getField().get(downright) != null &&
                                        this.getState().getField().get(downright).getPlayer() != player.changePlayer() &&
                                        this.getState().getField().get(upright) == null) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 7 && j > 0 && j != 7) {
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                if (this.getState().getField().get(downleft) != null &&
                                        this.getState().getField().get(downleft).getPlayer() != player.changePlayer() &&
                                        this.getState().getField().get(upleft) == null) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 0 && j == 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                if (this.getState().getField().get(downright) != null &&
                                        this.getState().getField().get(downright).getPlayer() != player.changePlayer()) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 7 && j == 7) {
                                Cell downleft = new Cell(i - 1, j - 1);
                                if (this.getState().getField().get(downleft) != null &&
                                        this.getState().getField().get(downleft).getPlayer() != player.changePlayer()) {
                                    ch = ch + 1;
                                }
                            }

                        } else {
                            //Here the Pawn is white.
                            if (i > 0 && i < 7 && j > 0 && j < 7) {
                                Cell downright = new Cell(i + 1, j - 1);
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                Cell upright = new Cell(i + 1, j + 1);
                                if ((this.getState().getField().get(upleft) != null && this.getState().getField().get(upleft).getPlayer() == Player.BLACK) ||
                                        (this.getState().getField().get(upright) != null && this.getState().getField().get(upright).getPlayer() == Player.BLACK)
                                        ) {
                                    if (this.getState().getField().get(downleft) == null &&
                                            this.getState().getField().get(downright) == null) {
                                        ch = ch + 1;
                                    }
                                }
                            }
                            if (i != 0 && i != 7 && j == 0) {
                                Cell upright = new Cell(i + 1, j + 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                if ((this.getState().getField().get(upleft) != null && this.getState().getField().get(upleft).getPlayer() != player.changePlayer())
                                        || (this.getState().getField().get(upright) != null && this.getState().getField().get(upright).getPlayer() != player.changePlayer())) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 0 && j > 0 && j != 7) {
                                Cell upright = new Cell(i + 1, j + 1);
                                Cell downright = new Cell(i + 1, j - 1);
                                if (this.getState().getField() != null && this.getState().getField().get(upright) != null &&
                                        this.getState().getField().get(upright).getPlayer() != player.changePlayer() &&
                                        this.getState().getField().get(downright) == null) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 7 && j > 0 && j != 7) {
                                Cell downleft = new Cell(i - 1, j - 1);
                                Cell upleft = new Cell(i - 1, j + 1);
                                if (this.getState().getField().get(upleft) != null &&
                                        this.getState().getField().get(upleft).getPlayer() != player.changePlayer() &&
                                        this.getState().getField().get(downleft) == null) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 0 && j == 0) {
                                Cell upright = new Cell(i + 1, j + 1);
                                if (this.getState().getField().get(upright) != null &&
                                        this.getState().getField().get(upright).getPlayer() != player.changePlayer()) {
                                    ch = ch + 1;
                                }
                            }
                            if (i == 7 && j == 0) {
                                Cell upleft = new Cell(i - 1, j + 1);
                                if (this.getState().getField().get(upleft) != null &&
                                        this.getState().getField().get(upleft).getPlayer() != player.changePlayer()) {
                                    ch = ch + 1;
                                }
                            }

                        }
                    }
                }
            }
        }
        value = ch - 1.5 * cm;
        return value;
    }

    /**
     * valuev
     *
     * @param player the machine side in the chess.
     * @return the value of V.
     */
    public double valuev(Player player, int deepth) {
        double vm = 0, vh = 0, value = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Cell temp = new Cell(i, j);
                if (this.getState().getField().get(temp) != null && this.getState().getField().get(temp).getPlayer() == player) {
                    for (Cell cell : this.getPossibleMovesForPawn(temp)) {
                        if (cell.getRow() == 0 || cell.getRow() == 7) {
                            if (player == Player.BLACK) {
                                if (j == 1) {
                                    vm = 5000 / (double) (deepth + 1);
                                }
                            }
                            if (player == Player.WHITE) {
                                if (j == 6) {
                                    vm = 5000 / (double) (deepth + 1);
                                }
                            }
                            if (this.getState().getField().get(temp) != null && this.getState().getField().get(temp).getPlayer() == player.changePlayer()) {
                                //Pawn is Black and not of maschine
                                if (player == Player.WHITE) {
                                    if (j == 6) {
                                        vh = 5000 / (double) (deepth + 1);
                                    }
                                }
                                if (player == Player.BLACK) {
                                    if (j == 1) {
                                        vh = 5000 / (double) (deepth + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        value = vm - 1.5 * vh;
        return value;
    }

@Override
    public boolean checkifsame(GameState a, GameState b) {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Cell temp = new Cell(i, j);
            if (a.getField().get(temp) != null && b.getField().get(temp) != null) {
                if (a.getField().get(temp).getPlayer().toString() != b.getField().get(temp).getPlayer().toString()) {
                    System.out.println("i,j:" + i + j);
                    return false;
                }
            } else if (a.getField().get(temp) == null && b.getField().get(temp) != null) {
                return false;
            } else if (a.getField().get(temp) != null && b.getField().get(temp) == null) {
                return false;
            }
        }
    }
    return true;
  }
}












