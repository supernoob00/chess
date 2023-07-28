package supernoob00;

public class CastleKing extends King {
    public final static CastleKing WHITE_CASTLE_KING = new CastleKing(Color.WHITE);
    public final static CastleKing BLACK_CASTLE_KING = new CastleKing(Color.BLACK);

    public static CastleKing getInstance(Color color) {
        return color == Color.WHITE ? WHITE_CASTLE_KING : BLACK_CASTLE_KING;
    }

    protected CastleKing(Color color) {
        super(color);
    }
}
