package supernoob00;

import java.util.Set;
import java.util.function.BiConsumer;

public enum Direction {
    UP(),
    DOWN(),
    LEFT(),
    RIGHT(),
    UP_LEFT(),
    UP_RIGHT(),
    DOWN_LEFT(),
    DOWN_RIGHT(),
    OTHER(); // not one of the eight standard directions

    // set opposite directions
    static {
        BiConsumer<Direction, Direction> setOpposite = (Direction d, Direction d2) -> {
            d.opposite = d2;
            d2.opposite = d;
        };
        setOpposite.accept(UP, DOWN);
        setOpposite.accept(LEFT, RIGHT);
        setOpposite.accept(UP_LEFT, DOWN_RIGHT);
        setOpposite.accept(UP_RIGHT, DOWN_LEFT);
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

    public Direction getOpposite() {
        return this.opposite;
    }

    public boolean isParallel(Direction dir) {
        return (dir == this) || (dir.opposite == this);
    }
}
