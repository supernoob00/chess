package supernoob00;

import java.util.HashSet;
import java.util.Set;

public abstract class Slider extends Piece {
    protected Set<Direction> directions;

    public Slider(Color color, int value) {
        super(color, value);
    }

    @Override
    public Set<Position> getLegalMoves(Board board) {
        Set<Position> allLegalMoves = new HashSet<Position>();
        for (Direction dir : this.directions) {
            Set<Position> moves = movesUntilStopped(dir, board);
            allLegalMoves.addAll(moves);
        }
        return allLegalMoves;
    }

    @Override
    public boolean canMove(Position dest, Board board) {
        Set<Position> legalMoves = getLegalMoves(board);
        return legalMoves.contains(dest);
    }

    // A piece can 'see' another position if it could hypothetically move to it, regardless
    // of whether that other position is occupied
    public boolean sees(Position otherPos, Board board) {
        Position myPos = board.getPosition(this);
        Direction dir = myPos.directionOf(otherPos);
        System.out.println(dir);
        if (!this.directions.contains(dir)) {
            return false;
        }
        Position current = myPos;
        while (current != otherPos) {
            current = current.move(dir);
            if (current != otherPos && board.getPiece(current) != null) {
                return false;
            }
        }
        return true;
    }

    public Set<Position> movesUntilStopped(Direction dir, Board board) {
        Set<Position> moves = new HashSet<Position>();
        Position current = board.getPosition(this);
        while (current.hasNext(dir)) {
            Position nextPos = current.move(dir);
            Piece nextPiece = board.getPiece(nextPos);
            if (nextPiece != null && this.color == nextPiece.color) {
                break;
            }
            current = nextPos;
            moves.add(current);
            if (nextPiece != null && this.color != nextPiece.color) {
                break;
            }
        }
        return moves;
    }
}
