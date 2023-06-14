package supernoob00;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Knight extends Piece implements Valued {
    private final int value;

    public Knight(Color color) {
        super(color);
        this.value = 3;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public Set<Board> pseudoLegalMoves(Position start, Board board) {
        Set<Position> positions = new HashSet<Position>();
        Set<Direction> vertical = Set.of(Direction.UP, Direction.DOWN);
        Set<Direction> horizontal = Set.of(Direction.LEFT, Direction.RIGHT);
        for (Direction vDir : vertical) {
            for (Direction hDir : horizontal) {
                Position firstDest = start.move(vDir, 2).move(hDir, 1);
                Position secondDest = start.move(hDir, 2).move(vDir, 1);
                positions.add(firstDest);
                positions.add(secondDest);
            }
        }
        positions = positions.stream()
                .filter(p -> p != Position.INVALID_POSITION)
                .collect(Collectors.toSet());

        Set<Board> testBoards = new HashSet<Board>();

        for (Position dest : positions) {
            Board testBoard = new Board(board);
            testBoard.removePiece(start);
            testBoard.setPiece(dest, this);
            testBoards.add(testBoard);
        }
        return testBoards;
    }

    @Override
    public boolean threatens(Position start, Position threatened, Board board) {
        Piece piece = board.getPiece(threatened);
        int rowDist = start.rowDistance(threatened);
        int colDist = start.colDistance(threatened);
        return ((rowDist == 2 && colDist == 1) || (colDist == 2 && rowDist == 1))
                && !friendly(piece);
    }
}
