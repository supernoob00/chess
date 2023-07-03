package supernoob00;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final Map<Position, Piece> board = new HashMap<>();

    private Position pawnTrail = null;

    public Piece getPiece(Position pos) {
        return this.board.get(pos);
    }

    public void setPiece(Position pos, Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("Piece cannot be null.");
        }
        this.board.put(pos, piece);
    }

    public Piece removePiece(Position pos) throws IllegalArgumentException {
        Piece piece = this.board.get(pos);
        if (piece == null) {
            throw new IllegalArgumentException();
        }
        this.board.remove(pos);
        return piece;
    }

    public Position getPawnTrailPos() {
        return this.pawnTrail;
    }

    public void setPawnTrailPos(Position pos) {
        this.pawnTrail = pos;
    }

    // TODO: clean this up
    public Position getKingPos(Color color) {
            Position kingPos =  this.board.entrySet().stream()
                    .filter(e ->
                    {boolean isKing = e.getValue().getType() == PieceType.KING;
                     boolean sameColor = e.getValue().getColor() == color;
                    return isKing && sameColor;})
                    .findFirst()
                    .get()
                    .getKey();
            return kingPos;
    }

    // applies move, then sets the KingStatus field of the move
    public void applyMove(Move move) {
        testMove(move);
        Color myColor = move.getColorMoved();
        Color enemyColor = myColor.opposite();
        // set whether move calls check, checkmate, or stalemate on enemy
        KingStatus enemyKingStatus = getKingStatus(enemyColor);
        if (enemyKingStatus != null) {
            move.setKingStatus(enemyKingStatus);
        }
    }

    // applies move to board, but DOES NOT evaluate if that move puts enemy king in
    // check/checkmate/stalemate
    public void testMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        Piece moved = move.getMoved();
        Color myColor = moved.getColor();
        Color enemyColor = myColor.opposite();
        // remove all pawn trails
        this.pawnTrail = null;
        // After moving, a CastleRook or CastleKing will turn into a regular Rook or King
        // without the ability to participate in castling
        if (moved instanceof CastleKing) {
            moved = King.getInstance(myColor);
        }
        else if (moved instanceof CastleRook) {
            moved = Rook.getInstance(myColor);
        }

        removePiece(start);
        // special cases if moved piece is a pawn
        if (moved.getType() == PieceType.PAWN && start.rowDistance(end) == 2) {
            Direction dir = start.directionOf(end);
            this.pawnTrail = start.move(dir);
            setPiece(end, moved);
        }
        else if (move.getDetail() == MoveDetail.EN_PASSANT) {
            Pawn movedPawn = (Pawn) moved;
            Direction pawnMoveDir = movedPawn.getMoveDirection();
            Direction opposite = pawnMoveDir.getOpposite();
            removePiece(end.move(opposite));
        }
        else if (move.getDetail() == MoveDetail.PROMOTION) {
            moved = move.getPromotion();
        }

        // special cases if castle move
        else if (move.isCastle()) {
            Position rookStart;
            Position rookEnd;
            if (move.getDetail() == MoveDetail.KINGSIDE_CASTLE) {
                rookStart = Rook.getKingsideStart(myColor);
                rookEnd = end.move(Direction.LEFT);
            }
            else {
                rookStart = Rook.getQueensideStart(myColor);
                rookEnd = end.move(Direction.RIGHT);
            }
            removePiece(rookStart);
            setPiece(rookEnd, Rook.getInstance(myColor));
        }
        setPiece(end, moved);
    }

    public void revertMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();
        Piece moved = move.getMoved();
        Piece taken = move.getTaken();
        Color myColor = moved.getColor();

        // clear any pawn trail left behind
        this.pawnTrail = null;

        // replace pawn trail of enemy color that existed before move
        if (move.getPawnTrailPos() != null) {
            this.pawnTrail = move.getPawnTrailPos();
        }

        // clear moved piece from end
        removePiece(end);

        // special cases if moved piece is a pawn
        if (move.getDetail() == MoveDetail.EN_PASSANT) {
            Pawn movedPawn = (Pawn) moved;
            Direction pawnMoveDir = movedPawn.getMoveDirection();
            Direction opposite = pawnMoveDir.getOpposite();
            setPiece(end.move(opposite), taken);
        }
        else if (move.getTaken() != null) {
            setPiece(end, taken);
        }
        // special cases if castle move
        else if (move.isCastle()) {
            Position rookStart;
            Position rookEnd;
            if (move.getDetail() == MoveDetail.KINGSIDE_CASTLE) {
                rookStart = Rook.getKingsideStart(myColor);
                rookEnd = end.move(Direction.LEFT);
            }
            else {
                rookStart = Rook.getQueensideStart(myColor);
                rookEnd = end.move(Direction.RIGHT);
            }
            removePiece(rookEnd);
            setPiece(rookStart, CastleRook.getInstance(myColor));
        }
        setPiece(start, moved);
    }

    public Set<Move> getLegalMovesForColor(Color color) {
        Set<Move> legalMoves = new HashSet<>();
        getPiecesOfColor(color).forEach(
                (pos, value) -> {
            Set<Move> legalMovesFromPos = getLegalMoves(pos);
            legalMoves.addAll(legalMovesFromPos);
        });
        Set<Move> castleMoves = getCastleMoves(color);
        legalMoves.addAll(castleMoves);
        return legalMoves;
    }

    public Set<Move> getLegalMoves(Position start) {
        Piece piece = getPiece(start);
        Color myColor = piece.getColor();
        return piece.pseudoLegalMoves(start, this)
                .stream()
                .filter(move -> {
                    testMove(move);
                    Position myKingPos = getKingPos(myColor);
                    // if own king is threatened (in check) move is illegal
                    boolean legal = !isThreatenedPos(myKingPos, myColor.opposite());
                    revertMove(move);
                    return legal;
                })
                .collect(Collectors.toSet());
    }

    public Map<Position, Piece> getPiecesOfColor(Color color) {
        return this.board.entrySet().stream()
                .filter(e -> e.getValue().getColor() == color)
                .collect(Collectors.toMap(Map.Entry :: getKey, Map.Entry :: getValue));
    }

    public Set<Position> getThreats(Position pos, Color enemyColor) {
        return this.board.keySet().stream()
                .filter((Position from) -> {
                    Piece piece = getPiece(from);
                    return (piece.getColor() == enemyColor)
                            && (piece.threatens(from, pos, this));
                })
                .collect(Collectors.toSet());
    }

    // TODO: Clean this up
    public boolean hasLineOfSight(Position pos1, Position pos2) {
        Direction dir = pos1.directionOf(pos2);
        if (dir == Direction.OTHER) {
            return false;
        }
        Position current = pos1;
        while (current.hasNext(dir) && current != pos2) {
            current = current.move(dir);
            if (getPiece(current) != null && current != pos2) {
                return false;
            }
        }
        return true;
    }

    public boolean isThreatenedPos(Position pos, Color enemyColor) {
        return getPiecesOfColor(enemyColor).entrySet().stream()
                .anyMatch(e -> {
                    Position from = e.getKey();
                    Piece piece = e.getValue();
                    return piece.threatens(from, pos, this);
                });
    }

    public Set<Move> getCastleMoves(Color color) {
        Set<Move> moves = new HashSet<>(2);
        Position kingStart = getKingPos(color);
        Position kingEnd;

        if (canCastleKingside(color)) {
            kingEnd = kingStart.move(Direction.RIGHT, 2);
            Move kingsideCastle = new Move.Builder(kingStart, kingEnd, this)
                    .detail(MoveDetail.KINGSIDE_CASTLE)
                    .build();
            moves.add(kingsideCastle);
        }
        if (canCastleQueenside(color)) {
            kingEnd = kingStart.move(Direction.LEFT, 2);
            Move queensideCastle = new Move.Builder(kingStart, kingEnd, this)
                    .detail(MoveDetail.QUEENSIDE_CASTLE)
                    .build();
            moves.add(queensideCastle);
        }
        return moves;
    }

    public boolean canCastleKingside(Color color) {
        Position kingStartPos = King.getStart(color);
        Position rookStartPos = Rook.getKingsideStart(color);

        // Kingside castling is not allowed if either king moved or kingside rook moved
        if (getPiece(rookStartPos) != CastleRook.getInstance(color)
                || getPiece(kingStartPos) != CastleKing.getInstance(color)) {
            return false;
        }

        Position kingCastleMiddle = kingStartPos.move(Direction.RIGHT, 1);
        Position kingCastleEnd = kingStartPos.move(Direction.RIGHT, 2);
        Color enemyColor = color.opposite();

        return hasLineOfSight(kingStartPos, rookStartPos)
                && !isThreatenedPos(kingStartPos, enemyColor)
                && !isThreatenedPos(kingCastleMiddle, enemyColor)
                && !isThreatenedPos(kingCastleEnd, enemyColor);
    }

    public boolean canCastleQueenside(Color color) {
        Position kingStartPos = King.getStart(color);
        Position rookStartPos = Rook.getQueensideStart(color);

        if (getPiece(rookStartPos) != CastleRook.getInstance(color)
                || getPiece(kingStartPos) != CastleKing.getInstance(color)) {
            return false;
        }

        Position kingCastleMiddle = kingStartPos.move(Direction.LEFT, 1);
        Position kingCastleEnd = kingStartPos.move(Direction.LEFT, 2);
        Color enemyColor = color.opposite();

        return hasLineOfSight(kingStartPos, rookStartPos)
                && !isThreatenedPos(kingStartPos, enemyColor)
                && !isThreatenedPos(kingCastleMiddle, enemyColor)
                && !isThreatenedPos(kingCastleEnd, enemyColor);
    }

    public KingStatus getKingStatus(Color color) {
        KingStatus status;
        Position kingPos = getKingPos(color);
        boolean kingThreatened = isThreatenedPos(kingPos, color.opposite());
        boolean hasNoMoves = getLegalMovesForColor(color).size() == 0;
        if (kingThreatened && hasNoMoves) {
            status = KingStatus.CHECKMATE;
        }
        else if (kingThreatened) {
            status = KingStatus.CHECK;
        }
        else if (hasNoMoves) {
            status = KingStatus.STALEMATE;
        }
        else {
            status = null;
        }
        return status;
    }

    // returns a graphical representation of board
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
        board.append("K: " + canCastleKingside(Color.WHITE) + "\n");
        board.append("k: " + canCastleKingside(Color.BLACK) + "\n");
        board.append("Q: " + canCastleQueenside(Color.WHITE) + "\n");
        board.append("q: " + canCastleQueenside(Color.BLACK) + "\n");
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
