package supernoob00;

import java.util.Set;

public interface Movable {
    Set<Board> getLegalMoves(Position start, Board board);

    // Set of possible moves before testing if they put own King in check
    Set<Board> pseudoLegalMoves(Position start, Board board);

    boolean canMove(Position start, Board before, Board after);

    boolean threatens(Position start, Position threatened, Board board);
}
