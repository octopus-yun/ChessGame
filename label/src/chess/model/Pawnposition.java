package chess.model;

/**
 *This is the class I have made myself.
 * I want to set connections between the value of the best pawns and the coordinate of it.
 * So if a variable who's class is Pawnposition is returned, it gives not only the calculated value
 * but also the best possibility for the chess.
 */
public class Pawnposition {
    private Cell cellf;
    private Cell cellt;
    double result;

    Pawnposition(Cell f, double r,Cell t) {
        this.cellf = f;
        this.cellt = t;
        this.result = r;
    }

    public Cell getcellf() {
        return this.cellf;
    }

    public double getresult(){
        return this.result;
    }
    public Cell getcellt() {
        return this.cellt;
    }
}
