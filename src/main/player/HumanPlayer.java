package main.player;

import main.game.BoardView;
import main.game.GameToken;

import java.awt.*;
import java.util.Scanner;

public class HumanPlayer implements Player {

    final Scanner sc;

    public HumanPlayer(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public Point play(BoardView board, int gameNumber) {
        Point play = new Point();
        do {

            do {
                System.out.println("enter line :");
                play.x = sc.nextInt();
            } while (play.x < 1 || play.x > board.getSize());

            do {
                System.out.println("enter col :");
                play.y = sc.nextInt();
            } while (play.y < 1 || play.y > board.getSize());

        } while (board.getTokenAt(play.x, play.y) != null && board.getTokenAt(play.x, play.y) != GameToken.N);

        return play;
    }

    @Override
    public void notifyEnd(BoardView board) {

    }
}
