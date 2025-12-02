package main;

public class ConsoleBoardPrinter implements BoardPrinter{

    @Override
    public void printBoard(BoardView boardView) {
        for (int i = 1; i <= boardView.getSize(); i++) {
            for (int j = 1; j <= boardView.getSize(); j++) {
                System.out.print(boardView.getTokenAt(i,j) + "|");
            }
            System.out.println();
            System.out.println("__".repeat(boardView.getSize()));
        }
        System.out.println();
    }
}
