package supernoob00;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece extends ChessObject implements Movable {
    public Piece(Color color) {
        super(color);
    }

    @Override
    public Set<Board> getLegalMoves(Position start, Board board) {
        return pseudoLegalMoves(start, board).stream()
                .filter(brd -> King.inCheck(this.color, brd))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean canMove(Position start, Board before, Board after) {
        Set<Board> legalMoves = getLegalMoves(start, before);
        return legalMoves.contains(after);
    }

    public boolean friendly(ChessObject obj) {
        return (obj != null) && (obj.getColor() == this.color);
    }

    public boolean enemy(ChessObject obj) {
        return (obj != null) && (obj.getColor() != this.color);
    }
}
