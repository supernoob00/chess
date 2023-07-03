package supernoob00;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece {
    protected PieceType type;
    protected Color color;

    public Piece(Color color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public PieceType getType() {
        return this.type;
    }

    public Color getColor() {
        return this.color;
    }

    protected abstract Set<Move> pseudoLegalMoves(Position start, Board board);

    public abstract boolean threatens(Position start, Position threatened, Board board);

    public boolean friendly(Piece other) {
        return (other != null) && (other.color == this.color);
    }

    public boolean enemy(Piece other) {
        return (other != null) && (other.color != this.color);
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
