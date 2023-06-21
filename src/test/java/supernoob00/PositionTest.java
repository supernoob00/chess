package supernoob00;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PositionTest {
    @Test
    public void returnsInvalidPosition() {
        Position invalid = Position.get(8,9);
        assertEquals(Position.INVALID_POSITION, invalid);
    }

    @Test
    public void sameRow() {
        Position pos1 = Position.get(1, 2);
        Position pos2 = Position.get(1, 5);
        boolean sameRow = Position.sameRow(pos1, pos2);
        assertTrue(sameRow);
    }

    @Test
    public void sameCol() {
        Position pos1 = Position.get(2, 5);
        Position pos2 = Position.get(4, 5);
        boolean sameCol = Position.sameCol(pos1, pos2);
        assertTrue(sameCol);
    }

    @Test
    public void sameDiagonal() {
        Position pos1 = Position.get(1, 2);
        Position pos2 = Position.get(2, 1);
        Position pos3 = Position.get(2, 3);
        boolean sameDiag = Position.sameDiag(pos1, pos2);
        boolean sameDiag2 = Position.sameDiag(pos1, pos3);
        assertTrue(sameDiag);
        assertTrue(sameDiag2);
    }

    @Test
    public void directionOfTest() {
        Position pos1 = Position.get(1, 2);
        Position pos2 = Position.get(1, 3);
        Position pos4 = Position.get(7, 2);
        Position pos5 = Position.get(2, 3);
        Position pos6 = Position.get(3,3);
        assertEquals(Direction.RIGHT, pos1.directionOf(pos2));
        assertEquals(Direction.DOWN, pos1.directionOf(pos4));
        assertEquals(Direction.DOWN_RIGHT, pos1.directionOf(pos5));
        assertEquals(Direction.UP_LEFT, pos5.directionOf(pos1));
        assertEquals(Direction.OTHER, pos1.directionOf(pos6));
    }

    @Test
    public void moveTest() {
        Position dest = Position.get(1,1).move(Direction.DOWN);
        assertEquals(Position.get(2,1), dest);
    }
}
