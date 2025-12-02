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
        generateStatesRecursively(grid, isXTurn, GameToken.X);
        states.remove(boardToString(grid, GameToken.X));
        generateStatesRecursively(grid, isXTurn, GameToken.O);
        states.remove(boardToString(grid, GameToken.O));
        System.out.println("Total states generated: " + states.size());
        return states;
    }

    private void generateStatesRecursively(GameToken[][] gameTokens, boolean isXTurn, GameToken toPlay) {

        String stateKey = boardToString(gameTokens, toPlay);

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

        double value = getValueBasedOnPlayer(status, toPlay);
        states.put(stateKey, value);
        count++;

        if (status != GameStatus.IN_PROGRESS) {
            return;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(gameTokens[i][j] == GameToken.N){
                    gameTokens[i][j] = getNextToken(isXTurn);
                    generateStatesRecursively(gameTokens, !isXTurn, toPlay);
                    gameTokens[i][j] = GameToken.N;
                }
            }
            
        }
    }

    private static double getValueBasedOnPlayer(GameStatus status, GameToken toPlay) {
        double value = switch (status) {
            case X_WINS -> toPlay == GameToken.X ? 1 : 0;
            case O_WINS -> toPlay == GameToken.O ? 1 : 0;
            case DRAW -> 0.5;
            case IN_PROGRESS -> 0.5;
        };
        return value;
    }

    private String boardToString(GameToken[][] board, GameToken toPlay) {
        StringBuilder sb = new StringBuilder();
        sb.append(toPlay.toString());
        sb.append("-");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(board[i][j].toString());
            }
        }
        return sb.toString();
    }

//    private static List<String> generateAllSymmetricStates(String state, int size) {
//        List<String> symmetricStates = new ArrayList<>();
//        char toPlay = state.charAt(0);
//        String boardState = state.substring(2);
//
//        char[][] board = new char[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                board[i][j] = boardState.charAt(i * size + j);
//            }
//        }
//
//        for (int i = 0; i < 4; i++) {
//            board = rotateBoard(board, size);
//            symmetricStates.add(toPlay + "-" + boardToString(board, size));
//            char[][] reflectedBoard = reflectBoard(board, size);
//            symmetricStates.add(toPlay + "-" + boardToString(reflectedBoard, size));
//        }
//
//        return symmetricStates;
//    }
}
