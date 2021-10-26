import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp () {
        p1 = new Player();
        p2 = new Player();

        p1.setColor("cyan");
        p2.setColor("yellow");

        p1.setPlayerNuber(100);
        p2.setPlayerNuber(69);

        p1.setTurn(false);
        p2.setTurn(true);

    }

    @Test
    @DisplayName("Testing set color()")
    void setColor () {
        p1.setColor("red");
        assertEquals("red", p1.getColor(), "player1 color should be red");
    }

    @Test
    @DisplayName("Testing player color")
    void getColor () {
        assertEquals("yellow", p2.getColor(), "player2 color should be yellow");
    }

    @Test
    @DisplayName("Testing setting player number")
    void setPlayerNuber () {
        p2.setPlayerNuber(420);
        assertEquals(420, p2.getPlayerNumber(), "player2 number should be updated");

    }

    @Test
    @DisplayName("Testing getting player number")
    void getPlayerNumber () {
        assertEquals(69, p2.getPlayerNumber(), "player number should stay the same as set in the setup");
    }

    @Test
    @DisplayName("Testing player's last move ")
    void getHasGone () {
        assertFalse(p1.getHasGone(),"initally player1 was set to not gone");
    }

    @Test
    @DisplayName("Testing setting player's moves")
    void setTurn () {
        p1.setTurn(true);
        assertTrue(p1.getHasGone(),"initally player1 was set to not gone");
        p2.setTurn(false);
        assertFalse(p2.getHasGone(),"initally player1 was set to not gone");
    }
}