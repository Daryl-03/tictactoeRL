package main.player;

import main.game.BoardView;
import main.game.Move;

public interface Player {
    Move play(BoardView board, int gameNumber);
    void notifyEnd(BoardView board);
}
