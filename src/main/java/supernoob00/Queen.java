package supernoob00;

import static supernoob00.Color.WHITE;

public class Queen extends Slider implements Valued {
    public final static Queen WHITE_QUEEN = new Queen(WHITE);
    public final static Queen BLACK_QUEEN = new Queen(Color.BLACK);

    public static Queen getInstance(Color color) {
        return color == WHITE ? WHITE_QUEEN : BLACK_QUEEN;
    }

    private final int value;

    protected Queen(Color color) {
        super(color, PieceType.QUEEN);
        this.value = 8;
        this.moveDirections = Direction.getAll();
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.color == Color.WHITE ? "Q" : "q";
    }
}
