package main.ai;

import main.BoardView;
import main.GameToken;
import main.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicTacToeAgent implements Player {
    private final Map<String, Double> valueFunction;
    private final AgentRepository agentRepository;
    private Move lastMove;
    private double learningRate = 0.1;

    public TicTacToeAgent(Map<String, Double> valueFunction, AgentRepository agentRepository) {
        this.valueFunction = valueFunction;
        this.agentRepository = agentRepository;
    }

    private List<Move> computeNextLegalMoves(String boardState, BoardView board) {

        List<Move> nextMoves = new ArrayList<>();
        for (int i = 1; i <= board.getSize(); i++) {
            for (int j = 1; j <= board.getSize(); j++) {
                if (board.getTokenAt(i, j) == GameToken.N) {
                    StringBuilder newState = new StringBuilder(boardState);
                    newState.setCharAt((i - 1) * board.getSize() + (j - 1), 'X');
                    nextMoves.add(new Move(newState.toString(), new Point(i, j)));
                }
            }
        }
        return nextMoves;
    }

    private Move getGreedyMove(String boardState, BoardView board) {
        List<Move> nextLegalMoves = computeNextLegalMoves(boardState, board);
        Move bestMove = null;
        double bestValue = Double.NEGATIVE_INFINITY;
        for (Move move : nextLegalMoves) {
            double value = valueFunction.get(move.state);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    @Override
    public Point play(BoardView board) {
        StringBuilder boardState = getStateString(board);
        Move move = getGreedyMove(boardState.toString(), board);
        if (move == null) {
            throw new IllegalStateException("No valid moves available");
        }
        if(lastMove != null){
            updateLastStateValue(move);
        }
        lastMove = move;
        return move.coordinates;
    }

    private void updateLastStateValue(Move move) {
        double newValue = valueFunction.get(lastMove.state) +
                learningRate * (valueFunction.get(move.state) - valueFunction.get(lastMove.state));
        valueFunction.put(lastMove.state, newValue);
        System.out.println("Updated value for state " + lastMove.state + " to " + newValue);
        agentRepository.saveValueFunction(valueFunction);
    }

    private static StringBuilder getStateString(BoardView board) {
        StringBuilder boardState = new StringBuilder();
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

    private static class Move{
        String state;
        Point coordinates;

        public Move(String state, Point coordinates) {
            this.state = state;
            this.coordinates = coordinates;
        }
    }
}
