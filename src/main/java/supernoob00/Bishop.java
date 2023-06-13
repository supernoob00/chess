package supernoob00;

public class Bishop extends SliderPiece {
    public Bishop(Color color, int value) {
        super(color, 3);
        this.moveDirections = Direction.getOrdinal();
    }
}
