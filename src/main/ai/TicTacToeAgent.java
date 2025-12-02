package main.ai;

import main.BoardView;
import main.GameToken;
import main.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicTacToeAgent implements Player {
    private final Map<String, Double> valueFunction;
    private final AgentRepository agentRepository;
    private Move lastMove;
    private double learningRate = 0.2;
    private final GameToken agentToken;
    private final double epsilon;
    private boolean wasLastMoveRandom = false;
    private int moveCount = 0;

    public TicTacToeAgent(Map<String, Double> valueFunction, AgentRepository agentRepository, GameToken agentToken, double epsilon) {
        this.valueFunction = valueFunction;
        this.agentRepository = agentRepository;
        this.agentToken = agentToken;

        this.epsilon = epsilon;
    }

    private double getEpsilon(int gameNumber) {
        return epsilon / (1 + 0.01 * gameNumber);
    }

    private List<Move> computeNextLegalMoves(String boardState, BoardView board) {
        String stateWithoutToken = boardState.substring(2);
        List<Move> nextMoves = new ArrayList<>();
        for (int i = 1; i <= board.getSize(); i++) {
            for (int j = 1; j <= board.getSize(); j++) {
                if (board.getTokenAt(i, j) == GameToken.N) {
                    StringBuilder newState = new StringBuilder(stateWithoutToken);
                    newState.setCharAt((i - 1) * board.getSize() + (j - 1), agentToken.toString().charAt(0));
                    newState.insert(0, agentToken.toString() + "-");
                    nextMoves.add(new Move(newState.toString(), new Point(i, j)));
                }
            }
        }
        return nextMoves;
    }

    private Move getGreedyMove(List<Move> nextLegalMoves) {
        Move bestMove = null;
        double bestValue = Double.NEGATIVE_INFINITY;
        for (Move move : nextLegalMoves) {
            if (!valueFunction.containsKey(move.state)) {
                System.out.println(move.state);
            }
            double value = valueFunction.get(move.state);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        wasLastMoveRandom = false;
        return bestMove;
    }

    private Move getRandomMove(List<Move> nextLegalMoves) {
        wasLastMoveRandom = true;
        moveCount++;
        int randomIndex = (int) (Math.random() * nextLegalMoves.size());
        return nextLegalMoves.get(randomIndex);
    }

    private Move getNextMove(String boardState, BoardView board, int gameNumber) {
        List<Move> nextLegalMoves = computeNextLegalMoves(boardState, board);
        Move greedyMove = getGreedyMove(nextLegalMoves);
        nextLegalMoves.remove(greedyMove);
        double epsilonValue = getEpsilon(gameNumber);
        if (Math.random() < epsilonValue && !nextLegalMoves.isEmpty()) {
            return getRandomMove(nextLegalMoves);
        } else {
            return greedyMove;
        }

    }

    @Override
    public Point play(BoardView board, int gameNumber) {
        StringBuilder boardState = getStateString(board);
        Move move = getNextMove(boardState.toString(), board, gameNumber);
        if (move == null) {
            throw new IllegalStateException("No valid moves available");
        }
        if (lastMove != null) {
            updateLastStateValue(move);
        }
        lastMove = move;
        return move.coordinates;
    }

    private void updateLastStateValue(Move move) {
        if (wasLastMoveRandom) {
            return;
        }
        double newValue = valueFunction.get(lastMove.state) +
                learningRate * (valueFunction.get(move.state) - valueFunction.get(lastMove.state));
        valueFunction.put(lastMove.state, newValue);
//        System.out.println("Updated value for state " + lastMove.state + " to " + newValue);
        agentRepository.saveValueFunction(valueFunction);
    }

    private StringBuilder getStateString(BoardView board) {
        StringBuilder boardState = new StringBuilder();
        boardState.append(agentToken.toString());
        boardState.append("-");
        for (int i = 1; i <= board.getSize(); i++) {
            for (int j = 1; j <= board.getSize(); j++) {
                GameToken token = board.getTokenAt(i, j);
                boardState.append(token);
            }
        }
        return boardState;
    }



    @Override
    public void notifyEnd(BoardView board) {
        String finalState = getStateString(board).toString();
        updateLastStateValue(new Move(finalState, null));
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    private static class Move {
        String state;
        Point coordinates;

        public Move(String state, Point coordinates) {
            this.state = state;
            this.coordinates = coordinates;
        }
    }
}
