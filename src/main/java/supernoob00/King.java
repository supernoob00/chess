package supernoob00;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class King extends MovablePiece {
    public static boolean inCheck(Color color, Board board) {
        Position kingPos = board.getKingPosition(color);
        Color enemyColor = color.opposite();
        for (Position pos : board.getPiecePositions(enemyColor)) {
            Piece enemyPiece = board.getPiece(pos);
            if (enemyPiece.canTake(kingPos, board)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inCheckmate(Color color, Board board) {

    }

    public static boolean inStalemate(Color color, Board board) {

    }

    private Set<Direction> moveDirections;

    public King(Color color, int value) {
        super(color, -1);
        this.moveDirections = Direction.getAll();
    }

    @Override
    public Set<Board> pseudoLegalMoves(Position start, Board board) {
        Position next;
        for (Direction dir : this.moveDirections) {
            if (!start.hasNext(dir)) {
                continue;
            }
            next = start.move(dir);
            Piece nextPiece = board.getPiece(next);

            Set<Position> removal = new HashSet<Position>(1);
            Map<Position, Piece> placement = new HashMap<Position, Piece>(1);

            removal.add(start);

            if (nextPiece == null) {
                placement.put(next, this);
                Board testBoard = board.getNewBoard(removal, )
            }

            else
        }
    }

    @Override
    public boolean canMove(Position start, Board before, Board after) {
        return false;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        return false;
    }
}
