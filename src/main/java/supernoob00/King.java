package supernoob00;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public final static King WHITE_KING = new King(Color.WHITE);
    public final static King BLACK_KING = new King(Color.BLACK);

    public static King getInstance(Color color) {
        return color == Color.WHITE ? WHITE_KING : BLACK_KING;
    }

    public static final Position WHITE_START_POS = Position.get("e1");
    public static final Position BLACK_START_POS = Position.get("e8");

    public static Position getStart(Color color) {
        return (color == Color.WHITE) ? WHITE_START_POS : BLACK_START_POS;
    }

    private final Set<Direction> moveDirections;

    protected King(Color color) {
        super(color, PieceType.KING);
        this.moveDirections = Direction.getAll();
    }

    @Override
    public Set<Move> pseudoLegalMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<Move>();
        for (Direction dir : this.moveDirections) {
            if (!start.hasNext(dir)) {
                continue;
            }
            Position next = start.move(dir);
            Piece nextPiece = board.getPiece(next);

            if (friendly(nextPiece)) {
                continue;
            }
            Move move = new Move.Builder(start, next, board).build();
            moves.add(move);
        }
        return moves;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Piece threatenedPiece = board.getPiece(threatened);
        boolean withinDistance = start.rowDistance(threatened) <= 1
                && start.colDistance(threatened) <= 1;
        return !friendly(threatenedPiece) && withinDistance;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "K" : "k";
    }
}
