package supernoob00;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SliderPiece extends Piece implements Moveable {
    protected Set<Direction> moveDirections;

    public SliderPiece(Color color, int value) {
        super(color, value);
    }

    @Override
    public Set<Board> getLegalMoves(Position start, Board board) {
        Set<Board> allMoves = new HashSet<Board>();
        for (Direction dir : this.moveDirections) {
            List<Board> moves = directionalMoves(start, dir, board);
            allMoves.addAll(moves);
        }
        Set<Board> legalMoves = allMoves.stream()
                .filter(brd -> King.inCheck(this.color, brd))
                .collect(Collectors.toSet());
        return legalMoves;
    }

    @Override
    public boolean canMove(Position start, Board before, Board after) {
        Set<Board> legalMoves = getLegalMoves(start, before);
        return legalMoves.contains(after);
    }

    // moves in a given direction
    protected List<Board> directionalMoves(Position start, Direction dir, Board board) {
        List<Board> moves = new ArrayList<Board>();
        Position current = start;
        while (current.hasNext(dir)) {
            Position next = current.move(dir);
            Piece nextPiece = board.getPiece(next);

            if (nextPiece != null && this.color == nextPiece.color) {
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
            if (current != threatened && board.getPiece(current) != null) {
                return false;
            }
        }
        return true;
    }
}
