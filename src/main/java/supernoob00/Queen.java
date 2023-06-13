package supernoob00;

import java.util.Set;

public class Queen extends SliderPiece {
    public Queen(Color color, int value) {
        super(color, 8);
        this.moveDirections = Direction.getAll();
    }
}
