package supernoob00;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public static boolean inCheck(Color color, Board board) {
        Position kingPos = board.getKingPosition(color);
        Color enemyColor = color.opposite();
        for (Position pos : board.getPiecePositions(enemyColor)) {
            Piece enemyPiece = board.getPiece(pos);
            if (enemyPiece.threatens(pos, kingPos, board)) {
                return true;
            }
        }
        return false;
    }

    public static boolean inCheckmate(Color color, Board board) {
        Position kingPos = board.getKingPosition(color);
        Piece king = board.getPiece(kingPos);
        Set<Board> legalMoves = king.getLegalMoves(kingPos, board);
        return (King.inCheck(color, board)) && (legalMoves.size() == 0);
    }

    public static boolean inStalemate(Color color, Board board) {
        Position kingPos = board.getKingPosition(color);
        Piece king = board.getPiece(kingPos);
        Set<Board> legalMoves = king.getLegalMoves(kingPos, board);
        return !(King.inCheck(color, board)) && (legalMoves.size() == 0);
    }

    private final Set<Direction> moveDirections;

    public King(Color color) {
        super(color);
        this.moveDirections = Direction.getAll();
    }

    @Override
    public Set<Board> pseudoLegalMoves(Position start, Board board) {
        Set<Board> testBoards = new HashSet<Board>();
        for (Direction dir : this.moveDirections) {
            if (!start.hasNext(dir)) {
                continue;
            }

            Position next = start.move(dir);
            Piece nextPiece = board.getPiece(next);

            if (friendly(nextPiece)) {
                continue;
            }

            Board testBoard = new Board(board);
            board.removePiece(start);
            board.setPiece(next, this);
            testBoards.add(testBoard);
        }
        return testBoards;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Piece threatenedPiece = board.getPiece(threatened);
        boolean withinDistance = start.rowDistance(threatened) <= 1
                && start.colDistance(threatened) <= 1;
        return !friendly(threatenedPiece) && withinDistance;
    }
}
