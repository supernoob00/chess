package supernoob00;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Piece extends BoardObject implements Movable {
    protected PieceType type;

    public Piece(Color color, PieceType type) {
        super(color);
        this.type = type;
    }

    public PieceType getType() {
        return this.type;
    }

    @Override
    public Set<Move> getLegalMoves(Position start, Board board) {
        King myKing = King.getInstance(this.color);
        Position myKingPos = board.getPosition(myKing);
        Set<Move> legalMoves = pseudoLegalMoves(start, board)
                .stream()
                .filter(move -> {
                    Piece taken = board.getPiece(move.getEnd());
                    board.applyMove(move);
                    boolean illegal = myKing.inCheck(myKingPos, board);
                    board.revertMove(move, taken);
                    return illegal;
                })
                .collect(Collectors.toSet());
        return legalMoves;
    }

    @Override
    public boolean canMove(Move move, Board board) {
        Set<Move> legalMoves = getLegalMoves(move.getStart(), board);
        return legalMoves.contains(move);
    }

    public boolean friendly(BoardObject obj) {
        return (obj != null) && (obj.getColor() == this.color);
    }

    public boolean enemy(BoardObject obj) {
        return (obj != null) && (obj.getColor() != this.color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece)) return false;
        Piece piece = (Piece) o;
        return type == piece.type && color == piece.color;
    }

    public boolean equals(PieceType type, Color color) {
        return this.type == type && this.color == color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}
