package supernoob00;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void samePieceEquals() {
        Board board = new Board();
        Rook rook = Rook.getInstance(Color.WHITE);
        Rook enemyRook = Rook.getInstance(Color.BLACK);
        King king = King.getInstance(Color.WHITE);
        Queen queen = Queen.WHITE_QUEEN;
        Position pos = Position.get(0,0);
        Position pos2 = Position.get(0, 1);
        Position pos3 = Position.get(1, 0);
        Position pos4 = Position.get(7, 7);
        board.setPiece(pos, Queen.WHITE_QUEEN);
        board.setPiece(pos2, rook);
        board.setPiece(pos3, enemyRook);
        board.setPiece(pos4, king);
        board.setPiece(Position.get(6,6), Knight.BLACK_KNIGHT);
        board.setPiece(Position.get(4,4), Queen.BLACK_QUEEN);
        board.setPiece(Position.get(2,3), Pawn.BLACK_PAWN);
        board.setPiece(Position.get(5, 5), King.BLACK_KING);
        System.out.println(board);
        Knight.BLACK_KNIGHT.pseudoLegalMoves(Position.get(6,6), board)
                .stream()
                .map(move -> move.toString())
                .forEach(System.out :: println);
    }
}
