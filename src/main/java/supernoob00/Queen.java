package supernoob00;

public class Queen extends Slider implements Valued {
    private final int value;

    public Queen(Color color) {
        super(color);
        this.value = 8;
        this.moveDirections = Direction.getAll();
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
