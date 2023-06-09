package supernoob00;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {
    protected final Color color;
    protected final int value;

    public Piece(Color color, int value) {
        this.color = color;
        this.value = value;
    }
    
    public Color getColor() {
        return this.color;
    }

    public int getValue() {
        return this.value;
    }

    public abstract Set<Position> getLegalMoves(Board board);

    public boolean canMove(Position dest, Board board) {
        return getLegalMoves(board).contains(dest);
    };
}