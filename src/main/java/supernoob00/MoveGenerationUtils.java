package supernoob00;

import java.util.Set;

public class BoardTester {

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

    public String getLeafNodeCount(int depth) {

    }
}
