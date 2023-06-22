package supernoob00;

public class CastleKing extends King {
    public final static CastleKing WHITE_CASTLEKING = new CastleKing(Color.WHITE);
    public final static CastleKing BLACK_CASTLEKING = new CastleKing(Color.BLACK);

    public static CastleKing getInstance(Color color) {
        return color == Color.WHITE ? WHITE_CASTLEKING : BLACK_CASTLEKING;
    }

    public CastleKing(Color color) {
        super(color);
    }
}
