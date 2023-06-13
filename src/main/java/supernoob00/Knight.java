package supernoob00;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knight extends Piece {

    public Knight(Color color, int value) {
        super(color, value);
    }

    @Override
    public Set<Position> getLegalMoves(Board board) {
        Position start = board.getPosition(this);
        Set<Position> legalMoves = new HashSet<Position>();
        // Assuming none are out of bounds, a knight can move to eight possible positions
        for (Direction dir1 : List.of(Direction.UP, Direction.DOWN)) {
            for (Direction dir2 : List.of(Direction.LEFT, Direction.RIGHT)) {
                Position dest1 = start.move(dir1, 2).move(dir2);
                Position dest2 = start.move(dir2, 2).move(dir1);

                Piece pieceAtDest1 = board.getPiece(dest1);
                Piece pieceAtDest2 = board.getPiece(dest2);

                if (dest1 != Position.INVALID_POSITION && pieceAtDest1.color != this.color) {
                    legalMoves.add(dest1);
                }
                if (dest2 != Position.INVALID_POSITION && pieceAtDest2.color != this.color) {
                    legalMoves.add(dest2);
                }
            }
        }
        return legalMoves;
    }

    @Override
    public boolean canMove(Position dest, Board board) {
        return false;
    }

    @Override
    public boolean sees(Piece piece, Board board) {
        return false;
    }
}
