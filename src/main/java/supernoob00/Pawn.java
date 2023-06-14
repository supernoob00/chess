package supernoob00;

import java.util.*;

public class Pawn extends Piece implements Valued {
    private int value;
    private Direction moveDirection;
    private Set<Direction> takeDirections;

    public Pawn(Color color) {
        super(color);
        this.value = 1;
        if (color == Color.WHITE) {
            moveDirection = Direction.UP;
            takeDirections = Set.of(Direction.UP_LEFT, Direction.UP_RIGHT);
        } else if (color == Color.BLACK) {
            moveDirection = Direction.DOWN;
            takeDirections = Set.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
        }
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public Set<Board> pseudoLegalMoves(Position start, Board board) {
        Set<Board> moves = new HashSet<>();
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

    private Set<Board> getForwardMoves(Position start, Board board) {
        Set<Board> testBoards = new HashSet<Board>();
        Direction moveDir = this.moveDirection;
        Position current = start;

        while (current.hasNext(moveDir)
                && start.rowDistance(current) <= 2) {
            Position next = current.move(moveDir);
            Piece nextPiece = board.getPiece(next);

            if (nextPiece != null) {
                break;
            }

            Board testBoard = new Board(board);
            testBoard.removePiece(start);
            testBoard.setPiece(next, this);
            testBoards.add(testBoard);

            current = next;
        }
        return testBoards;
    }

    private Set<Board> getDiagonalMoves(Position start, Board board) {
        Set<Board> testBoards = new HashSet<Board>();
        for (Direction takeDir : this.takeDirections) {
            if (!start.hasNext(takeDir)) {
                continue;
            }
            Position next = start.move(takeDir);
            Piece piece = board.getPiece(next);
            if (enemy(piece)) {
                Board testBoard = new Board(board);
                testBoard.removePiece(start);
                testBoard.setPiece(next, this);
                testBoards.add(testBoard);
            }
        }
        return testBoards;
    }

    private Set<Board> getEnPassantMoves(Position start, Board board) {
        Set<Board> testBoards = new HashSet<Board>();
        for (Direction takeDir : this.takeDirections) {
            if (!start.hasNext(takeDir)) {
                continue;
            }
            Position next = start.move(takeDir);
            PawnTrail trail = board.getPawnTrail(next);
            if (enemy(trail)) {
                Position pawnToTake = next.move(this.moveDirection.opposite());
                Board testBoard = new Board(board);
                board.removePieces(start, pawnToTake);
                board.setPiece(next, this);
                testBoards.add(testBoard);
            }
        }
        return testBoards;
    }
}
