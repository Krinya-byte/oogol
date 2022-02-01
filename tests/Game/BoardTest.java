package Game;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Board osztály nem lefedett metódusának tesztelése
 * initialize Boardot nem teszteltem mivel az főképp más függvéynekből áll össze
 */
public class BoardTest {

    /**
     * Létrehozok egy Boardot a teszthez
     */
    Board board = new Board(3,3);

    /**
     * getWidth metódus tesztelése
     */
    @Test
    public void getWidth() {
        assertEquals(3,board.getWidth());
    }

    /**
     * getHeight metódus tesztelése
     */
    @Test
    public void getHeight() {
        assertEquals(3,board.getHeight());
    }

}