package Game;

import static org.junit.Assert.assertTrue;

/**
 * Amoeba osztály felülírt metódusának tesztelése
 */
public class BasicLifeFormTest {

    /**
     * Board létrehozása teszteléshez
     */
    Board board = new Board(3,3);

    /**
     * Létrehozok 3 BasicLifeForm életformát
     * Majd a szomszédait is létrehozom
     * Ezután megszámolom az élő szomszédokat
     * Elvégzem a függvént
     * És letesztelem, hogy él-e még a BasicLifeForm a szabályokhoz hűen
     */
    @org.junit.Test
    public void amIGoingToLive() {
        for (int y = 0; y < 3; y++) {
            board.getLifeFormsonBoard()[1][y] = new BasicLifeForm(true);
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