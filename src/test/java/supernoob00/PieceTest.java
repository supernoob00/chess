package supernoob00;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void samePieceEquals() {
        Board board = new Board();
        Rook rook = new Rook(Color.WHITE);
        Position pos = Position.get(1,1);
        board.setPiece(pos, rook);
        assertTrue(rook == board.getPiece(pos));
    }
}
