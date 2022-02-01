package Game;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Absztrakt LifeForm ősosztály
 */
public abstract class LifeForm implements Serializable {
    /**
     * Az élő szomszédok száma
     * Szükséges számon tartani, ehhez kapcsolódnak az alapvető játékszabályok
     */
    int aliveNeighborCount;
    /**
     * Eldönti, hogy egy LifeForm élő vagy halott-e.
     * boolean volt szerintem a legyegyszerűbb módzser ennek a reprezentálására.
     * Az alapvető specifikációban lévő játékszabályokat is így tudtam a legegyszerűbben megvalósítani
     */
    boolean alive;
    /**
     * A LifeForm szomszédos életformái.
     * ArrayListben tárolom az egyszerűség kedvéért
     */
    ArrayList<LifeForm> neighbors = new ArrayList<>();
    /**
     * Az adott LifeForm színe, ami a megjelenítés miatt lényeges
     */
    private Color myGraphicColor;

    /**
     * Visszaadja a szomszédok listáját
     * @return neighbors - a szomszédos életformák
     */
    public ArrayList<LifeForm> getNeighbors() {
        return neighbors;
    }

    /**
     * Visszaadja az élő szomszédok számát
     * @return aliveNeigborCount - élő életformák száma
     */
    public int getAliveNeighborCount() {
        return aliveNeighborCount;
    }

    /**
     * Visszaadja a színét az adott életformának
     * @return myGraphicColor - szín amit visszaad
     */
    public Color getMyGraphicColor() {
        return myGraphicColor;
    }

    /**
     * Beállítja a LifeForm színét
     * @param myGraphicColor - szín amit átvesz
     */
    void setMyGraphicColor(Color myGraphicColor) {
        this.myGraphicColor = myGraphicColor;
    }

    /**
     * LifeForm konstruktora, ami beállítja az állapotát élőre, vagy halottra.
     * A pályaszerkeztő és az életformák általános hozzáadása miatt tartom szükségesnek
     * @param alive - boolean ami megmutatja, hogy élő vagy halott legyen az életforma
     */
    public LifeForm(boolean alive){this.alive = alive;}

    /**
     * Visszaadja a boolean-t, hogy él-e a LifeForm
     * A grafikai megjelenítés és a játéklogika miatt lényeges
     * @return alive - a visszaadott érték, hogy élő vagy halott-e a LifeForm
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kiszámítja, hogy a következő körben élő vagy halott lesz-e.
     * Csak a származtatott osztályokban van ez a függvény megvalósítva.
     * Attól függetlenül minden származtatott osztálynak szükséges tartalmaznia.
     */
    public  void AmIGoingToLive(){}

    /**
     * Megszámolja, hány szomszédos élő LifeForm van.
     * void típusú a tanácsokra hallgatva
     */
    public void countAliveNeighborLifeForms(){
        aliveNeighborCount = 0;
        for (LifeForm neighborLifeForm : neighbors) {
            if (neighborLifeForm.isAlive())
                aliveNeighborCount += 1;
        }
    }

    /**
     * Kiszámítja a LifeForm szomszédait a Boardon való helyzetüktől függően.
     * Az elején egy törlést végzek a szomszédok listáján, hogy ne forduljon elő duplikáció
     * @param board - Board amin elhelyezkednek
     * @param x - vízszintes pozíciójuk
     * @param y - függőleges pozíciójuk
     */
    public void calculateNeighbors(Board board, int x, int y) {
        neighbors.removeAll(neighbors);
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (i >= 0 && i < board.getHeight()  && j >= 0 && j <board.getWidth()) {
                    if (j != x || i != y) {
                        neighbors.add(board.getLifeFormsonBoard()[j][i]);
                    }
                }
            }
        }
    }
}
