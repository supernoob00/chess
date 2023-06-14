package supernoob00;

public class Rook extends Slider implements Valued {
    protected final int value;

    public Rook(Color color) {
        super(color);
        this.value = 5;
        this.moveDirections = Direction.getCardinal();
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
