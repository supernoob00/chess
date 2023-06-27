package supernoob00;

import java.util.HashSet;
import java.util.Set;

public class CastleKing extends King {
    public final static CastleKing WHITE_CASTLEKING = new CastleKing(Color.WHITE);
    public final static CastleKing BLACK_CASTLEKING = new CastleKing(Color.BLACK);

    public static CastleKing getInstance(Color color) {
        return color == Color.WHITE ? WHITE_CASTLEKING : BLACK_CASTLEKING;
    }

    public static final Position WHITE_kingStartPos = Position.get("e1");
    public static final Position BLACK_kingStartPos = Position.get("e8");

    public static Position getStart(Color color) {
        return (color == Color.WHITE) ? WHITE_kingStartPos : BLACK_kingStartPos;
    }

    public CastleKing(Color color) {
        super(color);
    }

    @Override
    public Set<Move> getLegalMoves(Position start, Board board) {
        Set<Move> moves = super.getLegalMoves(start, board);
        moves.addAll(getCastleMoves(board));
        return moves;
    }

    public Set<Move> getCastleMoves(Board board) {
        Set<Move> moves = new HashSet<>(2);
        Position kingStart = getStart(this.color);
        if (canCastleKingside(board)) {
            Move kingsideCastle = new Move(
                    kingStart,
                    kingStart.move(Direction.RIGHT, 2),
                    Move.KINGSIDE_CASTLE);
            moves.add(kingsideCastle);
        }
        if (canCastleQueenside(board)) {
            Move queensideCastle = new Move(
                    kingStart,
                    kingStart.move(Direction.LEFT, 2),
                    Move.QUEENSIDE_CASTLE);
            moves.add(queensideCastle);
        }
        return moves;
    }

    public boolean canCastleKingside(Board board) {
        return canCastle(board, Move.KINGSIDE_CASTLE);
    }

    public boolean canCastleQueenside(Board board) {
        return canCastle(board, Move.QUEENSIDE_CASTLE);
    }

    private boolean canCastle(Board board, int castleType) {
        CastleRook myCastleRook = CastleRook.getInstance(this.color);
        Position kingStartPos = getStart(this.color);
        Position rookStartPos = null;

        if (castleType == Move.KINGSIDE_CASTLE) {
            rookStartPos = CastleRook.getKingsideStart(this.color);
        }
        else if (castleType == Move.QUEENSIDE_CASTLE) {
            rookStartPos = CastleRook.getQueensideStart(this.color);
        }

        Position kingCastleMiddle = kingStartPos.move(Direction.RIGHT, 1);
        Position kingCastleEnd = kingStartPos.move(Direction.RIGHT, 2);

        return board.getPiece(kingStartPos) == this
                && board.getPiece(rookStartPos) == myCastleRook
                && !inCheck(kingStartPos, board)
                && board.isSafe(kingCastleMiddle, this.color.opposite())
                && board.isSafe(kingCastleEnd, this.color.opposite())
                && board.hasLineOfSight(kingStartPos, rookStartPos);
    }

}
