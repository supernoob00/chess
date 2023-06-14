package supernoob00;

import java.util.*;

public abstract class Slider extends Piece {
    protected Set<Direction> moveDirections;

    protected Slider(Color color) {
        super(color);
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

            if (friendly(nextPiece)) {
                break;
            }

            Board testBoard = new Board(board);
            testBoard.removePiece(start);
            testBoard.setPiece(next, this);

            moves.add(testBoard);
            current = next;
        }
        return moves;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Direction dir = start.directionOf(threatened);
        Piece threatenedPiece = board.getPiece(threatened);
        if (!this.moveDirections.contains(dir) || friendly(threatenedPiece)) {
            return false;
        }
        Position current = start.move(dir);
        while (current != threatened) {
            if (board.getPiece(current) != null) {
                return false;
            }
            current = current.move(dir);
        }
        return true;
    }
}
