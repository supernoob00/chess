package supernoob00;

public class Move {
    private Position start;
    private Position end;
    private Piece pieceMoved;
    private Piece pieceTaken;
    // en passant means position of taken piece
    // is not necessarily final location of moved piece
    private Position pieceTakenPosition;
    private boolean checkCalled;


    public Move(Position start, Position end, Piece pieceMoved) {
        this.start = start;
        this.end = end;
        this.pieceMoved = pieceMoved;
        this.pieceTaken = null;
        this.checkCalled = false;
    }

    public Position getStart() {
        return this.start;
    }

    public boolean isLegal(Move move, Board board) {
        return false;
    }
}
