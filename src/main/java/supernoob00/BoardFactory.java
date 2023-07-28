package supernoob00;

import java.util.*;

public class BoardFactory {
    public final static int CLASSIC = 1;
    public final static int CHESS_965 = 2;

    // Unless stated otherwise, king and rook chars are assumed to represent
    // unmoved king and rook
    public final static Map<Character, Piece> FEN_PIECE_CHARS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>('R', Rook.getInstance(Color.WHITE)),
            new AbstractMap.SimpleEntry<>('r', Rook.getInstance(Color.BLACK)),
            new AbstractMap.SimpleEntry<>('N', Knight.getInstance(Color.WHITE)),
            new AbstractMap.SimpleEntry<>('n', Knight.getInstance(Color.BLACK)),
            new AbstractMap.SimpleEntry<>('B', Bishop.getInstance(Color.WHITE)),
            new AbstractMap.SimpleEntry<>('b', Bishop.getInstance(Color.BLACK)),
            new AbstractMap.SimpleEntry<>('Q', Queen.getInstance(Color.WHITE)),
            new AbstractMap.SimpleEntry<>('q', Queen.getInstance(Color.BLACK)),
            new AbstractMap.SimpleEntry<>('K', King.getInstance(Color.WHITE)),
            new AbstractMap.SimpleEntry<>('k', King.getInstance(Color.BLACK)),
            new AbstractMap.SimpleEntry<>('P', Pawn.getInstance(Color.WHITE)),
            new AbstractMap.SimpleEntry<>('p', Pawn.getInstance(Color.BLACK)));

    public static Board newBoard(int startingBoard) {
        Board board;
        switch (startingBoard) {
            case CLASSIC:
                board = makeClassicBoard();
                break;
            default:
                throw new IllegalArgumentException("Not a valid starting board type.");
        }
        return board;
    }

    public static Board newBoard(String fen) {
        Board board = new Board();

        String[] parts = fen.split(" ");

        String[] boardString = parts[0].split("/");

        String castlingRights = parts[2];
        boolean whiteCanCastleKingside = castlingRights.contains("K");
        boolean whiteCanCastleQueenside = castlingRights.contains("Q");
        boolean blackCanCastleKingside = castlingRights.contains("k");
        boolean blackCanCastleQueenside = castlingRights.contains("q");

        String enPassantString = parts[3];
        if (!enPassantString.equals("-")) {
            Position enPassantTarget = Position.get(parts[3]);
            board.setPawnTrailPos(enPassantTarget);
        }

        for (int i = 0; i < 8; i++) {
            String s = boardString[i];
            int j = 0;
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    int count = Character.getNumericValue(c);
                    j += count;
                } else {
                    Position pos = Position.get(i, j);
                    Piece piece;

                    // TODO: clean this up
                    if (pos == King.getStart(Color.WHITE)
                            && (whiteCanCastleKingside || whiteCanCastleQueenside)) {
                        piece = CastleKing.WHITE_CASTLE_KING;
                    } else if (pos == King.getStart(Color.BLACK)
                            && (blackCanCastleKingside || blackCanCastleQueenside)) {
                        piece = CastleKing.BLACK_CASTLE_KING;
                    } else if (pos == Rook.getKingsideStart(Color.WHITE)
                            && whiteCanCastleKingside) {
                        piece = CastleRook.WHITE_CASTLE_ROOK;
                    } else if (pos == Rook.getKingsideStart(Color.BLACK)
                            && blackCanCastleKingside) {
                        piece = CastleRook.BLACK_CASTLE_ROOK;
                    } else if (pos == Rook.getQueensideStart(Color.WHITE)
                            && whiteCanCastleQueenside) {
                        piece = CastleRook.WHITE_CASTLE_ROOK;
                    } else if (pos == Rook.getQueensideStart(Color.BLACK)
                            && blackCanCastleQueenside) {
                        piece = CastleRook.BLACK_CASTLE_ROOK;
                    } else {
                        piece = FEN_PIECE_CHARS.get(c);
                    }
                    board.setPiece(pos, piece);
                    j++;
                }
            }
        }
        return board;
    }

    private static Board makeClassicBoard() {
        Board board = new Board();
        List<Piece> whiteBackRow = getBackRow(Color.WHITE);
        List<Piece> whiteFrontRow = getFrontRow(Color.WHITE);
        List<Piece> blackBackRow = getBackRow(Color.BLACK);
        List<Piece> blackFrontRow = getFrontRow(Color.BLACK);

        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            board.setPiece(Position.get(7, i), whiteBackRow.get(i));
        }
        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            board.setPiece(Position.get(6, i), whiteFrontRow.get(i));
        }
        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            board.setPiece(Position.get(0, i), blackBackRow.get(i));
        }
        for (int i = 0; i < Position.SIDE_COUNT; i++) {
            board.setPiece(Position.get(1, i), blackFrontRow.get(i));
        }
        return board;
    }

    private static List<Piece> getBackRow(Color color) {
            return List.of(
                    CastleRook.getInstance(color),
                    Knight.getInstance(color),
                    Bishop.getInstance(color),
                    Queen.getInstance(color),
                    CastleKing.getInstance(color),
                    Bishop.getInstance(color),
                    Knight.getInstance(color),
                    CastleRook.getInstance(color)
            );
        }

    private static List<Piece> getFrontRow(Color color) {
            return Collections.nCopies(Position.SIDE_COUNT, Pawn.getInstance(color));
        }

    private static void setBoardRow(Board board, int row, List<Piece> pieces) {
            Position.getRowPositions(7).forEach(
                    pos -> board.setPiece(pos, pieces.get(pos.getCol())));
        }
}
