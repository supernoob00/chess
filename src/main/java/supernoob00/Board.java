package supernoob00;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Board {
    public final static int SQUARE_COUNT = Position.ROW_COUNT * Position.COL_COUNT; // 64

    private Map<Position, BoardObject> board;

    public Board() {
        this.board = new HashMap<Position, BoardObject>();
    }

    // copy constructor for Board class
    public Board(Board other) {
        this.board = new HashMap<Position, BoardObject>(other.board);
    }

    private static void setBoardRow(Board board, int row, List<Piece> pieces) {
        Position.getRowPositions(7).forEach(
                pos -> board.setPiece(pos, pieces.get(pos.getCol())));
    }


    public void setup() {
        BiConsumer<Integer, List<Piece>> setBoardRow = new BiConsumer<>() {
            @Override
            public void accept(Integer row, List<Piece> pieces) {
                Position.getRowPositions(row).forEach(
                        pos -> Board.this.setPiece(pos, pieces.get(pos.getCol())));
            }
        };

        for (Color color : Color.values()) {
            List<Piece> backRow = List.of(
                    CastleRook.getInstance(color),
                    Knight.getInstance(color),
                    Bishop.getInstance(color),
                    Queen.getInstance(color),
                    CastleKing.getInstance(color),
                    Bishop.getInstance(color),
                    Knight.getInstance(color),
                    CastleRook.getInstance(color));
            List<Piece> pawnRow = Collections.nCopies(8, Pawn.getInstance(color));

            if (color == Color.WHITE) {
                setBoardRow.accept(7, backRow);
                setBoardRow.accept(6, pawnRow);
            }
            else {
                setBoardRow.accept(0, backRow);
                setBoardRow.accept(1, pawnRow);
            }
        }
    }

    public void applyMove(Move move) {
        Piece piece = removePiece(move.getStart());
        setPiece(move.getEnd(), piece);
    }

    public void revertMove(Move move, Piece taken) {
        Piece moved = removePiece(move.getEnd());
        if (taken != null) {
            setPiece(move.getEnd(), taken);
        }
        setPiece(move.getStart(), moved);
    }

    public Piece getPiece(Position pos) {
        BoardObject piece = this.board.get(pos);
        // excludes PawnTrails
        return (piece instanceof Piece) ? (Piece) piece : null;
    }

    public void setPiece(Position pos, BoardObject piece) {
        this.board.put(pos, piece);
    }

    public Set<Position> getOccupiedPositions() {
        return this.board.keySet();
    }

    public PawnTrail getPawnTrail(Position pos) {
        BoardObject piece = this.board.get(pos);
        return (piece instanceof PawnTrail) ? (PawnTrail) piece : null;
    }

    public Piece removePiece(Position pos) throws IllegalArgumentException {
        Piece piece = getPiece(pos);
        if (piece == null) {
            throw new IllegalArgumentException();
        }
        this.board.remove(pos);
        return piece;
    }

    public boolean clearPawnTrails() {
        boolean changes = false;
        for (Position pos : this.board.keySet()) {
            if (this.board.get(pos) instanceof PawnTrail) {
                changes = true;
                this.board.remove(pos);
            }
        }
        return changes;
    }

    public boolean hasLineOfSight(Position pos1, Position pos2, Direction dir) {
        Position current = pos1;
        while (current.hasNext(dir) && current != pos2) {
            if (getPiece(current) != null) {
                return false;
            }
            current = current.move(dir);
        }
        return current == pos2;
    }

    public Set<Position> getThreats(Position pos, Color enemyColor) {
        return this.board.keySet().stream()
                .filter((Position p) -> {
                    Piece piece = getPiece(p);
                    return (piece.getColor() == enemyColor)
                            && (piece.threatens(p, pos, this));
                })
                .collect(Collectors.toSet());
    }

    public List<BoardObject> removePieces(Position... lop) {
        List<BoardObject> pieces = new ArrayList<BoardObject>();
        for (Position pos : lop) {
            pieces.add(removePiece(pos));
        }
        return pieces;
    }

    public Set<Position> getPiecePositions(Color color) {
        return this.board.keySet()
                .stream()
                .filter(pos -> getPiece(pos).getColor() == color)
                .collect(Collectors.toSet());
    }

    public Position getPosition(Piece piece) {
        return this.board.keySet().stream()
                .filter(pos -> getPiece(pos).equals(piece))
                .findFirst()
                .orElse(null);
    }

    public Position getPosition(PieceType type, Color color) {
        return this.board.keySet().stream()
                .filter(pos -> getPiece(pos).equals(type, color))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        int i = 1;
        for (Position pos : Position.POSITIONS) {
                Piece piece = getPiece(pos);
                String icon;
                icon = (piece == null) ? "*" : piece.toString();
                board.append(icon);

                if (i > 1 && i % 8 == 0) {
                    board.append("\n");
                }
                i++;
        }
        return board.toString();
    }

    public boolean isThreatened(Position pos, Color enemyColor) {
        for (Position p : getPiecePositions(enemyColor)) {
            Piece enemyPiece = getPiece(p);
            if (enemyPiece.threatens(p, pos, this)) {
                return true;
            }
        }
        return false;
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
