package supernoob00;

public class Move {
    private Position start;
    private Position end;
    private ChessObject pieceMoved;
    private ChessObject pieceTaken;
    // en passant means position of taken piece
    // is not necessarily final location of moved piece
    private Position pieceTakenPosition;
    private boolean checkCalled;


    public Move(Position start, Position end, ChessObject pieceMoved) {
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
