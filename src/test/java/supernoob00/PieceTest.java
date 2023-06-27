package supernoob00;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void samePieceEquals() {
        Board board = Board.DEFAULT_BOARD;
        for (Position pos : board.getPiecePositions(Color.WHITE)) {
            Piece piece = board.getPiece(pos);
            Set<Move> moves = piece.getLegalMoves(pos, board);
            moves.forEach(System.out :: println);
        }
    }
}
