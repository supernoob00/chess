package supernoob00;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color, 1);
    }

    @Override
    public Set<Position> getLegalMoves(Board board) {
        return null;
    }

    @Override
    public boolean canMove(Position dest, Board board) {
        return false;
    }

    /*
    @Override
    public Set<Position> getLegalMoves(Position current, Board board) {
        Set<Position> moves = new HashSet<Position>();
        while (current.hasNext(current :: up) && canTake(
                current,
                current.up(),
                board.getPiece(current.up()))) {
            current = current.up();
            moves.add(current);
        }
        return moves;
    } */
    
    public String toString() {
        return "P";
    }
}
