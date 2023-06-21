package supernoob00;

import java.util.List;

public class Player {
    private Board board;
    private List<BoardObject> capturedPieces;

    public Player(Board board) {
        this.board = board;
    }

    public void move(Move move, Board board) {
        Piece piece = board.removePiece(move.getStart());
        if (piece == null) {
            throw new IllegalArgumentException();
        }
        board.setPiece(move.getEnd(), piece);
    }
}
