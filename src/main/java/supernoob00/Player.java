package supernoob00;

import java.util.List;
import java.util.Set;

public class Player {
    private Board board;
    private List<ChessObject> capturedPieces;

    public Player(Board board) {
        this.board = board;
    }

    public void move(ChessObject piece, Position dest) throws
        IllegalMoveException, 
        SquareAlreadyOccupiedException {
    }
}
