package supernoob00;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    public final static Board DEFAULT_BOARD = new Board();

    private static List<Piece> getBackRow(Color color) {
        return List.of(
            CastleRook.getInstance(color),
            Knight.getInstance(color),
            Bishop.get(color),
            Queen.getInstance(color),
            CastleKing.getInstance(color),
            Bishop.get(color),
            Knight.getInstance(color),
            CastleRook.getInstance(color)
        );
    }
    private static List<Piece> getFrontRow(Color color) {
        return Collections.nCopies(Position.SIDE_COUNT, Pawn.getInstance(color));
    }

    static {
        List<Piece> whiteBackRow = getBackRow(Color.WHITE);
        List<Piece> whiteFrontRow = getFrontRow(Color.WHITE);
        List<Piece> blackBackRow = getBackRow(Color.BLACK);
        List<Piece> blackFrontRow = getBackRow(Color.BLACK);

        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            DEFAULT_BOARD.setPiece(Position.get(7, i), whiteBackRow.get(i));
        }
        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            DEFAULT_BOARD.setPiece(Position.get(6, i), whiteFrontRow.get(i));
        }
        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            DEFAULT_BOARD.setPiece(Position.get(0, i), blackBackRow.get(i));
        }
        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            DEFAULT_BOARD.setPiece(Position.get(1, i), blackFrontRow.get(i));
        }
    }

    private final Map<Position, BoardObject> board;

    public Board() {
        this.board = new HashMap<Position, BoardObject>();
    }

    public Board(Map<Position, BoardObject> board) {
        this.board = new HashMap<Position, BoardObject>(board);
    }

    public boolean contains(Piece piece) {
        return this.board.containsValue(piece);
    }

    // copy constructor for Board class
    public Board(Board other) {
        this.board = new HashMap<Position, BoardObject>(other.board);
    }

    private static void setBoardRow(Board board, int row, List<Piece> pieces) {
        Position.getRowPositions(7).forEach(
                pos -> board.setPiece(pos, pieces.get(pos.getCol())));
    }

    public void applyMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

        if (move.isEnPassant()) {
            Pawn movedPawn = (Pawn) removePiece(start);
            setPiece(end, movedPawn);
            Direction pawnMoveDir = movedPawn.getMoveDirection();
            Direction opposite = pawnMoveDir.getOpposite();
            removePiece(end.move(opposite));
        }
        else if (move.isCastle()) {
            Piece movedKing = removePiece(start);
            Color kingColor = movedKing.getColor();
            Piece movedRook;
            Position rookEnd;

            if (move.isQueensideCastle()) {
                movedRook = removePiece(CastleRook.getQueensideStart(kingColor));
                rookEnd = end.move(Direction.RIGHT);
            }
            else {
                movedRook = removePiece(CastleRook.getKingsideStart(kingColor));
                rookEnd = end.move(Direction.LEFT);
            }
            setPiece(end, movedKing);
            setPiece(rookEnd, movedRook);
        }
        else {
            Piece piece = removePiece(start);
            setPiece(move.getEnd(), piece);
        }
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

    public Position getPosition(BoardObject o) {
        return this.board.keySet().stream()
                .filter(pos -> this.board.get(pos) == o)
                .findFirst()
                .orElse(null);
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
        Iterator<Position> iter = this.board.keySet().iterator();
        while (iter.hasNext()) {
            PawnTrail trail = getPawnTrail(iter.next());
            if (trail != null) {
                iter.remove();
            }
        }
        return changes;
    }

    public boolean hasLineOfSight(Position pos1, Position pos2) {
        Direction dir = pos1.directionOf(pos2);
        if (dir == Direction.OTHER) {
            return false;
        }
        Position current = pos1;
        while (current.hasNext(dir) && current != pos2) {
            if (getPiece(current) != null) {
                return false;
            }
            current = current.move(dir);
        }
        return true;
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

    public Position getPosition(Piece piece) {
        return this.board.keySet().stream()
                .filter(pos -> getPiece(pos).equals(piece))
                .findFirst()
                .orElse(null);
    }

    public Set<Position> getPiecePositions(Color color) {
        return this.board.keySet()
                .stream()
                .filter(pos -> getPiece(pos).getColor() == color)
                .collect(Collectors.toSet());
    }

    public boolean isSafe(Position pos, Color enemyColor) {
        for (Position p : getPiecePositions(enemyColor)) {
            Piece enemyPiece = getPiece(p);
            if (enemyPiece.threatens(p, pos, this)) {
                return false;
            }
        }
        return true;
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
