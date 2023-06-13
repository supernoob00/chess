package supernoob00;

import java.util.Set;

public class King extends Piece implements Moveable {
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

    public Set<Board> allMoves(Position start, Board board) {
        Position next;
        for (Direction dir : this.moveDirections) {
            if (!start.hasNext(dir)) {
                break;
            }
            next = start.move(dir);
            Piece nextPiece = board.getPiece(next);
            if (nextPiece == null || nextPiece.getColor() != this.color) {
                Board testBoard = board.getNewBoard()
            }
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
