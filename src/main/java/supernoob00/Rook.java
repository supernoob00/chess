package supernoob00;

public class Rook extends Slider {
    public Rook(Color color) {
        super(color, 5);
        this.moveDirections = Direction.getCardinal();
    }
}
