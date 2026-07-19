package main.player;

import main.game.BoardView;
import main.game.GameToken;
import main.game.Move;

import java.util.List;
import java.util.ArrayList;

public class RandomPlayer implements Player {

    @Override
    public Move play(BoardView board, int gameNumber) {

        List<Move> legalMoves = new ArrayList<>();

        int size = board.getSize();
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (board.getTokenAt(i, j) == GameToken.N) {
                    legalMoves.add(new Move(i, j));
                }
            }
        }

        if (legalMoves.isEmpty()) {
            throw new IllegalStateException("No legal moves available for RandomPlayer");
        }

        int idx = (int) (Math.random() * legalMoves.size());
        return legalMoves.get(idx);
    }

    @Override
    public void notifyEnd(BoardView board) {
    }
}
