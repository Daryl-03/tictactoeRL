package main;

import java.awt.*;
import java.util.Scanner;

public class HumanPlayer implements Player{

    final Scanner sc;

    public HumanPlayer(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public Point play(BoardView board, int gameNumber) {
        Point x = new Point();
        System.out.println("enter line :");
        x.x = sc.nextInt();
        System.out.println("enter col :");
        x.y = sc.nextInt();
        return x;
    }

    @Override
    public void notifyEnd(BoardView board) {

    }
}
