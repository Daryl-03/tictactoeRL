import main.BoardPrinter;
import main.ConsoleBoardPrinter;
import main.HumanPlayer;
import main.TictactoeGame;
import main.ai.AgentRepository;
import main.ai.InFileRepository;
import main.ai.StatesGenerator;
import main.ai.TicTacToeAgent;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BoardPrinter boardPrinter = new ConsoleBoardPrinter();
        StatesGenerator statesGenerator = new StatesGenerator(3);

        AgentRepository agentRepository = new InFileRepository();

        Map<String, Double> states = agentRepository.readValueFunction();
        System.out.println("States loaded from repository: " + (states != null ? states.size() : 0));
        if(states == null || states.isEmpty()){
            states = statesGenerator.generateStates();
        }

        TicTacToeAgent agentX = new TicTacToeAgent(states, agentRepository);

        TictactoeGame game = new TictactoeGame(3, agentX, new HumanPlayer(sc), boardPrinter);
        game.launchGame();

    }
}
