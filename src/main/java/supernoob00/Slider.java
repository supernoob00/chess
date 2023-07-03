package supernoob00;

import java.util.*;

public abstract class Slider extends Piece {
    protected Set<Direction> moveDirections;

    protected Slider(Color color, PieceType type) {
        super(color, type);
    }

    @Override
    public Set<Move> pseudoLegalMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<>();
        for (Direction dir : this.moveDirections) {
            Set<Move> dirMoves = directionalMoves(start, dir, board);
            moves.addAll(dirMoves);
        }
        return moves;
    }

    // TODO: clean up logic
    protected Set<Move> directionalMoves(
            Position start, Direction dir, Board board) {

        Position current = start;
        Set<Move> moves = new HashSet<>();
        while (current.hasNext(dir)) {
            current = current.move(dir);
            Piece currentPiece = board.getPiece(current);

            if (friendly(currentPiece)) {
                break;
            }
            if (currentPiece != null && currentPiece.getType() == PieceType.KING) {
                System.out.println("ALERT!");
            }

            Move move = new Move.Builder(start, current, board).build();
            moves.add(move);

            if (enemy(currentPiece)) {
                break;
            }
        }
        return moves;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Direction dir = start.directionOf(threatened);
        Piece threatenedPiece = board.getPiece(threatened);
        return !friendly(threatenedPiece)
                && this.moveDirections.contains(dir)
                && board.hasLineOfSight(start, threatened);
    }
}
