package main;

import java.awt.*;

public class TictactoeGame {
    private final TictatctoeBoard board;
    final Player player1, player2;
    final int sizeOfBoard;
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;
    private final BoardPrinter boardPrinter;
    private final GameEvaluator gameEvaluator = new GameEvaluator();

    public TictactoeGame(int size, Player player1, Player player2, BoardPrinter boardPrinter){
        board = new TictatctoeBoard(size);
        sizeOfBoard = size;
        this.player1 = player1;
        this.player2 = player2;
        this.boardPrinter = boardPrinter;
    }

    public void launchGame(){
        boolean player1Turn = true;
        boolean gameInProgress = true;
        while(gameInProgress){
            Point nextLocation;
            nextLocation = player1Turn ? player1.play(board) : player2.play(board);
            board.placeValue(
                    player1Turn ? GameToken.X : GameToken.O,
                    nextLocation.x,
                    nextLocation.y
            );

            if(boardPrinter != null)
                boardPrinter.printBoard(board);

            gameStatus = gameEvaluator.evaluateBoard(sizeOfBoard, board);
            if(gameStatus != GameStatus.IN_PROGRESS)
                gameInProgress = false;

            player1Turn = !player1Turn;
        }
        player1.notifyEnd(board);
        player2.notifyEnd(board);
        System.out.print("end of Game : "+ gameStatus.toString());
    }

}
