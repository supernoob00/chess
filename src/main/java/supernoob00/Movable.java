package supernoob00;

import java.util.Set;

public interface Movable {
    Set<Move> getLegalMoves(Position start, Board board);

    // Set of possible moves before testing if they put own King in check
    Set<Move> pseudoLegalMoves(Position start, Board board);

    boolean canMove(Move move, Board board);

    boolean threatens(Position start, Position other, Board board);

}
