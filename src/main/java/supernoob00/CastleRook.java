package supernoob00;

public class CastleRook extends Rook {
    public final static CastleRook WHITE_CASTLEROOK = new CastleRook(Color.WHITE);
    public final static CastleRook BLACK_CASTLEROOK = new CastleRook(Color.BLACK);

    public static CastleRook getInstance(Color color) {
        return color == Color.WHITE ? WHITE_CASTLEROOK : BLACK_CASTLEROOK;
    }

    public CastleRook(Color color) {
        super(color);
    }
}
