package Game;

import Game.*;


import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * Board amin a LifeFormok elhelyezkednek.
 * Beolvasás miatt implementálja a Serializable classot.
 */
public class Board implements Serializable{
    /**
     * Board szélessége
     */
    private int width;
    /**
     * Board magassága
     */
    private int height;
    /**
     * Életformák tömbje a Boardon
     */
    private LifeForm[][] lifeFormsonBoard;

    /**
     * Visszatér a Board szélességével
     * @return width - szélesség
     */
    public int getWidth() {
        return width;
    }

    /**
     * Visszatér a Board magasságával
     * @return height - magasság
     */
    public int getHeight() {
        return height;
    }

    /**
     * Visszatér a Boardon lévő LifeFormok tömbjével
     * @return a LifeFormok tömbje
     */
    public LifeForm[][] getLifeFormsonBoard() {

        return lifeFormsonBoard;
    }

    /**
     * Konstruktor inicializáláshoz.
     *
     * @param width szélessége a táblának
     * @param height magassága a táblának
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.lifeFormsonBoard = new LifeForm[width][height];
        initializeBoard();
    }

    /**
     * Inicializálja a tábla elemeit
     */
    public void initializeBoard() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                lifeFormsonBoard[x][y] = new BasicLifeForm(false);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                lifeFormsonBoard[x][y].calculateNeighbors(this, x, y);
            }
        }
    }

}
