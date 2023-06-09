package supernoob00;

import java.util.Set;

public class King extends Piece {
    public King(Color color, int value) {
        super(color, -1);
    }

    @Override
    public Set<Position> getLegalMoves(Board board) {
        return null;
    }

    public boolean inCheck(Position pos, Board board) {
        return false;
    }
}
