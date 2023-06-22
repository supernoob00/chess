package supernoob00;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece implements Valued {
    public final static Knight WHITE_KNIGHT = new Knight(Color.WHITE);
    public final static Knight BLACK_KNIGHT = new Knight(Color.BLACK);

    public static Knight getInstance(Color color) {
        return color == Color.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT;
    }

    private final int value;

    protected Knight(Color color) {
        super(color, PieceType.KNIGHT);
        this.value = 3;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public Set<Move> pseudoLegalMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<Move>();
        Set<Direction> vertical = Set.of(Direction.UP, Direction.DOWN);
        Set<Direction> horizontal = Set.of(Direction.LEFT, Direction.RIGHT);
        for (Direction vDir : vertical) {
            for (Direction hDir : horizontal) {
                Position firstDest = start.move(vDir, 2).move(hDir, 1);
                Position secondDest = start.move(hDir, 2).move(vDir, 1);

                if (firstDest != Position.INVALID_POSITION && !friendly(board.getPiece(firstDest))) {
                    moves.add(new Move(start, firstDest));
                }
                if (secondDest != Position.INVALID_POSITION && !friendly(board.getPiece(secondDest))) {
                    moves.add(new Move(start, secondDest));
                }
            }
        }
        return moves;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Piece piece = board.getPiece(threatened);
        int rowDist = start.rowDistance(threatened);
        int colDist = start.colDistance(threatened);
        return ((rowDist == 2 && colDist == 1) || (colDist == 2 && rowDist == 1))
                && !friendly(piece);
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "N" : "n";
    }
}
