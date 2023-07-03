package supernoob00;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MoveGenerationUtils {

    public static class Result {
        int nodes;
        int captures;
        int ep;
        int castles;
        int promotions;
        int checkmates;
        int checks;
        int discoveryChecks;
    }

    public static Result result = new Result();

    public static void clearResult() {
        result = new Result();
    }

    // Perft function for move generation validation
    public static void getMoveCount(Board board, int depth, Color toMove) {
        if (depth == 0) {
            return;
        }

        Set<Move> legalMoves = board.getLegalMovesForColor(toMove);
        for (Move m : legalMoves) {
            board.applyMove(m);

            if (depth == 1) {
                result.nodes++;
                if (m.getTaken() != null) {
                    result.captures++;
                }
                if (m.getDetail() == MoveDetail.EN_PASSANT) {
                    result.ep++;
                }
                if (m.getEnemyKingStatus() == KingStatus.CHECK) {
                    result.checks++;
                }
                if (m.getDetail() == MoveDetail.PROMOTION) {
                    result.promotions++;
                }
                if (m.getEnemyKingStatus() == KingStatus.CHECKMATE) {
                    result.checkmates++;
                }
                if (m.getDetail() == MoveDetail.KINGSIDE_CASTLE || m.getDetail() == MoveDetail.QUEENSIDE_CASTLE) {
                    result.castles++;
                }
            }
            getMoveCount(board, depth - 1, toMove.opposite());
            board.revertMove(m);
        }
    }

    public static int getLeafNodeCount(Board board, Color toMove, int depth) {
        if (depth == 0) {
            return 1;
        }

        int nodes = 0;

        Set<Move> moves = board.getLegalMovesForColor(toMove);
        for (Move m : moves) {
            board.testMove(m);
            nodes += getLeafNodeCount(board, toMove.opposite(), depth - 1);
            board.revertMove(m);
        }
        return nodes;
    }

    public static List<String> firstMoveNodeCounts(Board start, Color toMove, int depth) {
        List<String> moves = new ArrayList<>();
        Set<Move> firstMoves = start.getLegalMovesForColor(toMove);
        for (Move m : firstMoves) {
            start.testMove(m);
            int nodes = getLeafNodeCount(start, toMove.opposite(), depth - 1);
            moves.add(m.toString() + ": " + nodes);
            start.revertMove(m);
        }

       sortMoves(moves);
        return moves;
    }

    public static void sortMoves(List<String> moves) {
        Comparator<String> sortStyle = Comparator.comparing((String s) -> s.charAt(0))
                .thenComparing(s -> s.charAt(1))
                .thenComparing(s -> s.charAt(2))
                .thenComparing(s -> s.charAt(3));
        moves.sort(sortStyle);
    }

    public static List<String> getMovesFromFile(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        List<String> moves = new ArrayList<>();
        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            String move = s.nextLine();
            moves.add(move);
        }
        s.close();
        return moves;
    }

    public static <T> List<T> getDiff(List<T> sorted1, List<T> sorted2) {
        List<T> diff = new ArrayList<>();

        List<T> larger;
        List<T> smaller;

        if (sorted1.size() > sorted2.size()) {
            larger = sorted1;
            smaller = sorted2;
        }
        else {
            larger = sorted2;
            smaller = sorted1;
        }

        int smallIndex = 0;
        int largeIndex = 0;

        while (smallIndex < smaller.size()) {
            T str1 = smaller.get(smallIndex);
            T str2 = larger.get(largeIndex);

            if (!str1.equals(str2)) {
                if (!smaller.contains(str2)) {
                    diff.add(str2);
                    largeIndex++;
                }
                if (!larger.contains(str1)) {
                    diff.add(str1);
                    smallIndex++;
                }
            }
            else {
                smallIndex++;
                largeIndex++;
            }
        }

        for (int i = largeIndex; i < larger.size(); i++) {
            T str = larger.get(i);
            diff.add(str);
        }

        return diff;
    }
}
