package main.player.ai;

import main.game.BoardView;
import main.game.GameToken;
import main.game.Move;
import main.game.StateSerializer;
import main.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicTacToeAgent implements Player {
    private final Map<String, Double> valueFunction;
    private final AgentRepository agentRepository;
    private CandidateMove lastMove;
    private double learningRate = 0.5;
    private final GameToken agentToken;
    private final double epsilon;

    public TicTacToeAgent(Map<String, Double> valueFunction, AgentRepository agentRepository, GameToken agentToken, double epsilon) {
        this.valueFunction = valueFunction;
        this.agentRepository = agentRepository;
        this.agentToken = agentToken;

        this.epsilon = epsilon;
    }

    private double getEpsilon(int gameNumber) {
        return epsilon ;
    }

    private List<CandidateMove> computeNextLegalMoves(String boardState, BoardView board) {
        String stateWithoutToken = boardState.substring(2);
        List<CandidateMove> nextMoves = new ArrayList<>();
        for (int i = 1; i <= board.getSize(); i++) {
            for (int j = 1; j <= board.getSize(); j++) {
                if (board.getTokenAt(i, j) == GameToken.N) {
                    StringBuilder newState = new StringBuilder(stateWithoutToken);
                    newState.setCharAt((i - 1) * board.getSize() + (j - 1), agentToken.toString().charAt(0));
                    newState.insert(0, agentToken + "-");
                    nextMoves.add(new CandidateMove(newState.toString(), new Move(i, j)));
                }
            }
        }
        return nextMoves;
    }

    private CandidateMove getGreedyMove(List<CandidateMove> nextLegalMoves) {
        CandidateMove bestMove = null;

        double bestValue = Double.NEGATIVE_INFINITY;
        for (CandidateMove move : nextLegalMoves) {
            if (!valueFunction.containsKey(move.state)) {

                throw new IllegalStateException("Missing value for state: " + move.state);
            }
            double value = valueFunction.get(move.state);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        bestMove.setRandom(false);
        return bestMove;
    }

    private CandidateMove getRandomMove(List<CandidateMove> nextLegalMoves) {

        int randomIndex = (int) (Math.random() * nextLegalMoves.size());
        CandidateMove move = nextLegalMoves.get(randomIndex);
        move.setRandom(true);
        return move;
    }

    private CandidateMove getNextMove(String boardState, BoardView board, int gameNumber) {
        List<CandidateMove> nextLegalMoves = computeNextLegalMoves(boardState, board);
        CandidateMove greedyMove = getGreedyMove(nextLegalMoves);
        nextLegalMoves.remove(greedyMove);
        double epsilonValue = getEpsilon(gameNumber);
        if (Math.random() < epsilonValue && !nextLegalMoves.isEmpty()) {
            return getRandomMove(nextLegalMoves);
        } else {
            return greedyMove;
        }

    }

    @Override
    public Move play(BoardView board, int gameNumber) {
        StringBuilder boardState = getStateString(board);
        CandidateMove move = getNextMove(boardState.toString(), board, gameNumber);
        if (move == null) {
            throw new IllegalStateException("No valid moves available");
        }
        if (lastMove != null) {
            updateLastStateValue(move);
        }
        lastMove = move;
        return move.coordinates;
    }

    private void updateLastStateValue(CandidateMove move) {
        if (move.isRandom) {
            return;
        }
        double newValue = valueFunction.get(lastMove.state) +
                learningRate * (valueFunction.get(move.state) - valueFunction.get(lastMove.state));
        valueFunction.put(lastMove.state, newValue);
    }

    private StringBuilder getStateString(BoardView board) {
        return new StringBuilder(StateSerializer.toKey(board, agentToken));
    }



    @Override
    public void notifyEnd(BoardView board) {
        String finalState = getStateString(board).toString();
        updateLastStateValue(new CandidateMove(finalState, null));
        agentRepository.saveValueFunction(valueFunction);
        this.lastMove =  null;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    private static class CandidateMove {
        String state;
        Move coordinates;
        boolean isRandom;

        public CandidateMove(String state, Move coordinates) {
            this.state = state;
            this.coordinates = coordinates;
        }

        public void setRandom(boolean random) {
            isRandom = random;
        }
    }
}
