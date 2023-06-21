package supernoob00;

import java.util.Objects;

public abstract class BoardObject {
    protected final Color color;

    public BoardObject(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }
}