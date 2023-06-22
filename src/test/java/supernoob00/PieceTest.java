package supernoob00;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void samePieceEquals() {
        Board board = new Board();
        board.setup();
        board.setPiece(Position.get("d3"), Knight.BLACK_KNIGHT);
        for (Position pos : board.getPiecePositions(Color.WHITE)) {
            Piece piece = board.getPiece(pos);
            System.out.println(pos + " " + piece);
            Set<Move> moves = piece.getLegalMoves(pos, board);
            moves.forEach(System.out :: println);
        }
        System.out.println(Position.get(0,0));
        System.out.println(Position.get("a8"));
        System.out.println(board.getPiece(Position.get(5,3)));
    }
}
