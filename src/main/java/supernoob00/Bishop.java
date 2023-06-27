package supernoob00;

public class Bishop extends Slider implements Valued {
    public final static Bishop WHITE_BISHOP = new Bishop(Color.WHITE);
    public final static Bishop BLACK_BISHOP = new Bishop(Color.BLACK);

    public static Bishop get(Color color) {
        return color == Color.WHITE ? WHITE_BISHOP : BLACK_BISHOP;
    }

    private final int value;

    protected Bishop(Color color) {
        super(color, PieceType.BISHOP);
        this.value = 3;
        this.moveDirections = Direction.getOrdinal();
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "B" : "b";
    }
}
