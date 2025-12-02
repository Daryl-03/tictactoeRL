package main;

public class GameEvaluator {

    public GameStatus evaluateBoard(int sizeOfBoard, BoardView board) {
        GameToken winner = GameToken.N;
        boolean canPlaceMore = false;
        // vérifier les lignes
        for (int i = 1; i <= sizeOfBoard; i++) {
            boolean lineContainsWonSequence = true;
            for (int j = 1; j <= sizeOfBoard; j++) {
                if (board.getTokenAt(i, j) == GameToken.N) {
                    lineContainsWonSequence = false;
                    canPlaceMore = true;
                    break;
                }
                if (board.getTokenAt(i, j) != board.getTokenAt(i, 1)) {
                    lineContainsWonSequence = false;
                }
            }
            if(lineContainsWonSequence) {
                winner = board.getTokenAt(i,1);

            }

        }

        for (int i = 1; i <= sizeOfBoard; i++) {
            boolean colContainsWonSequence = true;
            for (int j = 1; j <= sizeOfBoard; j++) {
                if( board.getTokenAt(j,i) == GameToken.N || board.getTokenAt(j,i) != board.getTokenAt(1,i)) {
                    colContainsWonSequence = false;
                    break;
                }
            }
            if(colContainsWonSequence) {
                winner = board.getTokenAt(1,i);

            }
        }


        // vérifier les diagonales
        boolean containsWonSequence = true;
        for (int i = 1; i <= sizeOfBoard; i++) {
            if( board.getTokenAt(i,i) == GameToken.N || board.getTokenAt(i,i) != board.getTokenAt(1,1)) {
                containsWonSequence = false;
                break;
            }
        }
        if(containsWonSequence) {
            winner = board.getTokenAt(1,1);

        }

        // diag secondaire
        containsWonSequence = true;
        for (int i = 1; i <= sizeOfBoard; i++) {
            if( board.getTokenAt(i,sizeOfBoard-i+1) == GameToken.N || board.getTokenAt(i,sizeOfBoard-i+1) != board.getTokenAt(1,sizeOfBoard)) {
                containsWonSequence = false;
                break;
            }
        }
        if(containsWonSequence) {
            winner = board.getTokenAt(1,sizeOfBoard );
        }

        return winner == GameToken.N ? (canPlaceMore ? GameStatus.IN_PROGRESS : GameStatus.DRAW) : (winner == GameToken.X ? GameStatus.X_WINS : GameStatus.O_WINS);
    }
}
