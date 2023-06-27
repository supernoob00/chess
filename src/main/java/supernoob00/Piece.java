package supernoob00;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece extends BoardObject {
    protected PieceType type;

    public Piece(Color color, PieceType type) {
        super(color);
        this.type = type;
    }

    public PieceType getType() {
        return this.type;
    }

    public Set<Move> getLegalMoves(Position start, Board board) {
        King myKing = King.getInstance(this.color);
        Position myKingPos = board.getPosition(myKing);
        Set<Move> legalMoves = pseudoLegalMoves(start, board)
                .stream()
                .filter(move -> {
                    Piece taken = board.getPiece(move.getEnd());
                    board.applyMove(move);
                    boolean legal = !myKing.inCheck(myKingPos, board);
                    board.revertMove(move, taken);
                    return legal;
                })
                .collect(Collectors.toSet());
        return legalMoves;
    }

    protected abstract Set<Move> pseudoLegalMoves(Position start, Board board);

    public abstract boolean threatens(Position start, Position threatened, Board board);

    public boolean friendly(BoardObject obj) {
        return (obj != null) && (obj.getColor() == this.color);
    }

    public boolean enemy(BoardObject obj) {
        return (obj != null) && (obj.getColor() != this.color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return type == piece.type && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}
