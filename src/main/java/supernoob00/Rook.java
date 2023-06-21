package supernoob00;

public class Rook extends Slider implements Valued {
    public final static Rook WHITE_ROOK = new Rook(Color.WHITE);
    public final static Rook BLACK_ROOK = new Rook(Color.BLACK);

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
