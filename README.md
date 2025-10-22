# Tic-Tac-Toe with Reinforcement Learning

This project is a Java implementation of a Tic-Tac-Toe game where an agent learns to play using reinforcement learning techniques. The approach is inspired by the concepts described in the book "Reinforcement Learning: An Introduction" by Richard Sutton and Andrew Barto.

## Description

The project features an agent that can learn the best strategies to win at Tic-Tac-Toe. 
The agent refines its policy by updating a value function for each possible game state.
The value function is a mapping from game states to their estimated win probabilities, which the agent uses to make decisions during gameplay.
The updated value function is stored in a text file (`value_function.txt`), allowing the agent to retain its knowledge across sessions.

## Project Structure

*   `src/Main.java`: The main entry point to run the application.
*   `src/main/`: Contains the core game logic and player representations.
    *   `TictactoeGame.java`, `TictatctoeBoard.java`: Core logic for the Tic-Tac-Toe game.
    *   `HumanPlayer.java`: Represents the human player.
    *   `GameEvaluator.java`, `BoardPrinter.java`, etc.: Utility and helper classes for the game.
*   `src/main/ai/`: Contains the artificial intelligence logic.
    *   `TicTacToeAgent.java`: The agent that learns and makes decisions.
    *   `StatesGenerator.java`: Utility to generate all possible game states.
    *   `InFileRepository.java`: Manages reading and writing the value function to a file.
*   `src/test/`: Contains unit tests for the project.
*   `value_function.txt`: Text file storing the agent's "knowledge". It maps a value (win probability) to each game board state.

The program will prompt you to make your move by choosing a cell coordinate (row and column) between 1 and 3.
The agent will then use its value function to choose the best possible move.

## Milestones
-[X] Implemented the Tic-Tac-Toe game logic and user interface.
-[X] Developed the Tic-Tac-Toe agent with reinforcement learning capabilities (the agent can only play as 'X' for now).
-[X] Created a repository to store and retrieve the value function from a text file.
-[X] The learning rate is reduced over time to allow the agent to stabilize its learning.
-[X] Added functionality to save the value function to a file after each game.
-[] The agent can play as 'O' as well.

## Future Improvements

- Enable agent vs. agent self-play
- Add exploration strategies
- Integration with a plotting tool to visualize training progress

## References

Sutton, R. S., & Barto, A. G. (2018). Reinforcement Learning: An Introduction (2nd ed.). http://incompleteideas.net/book/RLbook2020.pdf