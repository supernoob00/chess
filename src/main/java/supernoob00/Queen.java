package supernoob00;

import java.util.Set;

public class Queen extends Slider {
    public Queen(Color color, int value) {
        super(color, 8);
        Set<Direction> dirs = Direction.getOrdinal();
        dirs.addAll(Direction.getCardinal());
        this.directions = dirs;
    }
}
