package supernoob00;

import java.util.Arrays;
import java.util.List;

public class Position {
    public final static int SIDE_COUNT = 8;
    public final static int LOWERCASE_START_ASCII = 97;

    public final static List<Position> POSITIONS = Arrays.asList(new Position[64]);
    static {
        int size = SIDE_COUNT * SIDE_COUNT;

        for (int row = 0; row < SIDE_COUNT; row++) {
            for (int col = 0; col < SIDE_COUNT; col++) {
                Position pos = new Position(row, col);
                POSITIONS.set(SIDE_COUNT * row + col, pos);
            }
        }
    }

    public final static Position INVALID_POSITION = new Position(-1, -1);

    public static boolean sameRow(Position pos1, Position pos2) {
        return pos1.row == pos2.row;
    }

    public static boolean sameCol(Position pos1, Position pos2) {
        return  pos1.col == pos2.col;
    }

    public static boolean sameDiag(Position pos1, Position pos2) {
        boolean sameSum = (pos1.row + pos1.col) == (pos2.row + pos2.col);
        boolean sameDifference = (pos1.row - pos2.row) == (pos1.col - pos2.col);
        return sameSum || sameDifference;
    }

    public static Position get(int row, int col) {
        boolean rowOutOfBounds = row < 0 || row > SIDE_COUNT - 1;
        boolean colOutOfBounds = col < 0 || col > SIDE_COUNT - 1;
        if (rowOutOfBounds || colOutOfBounds) {
            return INVALID_POSITION;
        }
        int index = SIDE_COUNT * row + col;
        return POSITIONS.get(index);
    }

    public static Position get(String coord) {
        char[] charArray = coord.toCharArray();
        int row = SIDE_COUNT - Character.getNumericValue(charArray[1]);
        int col = ((int) charArray[0]) - LOWERCASE_START_ASCII;
        return Position.get(row, col);
    }

    private final int row;
    private final int col;

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean validMove(int rowShift, int colShift) {
        boolean validRow = this.row + rowShift <= SIDE_COUNT;
        boolean validCol = this.col + colShift <= SIDE_COUNT;
        return validRow && validCol;
    }

    public static List<Position> getRowPositions(int row) {
        int start = SIDE_COUNT * row;
        int end = start + SIDE_COUNT;
        return POSITIONS.subList(start, end);
    }

    public Position move(int dx, int dy) {
        return Position.get(this.row + dx, this.col + dy);
    }

    public Position move(Direction dir, int count) {
        return switch (dir) {
            case UP -> this.up(count);
            case DOWN -> this.down(count);
            case RIGHT -> this.right(count);
            case LEFT -> this.left(count);
            case UP_LEFT -> this.up(count).left(count);
            case UP_RIGHT -> this.up(count).right(count);
            case DOWN_LEFT -> this.down(count).left(count);
            case DOWN_RIGHT -> this.down(count).right(count);
            case OTHER -> throw new IllegalArgumentException();
        };
    }

    public Position move(Direction dir) {
        return move(dir, 1);
    }

    public Position up(int count) {
        return move(-count, 0);
    }

    public Position left(int count) {
        return move(0, -count);
    }

    public Position down(int count) {
        return move(count, 0);
    }

    public Position right(int count) {
        return move(0, count);
    }

    public boolean hasNext(Direction dir, int dist) {
        return move(dir, dist) != INVALID_POSITION;
    }

    public boolean hasNext(Direction dir) {
        return move(dir) != INVALID_POSITION;
    }

    public int rowDistance(Position pos) {
        return Math.abs(pos.row - this.row);
    }

    public int colDistance(Position pos) {
        return Math.abs(pos.col - this.col);
    }

    public Direction directionOf(Position pos) {
        Direction direction = null;
        if (this == pos) {
            throw new IllegalArgumentException(
                    "Position argument cannot be same as 'this'.");
        }
        else if (Position.sameRow(this, pos)) {
            boolean toRight = pos.col - this.col > 0;
            direction = toRight ? Direction.RIGHT : Direction.LEFT;
        }
        else if (Position.sameCol(this, pos)) {
            boolean toDown = pos.row - this.row > 0;
            direction = toDown ? Direction.DOWN : Direction.UP;
        }
        else if (Position.sameDiag(this, pos)) {
            if (pos.row < this.row && pos.col > this.col) {
                direction = Direction.UP_RIGHT;
            }
            else if (pos.row > this.row && pos.col < this.col) {
                direction = Direction.DOWN_LEFT;
            }
            if (pos.row < this.row && pos.col < this.col) {
                direction = Direction.UP_LEFT;
            }
            if (pos.row > this.row && pos.col > this.col) {
                direction = Direction.DOWN_RIGHT;
            }
        }
        else {
            direction = Direction.OTHER;
        }
        return direction;
    }

    public String toString() {
        String rowVal = Integer.toString(SIDE_COUNT - this.row);
        String colVal = Character.toString(
                (char) (LOWERCASE_START_ASCII + this.col));
        return colVal + rowVal;
    }
}
