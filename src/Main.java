import main.*;
import main.ai.AgentRepository;
import main.ai.InFileRepository;
import main.ai.StatesGenerator;
import main.ai.TicTacToeAgent;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        launchGameAgainstHuman(sc);
//        launchTrainingGames(50000);
    }

    private static void launchGameAgainstHuman(Scanner sc) {
        BoardPrinter boardPrinter = new ConsoleBoardPrinter();
        StatesGenerator statesGenerator = new StatesGenerator(3);

        TicTacToeAgent agentX = getTicTacToeAgent(statesGenerator, GameToken.X, InFileRepository.DEFAULT_FILE_PATH);
        HumanPlayer humanPlayer = new HumanPlayer(sc);

        TictactoeGame game = new TictactoeGame(3, agentX, humanPlayer, boardPrinter);
        game.launchGame();
    }

    private static void launchTrainingGames(int numberOfGames) {
        BoardPrinter boardPrinter = new VoidBoardPrinter();
        StatesGenerator statesGenerator = new StatesGenerator(3);

        TicTacToeAgent agentX = getTicTacToeAgent(statesGenerator, GameToken.X, InFileRepository.DEFAULT_FILE_PATH);
        TicTacToeAgent agentO = getTicTacToeAgent(statesGenerator, GameToken.O, InFileRepository.DEFAULT_FILE_PATH);

        TictactoeGame game = new TictactoeGame(3, agentX, agentO, boardPrinter);
        game.playMultipleGames(numberOfGames);
    }

    private static TicTacToeAgent getTicTacToeAgent(StatesGenerator statesGenerator, GameToken token, String filePath) {
        AgentRepository agentRepository = new InFileRepository(filePath);

        Map<String, Double> states = agentRepository.readValueFunction();
        System.out.println("States loaded from repository: " + (states != null ? states.size() : 0));
        if (states == null || states.isEmpty()) {
            states = statesGenerator.generateStates();
        }
        return new TicTacToeAgent(states, agentRepository, token, 0.1);
    }
}
