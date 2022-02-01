package Game;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * Amoeba osztály felülírt metódusának tesztelése
 */
public class AmoebaTest {
    /**
     * Board amit inicializálok a teszt előtt
     */
    Board board = new Board(3,3);
    @Test
    /**
     * Létrehozok 3 amoeba életformát
     * Majd a szomszédait is létrehozom
     * Ezután megszámolom az élő szomszédokat
     * Elvégzem a függvént
     * És letesztelem, hogy él-e még az Amoeba a szabályokhoz hűen
     */
    public void amIGoingToLive() {
        for (int y = 0; y < 3; y++) {
            board.getLifeFormsonBoard()[1][y] = new Amoeba(true);
        }
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                board.getLifeFormsonBoard()[x][y].calculateNeighbors(board,x,y);
            }
        }
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                board.getLifeFormsonBoard()[x][y].countAliveNeighborLifeForms();
            }
        }
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                board.getLifeFormsonBoard()[x][y].AmIGoingToLive();
            }
        }
        for (int x = 0; x < 3; x++) {
            assertTrue(board.getLifeFormsonBoard()[x][1].isAlive());
        }
    }
}