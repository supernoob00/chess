package supernoob00;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private Map<Position, Piece> board;

    public Board() {
        this.board = new HashMap<Position, Piece>(Position.ROW_COUNT * Position.COL_COUNT);
        for (Position pos : Position.POSITIONS) {
                this.board.put(pos, null);
        }
    }

    // copy constructor for Board class
    public Board(Board other) {
        this.board = new HashMap<Position, Piece>(other.board);
    }

    // returns new Board copy with applied changes
    public Board getNewBoard(Set<Position> removals, Map<Position, Piece> placements) {
        Board testBoard = new Board(this);
        for (Position pos : removals) {
            testBoard.removePiece(pos);
        }
        for (Position pos : placements.keySet()) {
            testBoard.setPiece(pos, placements.get(pos));
        }
        return testBoard;
    }

    public Piece getPiece(Position pos) {
        if (this.board.get(pos) instanceof PawnTrail) {
            return null;
        }
        return this.board.get(pos);
    }

    public Piece getPawnTrail(Position pos) {
        if (this.board.get(pos) instanceof PawnTrail) {
            this.board.get(pos);
        }
        return null;
    }

    public void removePiece(Position pos) throws IllegalStateException {
        Piece piece = this.board.get(pos);
        if (piece == null) {
            throw new IllegalStateException("Cannot remove piece on an empty board position.");
        }
        this.board.replace(pos, null);
    }

    public void setPiece(Position pos, Piece piece) throws IllegalStateException {
        if (this.board.get(pos) != null) {
            throw new IllegalStateException("Board position must be empty to set piece.");
        }
        this.board.replace(pos, piece);
    }

    public List<Piece> getPieces() {
        return this.board.values()
                .stream()
                .filter(piece -> piece != null)
                .collect(Collectors.toList());
    }

    public Set<Position> getPiecePositions(Color color) {
        return this.board.keySet()
                .stream()
                .filter(pos -> (this.board.get(pos) != null)
                        && (this.board.get(pos).getColor() == color))
                .collect(Collectors.toSet());
    }

    public Position getKingPosition(Color color) throws IllegalStateException {
        for (Position pos : this.board.keySet()) {
            Piece piece = this.board.get(pos);
            if (piece instanceof King && piece.getColor() == color) {
                return pos;
            }
        }
        throw new IllegalStateException("No king with color " + color + " was found.");
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (Position pos : Position.POSITIONS) {
                String pieceIcon;
                if (this.board.get(pos) == null) {
                    pieceIcon = "*";
                }
                else {
                    pieceIcon = this.board.get(pos).toString();
                }
                board.append(pieceIcon);
                board.append("\n");
        }
        return board.toString();
    }
}
