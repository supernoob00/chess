package supernoob00;

import java.util.*;

public abstract class Slider extends MovablePiece {
    protected Set<Direction> moveDirections;

    public Slider(Color color, int value) {
        super(color, value);
    }

    @Override
    public boolean canMove(Position start, Board before, Board after) {
        Set<Board> legalMoves = getLegalMoves(start, before);
        return legalMoves.contains(after);
    }

    @Override
    public Set<Board> pseudoLegalMoves(Position start, Board board) {
        Set<Board> moves = new HashSet<Board>();
        for (Direction dir : this.moveDirections) {
            Set<Board> dirMoves = directionalMoves(start, dir, board);
            moves.addAll(dirMoves);
        }
        return moves;
    }

    // moves in a given direction
    protected Set<Board> directionalMoves(Position start, Direction dir, Board board) {
        Set<Board> moves = new HashSet<Board>();
        Position current = start;
        while (current.hasNext(dir)) {
            Position next = current.move(dir);
            Piece nextPiece = board.getPiece(next);

            if (friendlyPiece(nextPiece)) {
                break;
            }

            Set<Position> toRemove = new HashSet<Position>();
            toRemove.add(current);
            Map<Position, Piece> toPlace = Map.of(next, this);

            if (nextPiece != null) {
                toRemove.add(next);
            }

            Board testBoard = board.getNewBoard(toRemove, toPlace);
            moves.add(testBoard);
            current = next;
        }
        return moves;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Direction dir = start.directionOf(threatened);
        if (!this.moveDirections.contains(dir)) {
            return false;
        }
        Position current = start;
        while (current != threatened) {
            current = current.move(dir);
            if (current != threatened
                    && board.getPiece(current) != null
                    && board.getPiece(current).getColor() != this.color) {
                return false;
            }
        }
        return true;
    }
}
