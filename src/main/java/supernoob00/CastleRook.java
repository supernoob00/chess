package supernoob00;

public class CastleRook extends Rook {
    public final static CastleRook WHITE_CASTLE_ROOK = new CastleRook(Color.WHITE);
    public final static CastleRook BLACK_CASTLE_ROOK = new CastleRook(Color.BLACK);

    public static Rook getInstance(Color color) {
        return color == Color.WHITE ? WHITE_CASTLE_ROOK : BLACK_CASTLE_ROOK;
    }

    protected CastleRook(Color color) {
        super(color);
    }
}
