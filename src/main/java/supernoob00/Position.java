package supernoob00;

import java.util.ArrayList;
import java.util.List;

public class Position {
    public final static int LOWER_START_INDEX = 97;

    public final static Position INVALID_POSITION = new Position(-1, -1);

    public final static int ROW_COUNT = 8;
    public final static int COL_COUNT = 8;
    public final static List<Position> POSITIONS;

    static {
        int size = ROW_COUNT * COL_COUNT;
        POSITIONS = new ArrayList<Position>(size);

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                Position pos = new Position(row, col);
                POSITIONS.add(pos);
            }
        }
    }

    private final int row;
    private final int col;

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

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRowPositions() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public static Position get(int row, int col) {
        boolean rowOutOfBounds = row < 0 || row > ROW_COUNT - 1;
        boolean colOutOfBounds = col < 0 || col > COL_COUNT - 1;
        if (rowOutOfBounds || colOutOfBounds) {
            return INVALID_POSITION;
        }
        int index = ROW_COUNT * row + col;
        return POSITIONS.get(index);
    }

    public static Position get(String coord) {
        char[] charArray = coord.toCharArray();
        int row = ROW_COUNT - charArray[1];
        int col = ((int) charArray[0]) - LOWER_START_INDEX;
        return Position.get(row, col);
    }

    public boolean validMove(int rowShift, int colShift) {
        boolean validRow = this.row + rowShift <= ROW_COUNT;
        boolean validCol = this.col + colShift <= COL_COUNT;
        return validRow && validCol;
    }

    public static List<Position> getRowPositions(int row) {
        int startIndex = COL_COUNT * row;
        int endIndex = startIndex + COL_COUNT;
        return POSITIONS.subList(startIndex, endIndex);
    }

    public Position move(int dx, int dy) {
        return Position.get(this.row + dx, this.col + dy);
    }

    // TODO : fix this
    public Position move(Direction dir, int count) {
        switch (dir) {
            case UP:
                return this.up(count);
            case DOWN:
                return this.down(count);
            case RIGHT:
                return this.right(count);
            case LEFT:
                return this.left(count);
            case UP_LEFT:
                return this.up(count).left(count);
            case UP_RIGHT:
                return this.up(count).right(count);
            case DOWN_LEFT:
                return this.down(count).left(count);
            case DOWN_RIGHT:
                return this.down(count).right(count);
            case OTHER:
                throw new IllegalArgumentException();
        };
        return INVALID_POSITION;
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
        if (move(dir, dist) == INVALID_POSITION) {
            return false;
        }
        return true;
    }

    public boolean hasNext(Direction dir) {
        if (move(dir) == INVALID_POSITION) {
            return false;
        }
        return true;
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
        String rowVal = Integer.toString(ROW_COUNT - this.row);
        String colVal = Character.toString(
                (char) (LOWER_START_INDEX + this.col));
        return colVal + rowVal;
    }
}
