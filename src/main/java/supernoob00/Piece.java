package supernoob00;

import java.util.Objects;

public abstract class Piece {
    protected final Color color;
    protected final int value;

    public Piece(Color color, int value) {
        this.color = color;
        this.value = value;
    }
    
    public Color getColor() {
        return this.color;
    }

    public int getValue() {
        return this.value;
    }

    public boolean friendlyPiece(Piece piece) {
        return (piece != null) && (piece.getColor() == this.color);
    }

    public boolean enemyPiece(Piece piece) {
        return (piece != null) && (piece.getColor() != this.color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return value == piece.value && color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }
}