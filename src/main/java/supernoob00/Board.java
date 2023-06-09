package supernoob00;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Piece> board;
    private Map<Piece, Position> pieces;

    public Board() {
        this.board = new HashMap<Position, Piece>(Position.ROW_COUNT * Position.COL_COUNT);
        for (Position pos : Position.POSITIONS) {
                this.board.put(pos, null);
        }
        this.pieces = new HashMap<Piece, Position>();
    }

    public Piece getPiece(Position pos) {
        return this.board.get(pos);
    }

    public Position getPosition(Piece piece) {
        return this.pieces.get(piece);
    }

    public void removePiece(Position pos) throws IllegalStateException {
        Piece piece = this.board.get(pos);
        if (piece == null) {
            throw new IllegalStateException("Cannot remove piece on an empty board position.");
        }
        this.board.replace(pos, null);
        this.pieces.remove(piece);
    }

    public void remove(Piece piece) throws IllegalStateException {
        Position pos = this.pieces.get(piece);
        if (this.pieces.get(piece) == null) {
            throw new IllegalStateException("Cannot remove piece on an empty board position.");
        }
        this.board.replace(pos, null);
        this.pieces.remove(piece);
    }

    public void setPiece(Position pos, Piece piece) throws IllegalStateException {
        if (this.board.get(pos) != null) {
            throw new IllegalStateException("Board position must be empty to set piece.");
        }
        this.board.replace(pos, piece);
        this.pieces.put(piece, pos);
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
