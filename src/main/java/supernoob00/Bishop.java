package supernoob00;

public class Bishop extends Slider {
    public Bishop(Color color, int value) {
        super(color, 3);
        this.directions = Direction.getOrdinal();
    }
}
