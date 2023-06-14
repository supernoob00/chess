package supernoob00;

import java.util.Objects;

public abstract class ChessObject {
    protected final Color color;

    public ChessObject(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessObject piece = (ChessObject) o;
        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}