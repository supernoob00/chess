package supernoob00;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class MovablePiece extends Piece implements Movable {
    public MovablePiece(Color color, int value) {
        super(color, value);
    }

    @Override
    public Set<Board> getLegalMoves(Position start, Board board) {
        return pseudoLegalMoves(start, board).stream()
                .filter(brd -> King.inCheck(this.color, brd))
                .collect(Collectors.toSet());
    }
}
