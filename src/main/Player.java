package main;

import java.awt.*;

public interface Player {
    Point play(BoardView board, int gameNumber);
    void notifyEnd(BoardView board);
}
