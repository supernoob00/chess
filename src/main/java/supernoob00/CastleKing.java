package supernoob00;

// a special kind of King that can castle

import java.util.HashSet;
import java.util.Set;

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
