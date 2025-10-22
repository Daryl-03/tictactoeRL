package main;

public interface BoardView {
    GameToken getTokenAt(int line, int col);
    int getSize();
}
