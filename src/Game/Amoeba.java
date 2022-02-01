package Game;

import java.awt.*;

/**
 * LifeForm osztály leszármazottja
 */

public class Amoeba extends LifeForm {

    /**
     * Amoeba konstrukora, beállítja élőnek, ami az új életformák táblához adása miatt szükséges.
     * Ezenkívűl beállítja a saját színét, ami a grafikai megjelnítés miatt szükséges.
     * @param alive - boolean változó arra, hogy életben van-e
     */
    public Amoeba(boolean alive) {
        super(alive);
        if(alive)
            super.setMyGraphicColor(Color.blue);
        else
            super.setMyGraphicColor(Color.yellow);
    }
    /**
     * Megöli magát, vagy újraéled, ami a szomszédainak számától függően.
     * Az Amoeba osztály egy kis csavart visz be a játékszabályba.
     * Csak akkor éled újra ha nincsenek szomszédai.
     * Csak akkor hal meg, ha körül van véve élő LifeFormokkal.
     */
    public void AmIGoingToLive() {
        if (alive) {
            if (aliveNeighborCount == 8) {
                this.alive = false;
                super.setMyGraphicColor(Color.yellow);
            }
        } else {
            if (aliveNeighborCount == 0) {
                this.alive = true;
                super.setMyGraphicColor(Color.blue);
            }
        }
    }

    /**
     * Megszámolja, hány szomszédos cella van, amiben van élő életforma
     */
    public void countAliveNeighborLifeForms() {
        super.countAliveNeighborLifeForms();
    }

    /**
     * Kiszámítja a LifeForm szomszédait a Boardon való helyzetüktől függően
     * @param board - Board amin elhelyezkednek
     * @param x - vízszintes pozíciójuk
     * @param y - függőleges pozíciójuk
     */
    public void calculateNeighbors(Board board, int x, int y) {
        super.calculateNeighbors(board,x,y);
    }
}
