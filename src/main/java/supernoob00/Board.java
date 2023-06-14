package supernoob00;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private Map<Position, ChessObject> board;

    public Board() {
        this.board = new HashMap<Position, ChessObject>(Position.ROW_COUNT * Position.COL_COUNT);
        for (Position pos : Position.POSITIONS) {
                this.board.put(pos, null);
        }
    }

    // copy constructor for Board class
    public Board(Board other) {
        this.board = new HashMap<Position, ChessObject>(other.board);
    }

    // returns new Board copy with applied changes
    public Board getNewBoard(Map<Position, ChessObject> changes) {
        Board testBoard = new Board(this);
        for (Position pos : changes.keySet()) {
            testBoard.setPiece(pos, changes.get(pos));
        }
        return testBoard;
    }

    public Piece getPiece(Position pos) {
        ChessObject piece = this.board.get(pos);
        // excludes PawnTrails
        return (piece instanceof Piece) ? (Piece) piece : null;
    }

    public PawnTrail getPawnTrail(Position pos) {
        ChessObject piece = this.board.get(pos);
        return (piece instanceof PawnTrail) ? (PawnTrail) piece : null;
    }

    public ChessObject removePiece(Position pos) {
        ChessObject piece = this.board.get(pos);
        this.board.replace(pos, null);
        return piece;
    }

    public List<ChessObject> removePieces(Position... lop) {
        List<ChessObject> pieces = new ArrayList<ChessObject>();
        for (Position pos : lop) {
            pieces.add(removePiece(pos));
        }
        return pieces;
    }

    public void setPiece(Position pos, ChessObject piece) {
        this.board.replace(pos, piece);
    }

    public List<ChessObject> getPieces() {
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
            ChessObject piece = this.board.get(pos);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board other = (Board) o;
        return Objects.equals(this.board, other.board);
    }

    @Override
    public int hashCode() {
        return this.board.hashCode();
    }
}
