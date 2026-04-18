package main.game;

public final class StateSerializer {

    private StateSerializer() {}

    public static String toKey(BoardView board, GameToken toPlay) {
        StringBuilder sb = new StringBuilder();
        sb.append(toPlay);
        sb.append("-");
        for (int i = 1; i <= board.getSize(); i++) {
            for (int j = 1; j <= board.getSize(); j++) {
                sb.append(board.getTokenAt(i, j));
            }
        }
        return sb.toString();
    }

    public static String toKey(GameToken[][] board, GameToken toPlay) {
        StringBuilder sb = new StringBuilder();
        sb.append(toPlay);
        sb.append("-");
        for (GameToken[] gameTokens : board) {
            for (GameToken gameToken : gameTokens) {
                sb.append(gameToken);
            }
        }
        return sb.toString();
    }
}