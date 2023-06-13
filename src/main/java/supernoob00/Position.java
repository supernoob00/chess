package supernoob00;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static supernoob00.Direction.UP;

public class Position {
    public final static Position INVALID_POSITION = new Position(-1, -1);

    public final static int ROW_COUNT = 8;
    public final static int COL_COUNT = 8;
    public final static int DIAG_COUNT = ROW_COUNT * 2 - 1;

    public final static List<Position> POSITIONS;
    public final static List<Position> COL_POSITIONS;
    public final static List<Position> DIAG_POSITIONS;
    public final static List<Position> ANTIDIAG_POSITIONS;

    static {
        int size = ROW_COUNT * COL_COUNT;

        POSITIONS = new ArrayList<Position>(size);
        COL_POSITIONS = new ArrayList<Position>(size);
        DIAG_POSITIONS = new ArrayList<Position>(size);
        ANTIDIAG_POSITIONS = new ArrayList<Position>(size);

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < COL_COUNT; col++) {
                Position pos = new Position(row, col);
                POSITIONS.add(pos);
                // COL_POSITIONS.add(COL_COUNT * col + row, pos);
            }
        }

        /*
        for (int diagIndex = 0; diagIndex < DIAG_COUNT; diagIndex++) {
            for (int insideIndex = 0; insideIndex < diagIndex + 1; insideIndex++) {
                Position pos = Position.get(diagIndex, insideIndex);
                DIAG_POSITIONS.add(pos);
            }
        } */
    }

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

    private int row;
    private int col;

    private Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Position get(int row, int col) {
        boolean rowOutOfBounds = row < 0 || col > ROW_COUNT;
        boolean colOutOfBounds = col < 0 || col > COL_COUNT;
        if (rowOutOfBounds || colOutOfBounds) {
            return INVALID_POSITION;
        }
        int index = ROW_COUNT * row + col;
        return POSITIONS.get(index);
    }

    public static Position get(String coord) {
        // Code here
        return Position.get(0,0);
    }

    public int getRowIndex() {
        return this.row;
    }

    public boolean validMove(int rowShift, int colShift) {
        boolean validRow = this.row + rowShift <= ROW_COUNT;
        boolean validCol = this.col + colShift <= COL_COUNT;
        return validRow && validCol;
    }

    public Position move(int dx, int dy) {
        return Position.get(this.row + dx, this.col + dy);
    }

    public Position move(Direction dir, int count) {
        Position dest = this;
        switch (dir) {
            case UP:
                dest = up(count);
                break;
            case DOWN:
                dest = down(count);
                break;
            case RIGHT:
                dest = right(count);
                break;
            case LEFT:
                dest = left(count);
                break;
        }
        return dest;
    }

    public Position move(Direction dir) {
        return move(dir, 1);
    }

    public Position up(int count) {
        return move(-count, 0);
    }

    public Position up() {
        return up(1);
    }

    public Position left(int count) {
        return move(0, -count);
    }

    public Position left() {
        return left(1);
    }

    public Position down(int count) {
        return move(count, 0);
    }

    public Position down() {
        return down(1);
    }

    public Position right(int count) {
        return move(0, count);
    }

    public Position right() {
        return right(1);
    }

    public boolean hasNext(Direction dir) {
        if (move(dir) == INVALID_POSITION) {
            return false;
        }
        return true;
    }

    // TODO : clean this up
    public Direction directionOf(Position pos) {
        if (this == pos) {
            throw new IllegalArgumentException();
        }
        if (Position.sameRow(this, pos)) {
            boolean toRight = pos.col - this.col > 0;
            return toRight ? Direction.RIGHT : Direction.LEFT;
        }
        else if (Position.sameCol(this, pos)) {
            boolean toDown = pos.row - this.row > 0;
            return toDown ? Direction.DOWN : Direction.UP;
        }
        else if (Position.sameDiag(this, pos)) {
            Direction diagDir = null;
            boolean toUpRight = pos.row < this.row && pos.col > this.col;
            if (toUpRight) {
                diagDir = Direction.UP_RIGHT;
            }
            boolean toDownLeft = pos.row > this.row && pos.col < this.col;
            if (toDownLeft) {
                diagDir = Direction.DOWN_LEFT;
            }
            boolean toUpLeft = pos.row < this.row && pos.col < this.col;
            if (toUpLeft) {
                diagDir = Direction.UP_LEFT;
            }
            boolean toDownRight = pos.row > this.row && pos.col > this.col;
            if (toDownRight) {
                diagDir = Direction.DOWN_RIGHT;
            }
            return diagDir;
        }
        return Direction.OTHER;
    }

    public List<Position> getRow() {
        int startIndex = ROW_COUNT * this.row;
        int endIndex = startIndex + COL_COUNT;
        return POSITIONS.subList(startIndex, endIndex);
    }

    public List<Position> getCol() {
        int startIndex = COL_COUNT * this.col;
        int endIndex = startIndex + ROW_COUNT;
        return COL_POSITIONS.subList(startIndex, endIndex);
    }

    public String toString() {
        return this.row + " " + this.col;
    }
}
