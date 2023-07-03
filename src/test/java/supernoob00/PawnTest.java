package supernoob00;

import org.junit.Test;

public class PawnTest {

    @Test
    public void applyMoveThenReverse() {
        Board board = BoardFactory.makeBoardFromFen(
                "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R");
        System.out.println(board);
        System.out.println(board.getPawnTrailPos());
    }
}
