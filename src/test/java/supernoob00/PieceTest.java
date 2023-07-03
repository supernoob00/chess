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
                "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -");

        int nodes = MoveGenerationUtils.getLeafNodeCount(oldBoard, Color.WHITE, 5);

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
