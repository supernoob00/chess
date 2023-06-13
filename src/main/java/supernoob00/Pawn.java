package supernoob00;

import java.util.*;

public class Pawn extends MovablePiece {
    private Direction moveDirection;
    private Set<Direction> takeDirections;

    public Pawn(Color color) {
        super(color, 1);
        if (color == Color.WHITE) {
            moveDirection = Direction.UP;
            takeDirections = Set.of(Direction.UP_LEFT, Direction.UP_RIGHT);
        } else if (color == Color.BLACK) {
            moveDirection = Direction.DOWN;
            takeDirections = Set.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
        }
    }

    @Override
    public Set<Board> pseudoLegalMoves(Position start, Board board) {
        Set<Board> moves = new HashSet<>();
        moves.addAll(getForwardMoves(start, board));
        moves.addAll(getDiagonalMoves(start, board));
        moves.addAll(getEnPassantMoves(start, board));
        return moves;
    }

    @Override
    public boolean canMove(Position start, Board before, Board after) {
        return false;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        return false;
    }

    private Set<Board> getForwardMoves(Position start, Board board) {
        Set<Board> testBoards = new HashSet<Board>();
        Direction moveDir = this.moveDirection;
        Position current = start;

        // forward movement
        while (current.hasNext(moveDir)
                && Math.abs(start.getRowIndex() - current.getRowIndex()) <= 2) {
            Position next = current.move(moveDir);
            Piece nextPiece = board.getPiece(next);

            if (nextPiece != null) {
                break;
            }

            Set<Position> toRemove = Set.of(current);
            Map<Position, Piece> toPlace = Map.of(next, this);
            Board testBoard = board.getNewBoard(toRemove, toPlace);

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
            if (piece != null && piece.getColor() != this.color) {
                Set<Position> toRemove = Set.of(start, next);
                Map<Position, Piece> toPlace = Map.of(next, this);
                Board testBoard = board.getNewBoard(toRemove, toPlace);
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
            Piece piece = board.getPawnTrail(next);
            if (piece != null && piece.getColor() != this.color) {
                Position pawnToTake = next.move(this.moveDirection.opposite());
                Set<Position> toRemove = Set.of(start, next, pawnToTake);
                Map<Position, Piece> toPlace = Map.of(next, this);
                Board testBoard = board.getNewBoard(toRemove, toPlace);
                testBoards.add(testBoard);
            }
        }
        return testBoards;
    }
}
