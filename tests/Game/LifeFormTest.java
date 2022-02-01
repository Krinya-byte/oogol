package Game;

import org.junit.*;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * LifeForm alapműveleteinek tesztelése
 */
public class LifeFormTest {
    /**
     * Új életforma egyes tesztekhez
     */
    LifeForm newLifeForm = new BasicLifeForm(true);
    /**
     * Board a tesztekhez
     */
    Board board = new Board(3,3);

    /**
     * Beállít egy csapat Lifeformot és újrainicializálja a szomszédokat a táblához
     */
    @Before
    public void setup(){
        board.getLifeFormsonBoard()[1][0] = new BasicLifeForm(true);
        board.getLifeFormsonBoard()[1][1] = new BasicLifeForm(true);
        board.getLifeFormsonBoard()[1][2] = new BasicLifeForm(true);
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                board.getLifeFormsonBoard()[x][y].calculateNeighbors(board,x,y);
            }
        }
    }

    /**
     * getMyGraphicColor tesztelése
     */
    @org.junit.Test
    public void getMyGraphicColor() {
        assertEquals(Color.red,newLifeForm.getMyGraphicColor());
    }

    /**
     * setMyGraphicColor tesztelése
     */
    @org.junit.Test
    public void setMyGraphicColor() {
        newLifeForm.setMyGraphicColor(Color.blue);
        assertEquals(Color.blue,newLifeForm.getMyGraphicColor());
    }

    /**
     * isAlive metódus tesztelése
     */
    @org.junit.Test
    public void isAlive() {
        assertTrue(newLifeForm.isAlive());
    }

    /**
     * Az élő szomszédok számának tesztelése
     */
    @org.junit.Test
    public void countNeighboringLifeForms() {
        for (int y = 0; y < 3; y++) {
            board.getLifeFormsonBoard()[1][y].countAliveNeighborLifeForms();
        }
        assertEquals(1,board.getLifeFormsonBoard()[1][0].getAliveNeighborCount());
        assertEquals(2,board.getLifeFormsonBoard()[1][1].getAliveNeighborCount());
        assertEquals(1,board.getLifeFormsonBoard()[1][2].getAliveNeighborCount());
    }

    /**
     * Megfelelő szomszédok számának a tesztelése
     */
    @org.junit.Test
    public void calculateNeighbors() {
        ArrayList<LifeForm> neighbors = (ArrayList<LifeForm>) board.getLifeFormsonBoard()[0][0].getNeighbors();
        assertEquals(neighbors.get(0),board.getLifeFormsonBoard()[1][0]);
        assertEquals(neighbors.get(1),board.getLifeFormsonBoard()[0][1]);
        assertEquals(neighbors.get(2),board.getLifeFormsonBoard()[1][1]);
    }
}