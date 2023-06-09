package supernoob00;

public enum Color {
    WHITE("white"),
    BLACK("black"),;

    private String colorName;

    private Color(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return this.colorName;
    }
}
