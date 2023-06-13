package supernoob00;

public class Queen extends Slider {
    public Queen(Color color, int value) {
        super(color, 8);
        this.moveDirections = Direction.getAll();
    }
}
