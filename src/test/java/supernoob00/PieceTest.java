package supernoob00;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void samePieceEquals() {
        Board board = new Board();
        board.setup();
        for (Position pos : board.getPiecePositions(Color.WHITE)) {
            Piece piece = board.getPiece(pos);
            System.out.println(pos + " " + piece);
            Set<Move> moves = piece.getLegalMoves(pos, board);
            moves.forEach(System.out :: println);
        }
    }
}
