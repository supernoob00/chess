package supernoob00;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class PieceTest {
    @Test
    public void moveGenerationCorrect() {
        Board oldBoard = BoardFactory.makeBoardFromFen(
                "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - - -");

        int nodes = MoveGenerationUtils.getLeafNodeCount(oldBoard, Color.WHITE, 4);

        System.out.println(nodes);

        try {
            List<String> moves = MoveGenerationUtils.getMovesFromFile("moves.txt");
            MoveGenerationUtils.sortMoves(moves);
            System.out.println(moves);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
