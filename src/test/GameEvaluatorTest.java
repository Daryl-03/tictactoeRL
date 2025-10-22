package test;

    import main.GameEvaluator;
    import main.GameStatus;
    import main.GameToken;
    import main.BoardView;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.assertEquals;

    public class GameEvaluatorTest {

        private GameEvaluator evaluator;

        @BeforeEach
        void setUp() {
            evaluator = new GameEvaluator();
        }

        @Test
        void testXWinsWithFirstRow() {
            BoardView board = new BoardView() {
                public GameToken getTokenAt(int i, int j) { return i == 1 ? GameToken.X : GameToken.N; }
                public int getSize() { return 3; }
            };
            assertEquals(GameStatus.X_WINS, evaluator.evaluateBoard(3, board));
        }

        @Test
        void testOWinsWithSecondColumn() {
            BoardView board = new BoardView() {
                public GameToken getTokenAt(int i, int j) { return j == 2 ? GameToken.O : GameToken.N; }
                public int getSize() { return 3; }
            };
            assertEquals(GameStatus.O_WINS, evaluator.evaluateBoard(3, board));
        }

        @Test
        void testXWinsWithMainDiagonal() {
            BoardView board = new BoardView() {
                public GameToken getTokenAt(int i, int j) { return i == j ? GameToken.X : GameToken.N; }
                public int getSize() { return 3; }
            };
            assertEquals(GameStatus.X_WINS, evaluator.evaluateBoard(3, board));
        }

        @Test
        void testOWinsWithAntiDiagonal() {
            BoardView board = new BoardView() {
                public GameToken getTokenAt(int i, int j) { return i + j == 4 ? GameToken.O : GameToken.N; }
                public int getSize() { return 3; }
            };
            assertEquals(GameStatus.O_WINS, evaluator.evaluateBoard(3, board));
        }

        @Test
        void testGameInProgress() {
            BoardView board = new BoardView() {
                public GameToken getTokenAt(int i, int j) {
                    if (i == 1 && j == 1) return GameToken.X;
                    if (i == 2 && j == 1) return GameToken.O;
                    return GameToken.N;
                }
                public int getSize() { return 3; }
            };
            assertEquals(GameStatus.IN_PROGRESS, evaluator.evaluateBoard(3, board));
        }

        @Test
        void testDraw() {
            BoardView board = new BoardView() {
                public GameToken getTokenAt(int i, int j) {
                    GameToken[][] grid = {
                        {GameToken.X, GameToken.O, GameToken.X},
                        {GameToken.O, GameToken.X, GameToken.O},
                        {GameToken.O, GameToken.X, GameToken.O}
                    };
                    return grid[i-1][j-1];
                }
                public int getSize() { return 3; }
            };
            assertEquals(GameStatus.DRAW, evaluator.evaluateBoard(3, board));
        }
    }