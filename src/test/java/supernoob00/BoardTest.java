package supernoob00;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void boardSetup() {
        Board board = new Board();
        Position pos = Position.get(5, 3);
        Position betweenPos = Position.get(5, 5);
        Position otherPos = Position.get(5, 7);
        Rook rook = new Rook(Color.WHITE);
        board.setPiece(pos, rook);
        board.setPiece(betweenPos, new Pawn(Color.BLACK));
        assertFalse(rook.sees(otherPos, board));
        assertFalse(rook.sees(Position.get(0,0), board));
    }

    @Test
    public void legalMovesCorrect() {
        Board board = new Board();
        Rook rook = new Rook(Color.WHITE);
        board.setPiece(Position.get(5,3), rook);
        board.setPiece(Position.get(5,5), new Pawn(Color.WHITE));
        board.setPiece(Position.get(4,3), new Pawn(Color.WHITE));
        board.setPiece(Position.get(5,2), new Pawn(Color.WHITE));
        board.setPiece(Position.get(6,3), new Pawn(Color.WHITE));
        Set<Position> expectedLegalMoves = Set.of(Position.get(5,4));
        assertEquals(expectedLegalMoves, rook.getLegalMoves(board));
    }
}
