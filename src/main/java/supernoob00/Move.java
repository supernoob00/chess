package supernoob00;

public class Move {

    public static final int EN_PASSANT = 1;
    public static final int KINGSIDE_CASTLE = 2;
    public static final int QUEENSIDE_CASTLE = 3;
    public static final int CHECK = 4;
    public static final int CHECKMATE = 5;

    private Position start;
    private Position end;
    private boolean enPassant;
    private boolean kingsideCastle;
    private boolean queensideCastle;
    private boolean check;
    private boolean checkmate;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Move(Position start, Position end, int checkOrMate, int special) {
        this(start, end);
        switch (checkOrMate) {
            case CHECK -> this.check = true;
            case CHECKMATE -> this.checkmate = true;
            default -> throw new IllegalArgumentException();
        }
        switch (special) {
            case EN_PASSANT -> this.enPassant = true;
            case KINGSIDE_CASTLE -> this.kingsideCastle = true;
            case QUEENSIDE_CASTLE -> this.queensideCastle = true;
            default -> throw new IllegalArgumentException();
        }
    }

    public Move(Position start, Position end, int checkOrMate) {
        this(start, end);
        switch (checkOrMate) {
            case CHECK -> this.check = true;
            case CHECKMATE -> this.checkmate = true;
            case EN_PASSANT -> this.enPassant = true;
            case KINGSIDE_CASTLE -> this.kingsideCastle = true;
            case QUEENSIDE_CASTLE -> this.queensideCastle = true;
            default -> throw new IllegalArgumentException();
        }
    }

    public Position getStart() {
        return this.start;
    }

    public Position getEnd() {
        return this.end;
    }

    public boolean isEnPassant() {
        return this.enPassant;
    }

    public boolean isCheck() {
        return this.check;
    }

    public boolean isCheckmate() {
        return this.checkmate;
    }

    public boolean isCastle() {
        return this.kingsideCastle || this.queensideCastle;
    }

    public boolean isKingsideCastle() {
        return this.kingsideCastle;
    }

    public boolean isQueensideCastle() {
        return this.queensideCastle;
    }

    public void setCheck() {
        this.check = true;
    }

    public void setCheckmate() {
        this.checkmate = true;
    }

    @Override
    public String toString() {
        return this.start.toString()
                + this.end.toString();
    }
}
