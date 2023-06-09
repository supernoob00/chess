package supernoob00;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Slider {
    public Rook(Color color) {
        super(color, 5);
        this.directions = Direction.getCardinal();
    }

    // A more performant implementation of canMove that does not check all legal moves
    public boolean canMoveLite(Position dest, Board board) {
        boolean canSee = sees(dest, board);
        Piece pieceAtDest = board.getPiece(dest);
        boolean validDest = (pieceAtDest == null || pieceAtDest.getColor() != this.color);
        return (canSee && validDest);
    }
}
