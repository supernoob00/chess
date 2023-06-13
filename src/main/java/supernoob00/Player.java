package supernoob00;

import java.util.List;
import java.util.Set;

public class Player {
    private Board board;
    private List<Piece> capturedPieces;

    public Player(Board board) {
        this.board = board;
    }

    public void move(Piece piece, Position dest) throws
        IllegalMoveException, 
        SquareAlreadyOccupiedException {

        Set<Position> legalMoves = piece.getLegalMoves(this.board);
        if (!legalMoves.contains(dest)) {
            throw new IllegalMoveException();
        }

        board.removePiece(dest);
        board.setPiece(dest, piece);
    }

    public void
}
