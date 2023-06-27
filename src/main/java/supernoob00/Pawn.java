package supernoob00;

import java.util.*;

public class Pawn extends Piece implements Valued {
    public final static Pawn WHITE_PAWN = new Pawn(Color.WHITE);
    public final static Pawn BLACK_PAWN = new Pawn(Color.BLACK);

    public static Pawn getInstance(Color color) {
        return color == Color.WHITE ? WHITE_PAWN : BLACK_PAWN;
    }

    private int value;
    private Direction moveDirection;
    private Set<Direction> takeDirections;
    private int startRow;

    protected Pawn(Color color) {
        super(color, PieceType.PAWN);
        this.value = 1;
        if (color == Color.WHITE) {
            moveDirection = Direction.UP;
            takeDirections = Set.of(Direction.UP_LEFT, Direction.UP_RIGHT);
            startRow = 6;
        } else if (color == Color.BLACK) {
            moveDirection = Direction.DOWN;
            takeDirections = Set.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
            startRow = 1;
        }
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public Direction getMoveDirection() {
        return this.moveDirection;
    }

    @Override
    public Set<Move> pseudoLegalMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<Move>();
        moves.addAll(getForwardMoves(start, board));
        moves.addAll(getDiagonalMoves(start, board));
        moves.addAll(getEnPassantMoves(start, board));
        return moves;
    }

    // Does not check for any possible en passant move
    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        for (Direction takeDir : this.takeDirections) {
            if (start.hasNext(takeDir)
                    && threatened == start.move(takeDir)
                    && !friendly(board.getPiece(threatened))) {
                return true;
            }
        }
        return false;
    }

    private Set<Move> getForwardMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<Move>();
        Direction moveDir = this.moveDirection;
        Position current = start;

        int allowed = (start.getRow() == this.startRow) ? 2 : 1;

        while (current.hasNext(moveDir)
                && start.rowDistance(current) < allowed) {
            Position next = current.move(moveDir);
            Piece nextPiece = board.getPiece(next);

            if (nextPiece != null) {
                break;
            }

            Move move = new Move(start, next);
            moves.add(move);

            current = next;
        }
        return moves;
    }

    private Set<Move> getDiagonalMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<Move>();
        for (Direction takeDir : this.takeDirections) {
            if (!start.hasNext(takeDir)) {
                continue;
            }
            Position next = start.move(takeDir);
            Piece nextPiece = board.getPiece(next);
            if (enemy(nextPiece)) {
                Move move = new Move(start, next);
                moves.add(move);
            }
        }
        return moves;
    }

    private Set<Move> getEnPassantMoves(Position start, Board board) {
        Set<Move> moves = new HashSet<Move>();
        for (Direction takeDir : this.takeDirections) {
            if (!start.hasNext(takeDir)) {
                continue;
            }
            Position next = start.move(takeDir);
            PawnTrail trail = board.getPawnTrail(next);
            if (enemy(trail)) {
                Position takenPawnPos = next.move(this.moveDirection.getOpposite());
                Move move = new Move(start, next, Move.EN_PASSANT);
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "P" : "p";
    }
}
