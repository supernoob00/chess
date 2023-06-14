package supernoob00;

public class Bishop extends Slider implements Valued {
    private final int value;

    public Bishop(Color color) {
        super(color);
        this.value = 3;
        this.moveDirections = Direction.getOrdinal();
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
