package supernoob00;

public class CastleRook extends Rook {
    public final static CastleRook WHITE_CASTLEROOK = new CastleRook(Color.WHITE);
    public final static CastleRook BLACK_CASTLEROOK = new CastleRook(Color.BLACK);

    public static final Position WHITE_KINGSIDE_START = Position.get("h1");
    public static final Position WHITE_QUEENSIDE_START = Position.get("a8");
    public static final Position BLACK_KINGSIDE_START = Position.get("h8");
    public static final Position BLACK_QUEENSIDE_START = Position.get("a1");

    public static Position getKingsideStart(Color color) {
        return (color == Color.WHITE) ? WHITE_KINGSIDE_START : BLACK_KINGSIDE_START;
    }

    public static Position getQueensideStart(Color color) {
        return (color == Color.WHITE) ? WHITE_QUEENSIDE_START : BLACK_QUEENSIDE_START;
    }

    public static CastleRook getInstance(Color color) {
        return color == Color.WHITE ? WHITE_CASTLEROOK : BLACK_CASTLEROOK;
    }

    protected CastleRook(Color color) {
        super(color);
    }
}
