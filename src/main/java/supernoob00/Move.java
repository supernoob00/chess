package supernoob00;

public class Move {

    private final Position start;
    private final Position end;
    // may not be the same piece at end position - castlerook and castleking change
    private final Piece moved;
    private final Piece taken;
    private final Color colorMoved;
    // stored state of board, necessary in reverting moves
    private final Position pawnTrailPos;
    private final Piece promotion;
    private final MoveDetail detail;
    // only determined when move is actually applied to board
    private KingStatus enemyKingStatus;

    // board parameter is before move is applied
    private Move(Builder builder) {
        this.start = builder.start;
        this.end = builder.end;
        this.moved = builder.moved;
        this.taken = builder.taken;
        this.colorMoved = builder.colorMoved;
        this.pawnTrailPos = builder.pawnTrailPos;
        this.promotion = builder.promotion;
        this.detail = builder.detail;
        this.enemyKingStatus = builder.enemyKingStatus;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public Position getPawnTrailPos() {
        return this.pawnTrailPos;
    }

    public Piece getMoved() {
        return moved;
    }

    public Piece getTaken() {
        return this.taken;
    }

    public Color getColorMoved() {
        return this.colorMoved;
    }

    public Piece getPromotion() {
        return this.promotion;
    }

    public MoveDetail getDetail() {
        return this.detail;
    }

    public KingStatus getEnemyKingStatus() {
        return this.enemyKingStatus;
    }

    public boolean isCastle() {
        return this.detail == MoveDetail.KINGSIDE_CASTLE
                || this.detail == MoveDetail.QUEENSIDE_CASTLE;
    }

    public void setKingStatus(KingStatus status) {
        this.enemyKingStatus = status;
    }

    @Override
    public String toString() {
        return this.start.toString()
                + this.end.toString();
    }

    public static class Builder {
        private final Position start;
        private final Position end;
        // may not be the same piece at end position - castlerook and castleking change
        private final Piece moved;
        private Piece taken;
        private final Color colorMoved;
        // stored state of board, necessary in reverting moves
        private final Position pawnTrailPos;
        private Piece promotion;
        private MoveDetail detail;
        private KingStatus enemyKingStatus;

        public Builder(Position start, Position end, Board board) {
            this.start = start;
            this.end = end;
            this.moved = board.getPiece(start);
            this.taken = board.getPiece(end);
            this.colorMoved = moved.getColor();
            this.pawnTrailPos = board.getPawnTrailPos();
        }

        public Builder promotion(Piece promotion) {
            this.promotion = promotion;
            this.detail = MoveDetail.PROMOTION;
            return this;
        }

        public Builder detail(MoveDetail detail) {
            this.detail = detail;
            if (detail == MoveDetail.EN_PASSANT) {
                this.taken = Pawn.getInstance(colorMoved.opposite());
            }
            return this;
        }

        public Builder kingStatus(KingStatus status) {
            this.enemyKingStatus = status;
            return this;
        }

        public Move build() {
            return new Move(this);
        }
    }
}
