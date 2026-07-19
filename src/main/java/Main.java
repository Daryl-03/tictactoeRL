import main.player.ai.AgentRepository;
import main.player.ai.InFileRepository;
import main.player.ai.StatesGenerator;
import main.player.ai.TicTacToeAgent;
import main.game.GameEvaluator;
import main.game.GameToken;
import main.game.TictactoeGame;
import main.player.HumanPlayer;
import main.player.RandomPlayer;
import main.ui.BoardPrinter;
import main.ui.ConsoleBoardPrinter;
import main.ui.VoidBoardPrinter;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        launchGameAgainstHuman(sc);
//        launchTrainingGames(3000);
    }

    private static void launchGameAgainstHuman(Scanner sc) {
        BoardPrinter boardPrinter = new ConsoleBoardPrinter();
        StatesGenerator statesGenerator = new StatesGenerator(3);
        GameEvaluator gameEvaluator = new GameEvaluator();

        TicTacToeAgent agentX = getTicTacToeAgent(statesGenerator, GameToken.X, InFileRepository.DEFAULT_FILE_PATH, 0);
        HumanPlayer humanPlayer = new HumanPlayer(sc);

        TictactoeGame game = new TictactoeGame(3, agentX ,humanPlayer, boardPrinter, gameEvaluator);
        game.launchGame();
    }

    private static void launchTrainingGames(int numberOfGames) {
        BoardPrinter boardPrinter = new VoidBoardPrinter();
        StatesGenerator statesGenerator = new StatesGenerator(3);
        GameEvaluator gameEvaluator = new GameEvaluator();

        RandomPlayer agentO = new RandomPlayer();
        TicTacToeAgent agentX = getTicTacToeAgent(statesGenerator, GameToken.X, InFileRepository.DEFAULT_FILE_PATH, 0.1);
//        TicTacToeAgent agentO = getTicTacToeAgent(statesGenerator, GameToken.O, InFileRepository.DEFAULT_FILE_PATH, 0.01);

        TictactoeGame game = new TictactoeGame(3, agentX, agentO, boardPrinter, gameEvaluator);
        game.playMultipleGames(numberOfGames);
    }

    private static TicTacToeAgent getTicTacToeAgent(StatesGenerator statesGenerator, GameToken token, String filePath, double epsilon) {
        AgentRepository agentRepository = new InFileRepository(filePath);

        Map<String, Double> states = agentRepository.readValueFunction();
        System.out.println("States loaded from repository: " + (states != null ? states.size() : 0));
        if (states == null || states.isEmpty()) {
            states = statesGenerator.generateStates();
        }
        return new TicTacToeAgent(states, agentRepository, token, epsilon);
    }
}
