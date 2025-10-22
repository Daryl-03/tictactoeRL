package main.ai;

import main.BoardView;
import main.GameEvaluator;
import main.GameStatus;
import main.GameToken;

import java.util.*;

public class StatesGenerator {
    Map<String, Double> states;
    final int size;
    GameEvaluator gameEvaluator = new GameEvaluator();
    int count = 0;
    Set<String> visitedStates = new HashSet<>();

    public StatesGenerator(int size) {
        this.size = size;
        this.states = new LinkedHashMap<>();
    }
    
    private GameToken getNextToken(boolean isXTurn){
        return isXTurn ? GameToken.X : GameToken.O;
    }

    public Map<String, Double> generateStates(){
        boolean isXTurn = true;
        GameToken[][] grid = new GameToken[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = GameToken.N;
            }
        }
        generateStatesRecursively(grid, isXTurn);
        states.remove(boardToString(grid));
        System.out.println("Total states generated: " + states.size());
        return states;
    }

    private void generateStatesRecursively(GameToken[][] gameTokens, boolean isXTurn) {

        String stateKey = boardToString(gameTokens);

        if(visitedStates.contains(stateKey)){
            return;
        } else {
            visitedStates.add(stateKey);
        }

        BoardView boardView = new BoardView() {
            @Override
            public GameToken getTokenAt(int line, int col) {
                return gameTokens[line-1][col-1];
            }

            @Override
            public int getSize() {
                return size;
            }
        };
        GameStatus status = gameEvaluator.evaluateBoard(size, boardView);

        double value = switch (status) {
            case X_WINS -> 1;
            case O_WINS, DRAW -> 0;
            case IN_PROGRESS -> 0.5;
        };
        states.put(stateKey, value);
        count++;

        if (status != GameStatus.IN_PROGRESS) {
            return;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameTokens[i][j] == GameToken.N){
                    gameTokens[i][j] = getNextToken(isXTurn);
                    generateStatesRecursively(gameTokens, !isXTurn);
                    gameTokens[i][j] = GameToken.N;
                }
            }
            
        }
    }

    private String boardToString(GameToken[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i][j].toString());
            }
        }
        return sb.toString();
    }
}
