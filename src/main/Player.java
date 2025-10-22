package main;

import java.awt.*;

public interface Player {
    Point play(BoardView board);
    void notifyEnd(BoardView board);
}
