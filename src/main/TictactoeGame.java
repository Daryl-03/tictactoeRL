package main;

import java.awt.*;

public class TictactoeGame {
    private final TictatctoeBoard board;
    final Player player1, player2;
    final int sizeOfBoard;
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;
    private final BoardPrinter boardPrinter;
    private final GameEvaluator gameEvaluator = new GameEvaluator();
    private int gameNumber = 1;

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
            nextLocation = player1Turn ? player1.play(board, gameNumber) : player2.play(board,gameNumber);
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
    }

    public void resetGame(){
        gameStatus = GameStatus.IN_PROGRESS;
        board.resetBoard();
    }

    public void playMultipleGames(int numberOfGames){
        int x_wins=0;
        int y_wins=0;

        for(int i=0; i<numberOfGames; i++){
//            System.err.println("Starting Game #: "+ (i+1) +" epsilon: "+ (0.1/(1 + 0.01 * gameNumber)));
            launchGame();
            if (gameStatus == GameStatus.X_WINS)
                x_wins ++;
            if (gameStatus == GameStatus.O_WINS)
                y_wins ++;
            resetGame();
            gameNumber += 1;
        }

        System.out.println("GAME FINISHED");
        System.out.println("_".repeat(50));
        System.out.println("X_WINS: "+x_wins);
        System.out.println("O_WINS: "+y_wins);
        System.out.println("Draw: "+(numberOfGames - x_wins - y_wins));
    }

}
