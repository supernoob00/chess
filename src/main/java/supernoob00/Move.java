package supernoob00;

import java.util.Set;

public class Move {
    public enum Special {
        EN_PASSANT,
        KINGSIDE_CASTLE,
        QUEENSIDE_CASTLE,
        CHECK,
        CHECKMATE;
    }

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

    public Move(Position start, Position end, Special... special) {
        this(start, end);
        for (Special s : special) {
            switch (s) {
                case EN_PASSANT: this.enPassant = true;
                case KINGSIDE_CASTLE: this.kingsideCastle = true;
                case QUEENSIDE_CASTLE: this.queensideCastle = true;
                case CHECK: this.check = true;
                case CHECKMATE: this.checkmate = true;
            }
        }
    }

    public Position getStart() {
        return this.start;
    }

    public Position getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return this.start.toString()
                + this.end.toString();
    }
}
