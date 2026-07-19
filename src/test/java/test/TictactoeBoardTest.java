package test;

import main.game.TictactoeBoard;
import main.game.GameToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TictactoeBoardTest {

    private TictactoeBoard board;

    @BeforeEach
    void setUp() {
        board = new TictactoeBoard(3);
    }

    @Test
    void testBoardInitialization() {
        assertEquals(3, board.getSize());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(GameToken.N, board.getTokenAt(i+1, j+1));
            }
        }
    }

    @Test
    void testMakeValidMove() {
        assertTrue(board.placeValue(GameToken.X, 1, 1));
        assertEquals(GameToken.X, board.getTokenAt(1, 1));
    }

    @Test
    void testMakeInvalidMove() {
        board.placeValue(GameToken.X, 1, 1);
        assertFalse(board.placeValue(GameToken.O, 1, 1));
    }

    @Test
    void testMakeMoveOutOfBounds() {
        assertFalse(board.placeValue(GameToken.X, -2, 1));
        assertFalse(board.placeValue(GameToken.O, 0, 3));
        assertFalse(board.placeValue(GameToken.X, 4, 3));
    }



    @Test
    void testIsFull() {
        assertTrue(board.canPlaceMore());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.placeValue((i + j) % 2 == 0 ? GameToken.X : GameToken.O, i+1, j+1);
            }
        }

        assertFalse(board.canPlaceMore());
    }

    @Test
    void testGetGrid() {
        board.placeValue(GameToken.X, 1, 1);
        board.placeValue(GameToken.O, 2, 2);

        assertEquals(GameToken.X, board.getTokenAt(1,1));
        assertEquals(GameToken.O, board.getTokenAt(2,2));
        assertEquals(GameToken.N, board.getTokenAt(3,3));
    }
}
