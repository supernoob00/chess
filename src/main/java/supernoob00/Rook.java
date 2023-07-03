package supernoob00;

public class Rook extends Slider implements Valued {
    public final static Rook WHITE_ROOK = new Rook(Color.WHITE);
    public final static Rook BLACK_ROOK = new Rook(Color.BLACK);

    public static final Position WHITE_KINGSIDE_START = Position.get("h1");
    public static final Position WHITE_QUEENSIDE_START = Position.get("a1");
    public static final Position BLACK_KINGSIDE_START = Position.get("h8");
    public static final Position BLACK_QUEENSIDE_START = Position.get("a8");

    public static Position getKingsideStart(Color color) {
        return (color == Color.WHITE) ? WHITE_KINGSIDE_START : BLACK_KINGSIDE_START;
    }

    public static Position getQueensideStart(Color color) {
        return (color == Color.WHITE) ? WHITE_QUEENSIDE_START : BLACK_QUEENSIDE_START;
    }

    public static Rook getInstance(Color color) {
        return color == Color.WHITE ? WHITE_ROOK : BLACK_ROOK;
    }

    protected final int value;

    protected Rook(Color color) {
        super(color, PieceType.ROOK);
        this.value = 5;
        this.moveDirections = Direction.getCardinal();
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "R" : "r";
    }
}
