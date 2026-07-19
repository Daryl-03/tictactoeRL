package main.player;

import main.game.BoardView;
import main.game.GameToken;
import main.game.Move;

import java.util.Scanner;

public class HumanPlayer implements Player {

    final Scanner sc;

    public HumanPlayer(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public Move play(BoardView board, int gameNumber) {
        int row;
        int col;
        do {

            do {
                System.out.println("enter line :");
                row = sc.nextInt();
            } while (row < 1 || row > board.getSize());

            do {
                System.out.println("enter col :");
                col = sc.nextInt();
            } while (col < 1 || col > board.getSize());

        } while (board.getTokenAt(row, col) != GameToken.N);

        return new Move(row, col);
    }

    @Override
    public void notifyEnd(BoardView board) {

    }
}
