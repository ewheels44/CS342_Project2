import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDataTest {

    private GameData gd;

    @BeforeEach
    void setUp () {
        gd = new GameData();
    }

    @Test
    @DisplayName("Testing isWonGame()")
    void isWonGame () {
        gd.setWonGame(true);
        assertTrue(gd.isWonGame(), "this should be true");
        gd.setWonGame(false);
        assertFalse(gd.isWonGame(), "this should be false");
    }

    @Test
    @DisplayName("Testing setWonGame()")
    void setWonGame () {
        gd.setWonGame(true);
        assertTrue(gd.isWonGame(), "this should be true");
        gd.setWonGame(false);
        assertFalse(gd.isWonGame(), "this should be false");
    }

    @Test
    @DisplayName("Testing getTotalPieces ()")
    void getTotalPieces () {
        assertEquals(0, gd.getTotalPieces(), "Initally the number of total pieces should be zero");
        gd.incTotalpices();
        assertEquals(1, gd.getTotalPieces(), "Increment the number of total pieces");

    }

    @Test
    @DisplayName("Testing incTotalpices ()")
    void incTotalpices () {
        gd.incTotalpices();
        assertEquals(1, gd.getTotalPieces(), "Increment the number of total pieces");
    }

    @Test
    @DisplayName("Testing decTotalpieces ()")
    void decTotalpieces () {
        gd.incTotalpices();
        gd.incTotalpices();
        gd.decTotalpieces();
        assertEquals(1, gd.getTotalPieces(), "decrement the number of total pieces");
    }

    @Test
    @DisplayName("Testing getWhosTurn ()")
    void getWhosTurn () {
        assertEquals("B", gd.getWhosTurn(), "Initially player one will make the move");
    }

    @Test
    @DisplayName("Testing getNextTurn ()")
    void getNextTurn () {
        assertEquals("B", gd.getNextTurn(), "Blue will go first");
    }
}