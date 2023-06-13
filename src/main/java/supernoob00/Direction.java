package supernoob00;

import java.util.Set;

public enum Direction {
    UP(),
    DOWN(),
    LEFT(),
    RIGHT(),
    UP_LEFT(),
    UP_RIGHT(),
    DOWN_LEFT(),
    DOWN_RIGHT(),
    // not one of the eight standard directions
    OTHER();

    private static void setOppositeHelper(Direction dir1, Direction dir2) {
        dir1.opposite = dir2;
        dir2.opposite = dir1;
    }

    // set opposite directions
    static {
        setOppositeHelper(UP, DOWN);
        setOppositeHelper(LEFT, RIGHT);
        setOppositeHelper(UP_LEFT, DOWN_RIGHT);
        setOppositeHelper(UP_RIGHT, DOWN_LEFT);
        OTHER.opposite = OTHER;
    }

    private Direction opposite;

    public static Set<Direction> getAll() {
        return Set.of(
                UP,
                DOWN,
                LEFT,
                RIGHT,
                UP_LEFT,
                UP_RIGHT,
                DOWN_RIGHT,
                DOWN_LEFT
        );
    }

    public static Set<Direction> getCardinal() {
        return Set.of(UP, DOWN, LEFT, RIGHT);
    }

    public static Set<Direction> getOrdinal() {
        return Set.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
    }

    public Direction opposite() {
        return this.opposite;
    }
}
