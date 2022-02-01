package Game;

import java.awt.*;

/**
 * LifeForm leszármazottja, az alapvető játékszabályokat hivatott megvalósítani
 */
public class BasicLifeForm extends LifeForm {

    /**
     * BasicLifeForm konstrukora, beállítja élőnek, ami az új életformák táblához adása miatt szükséges.
     * Ezenkívűl beállítja a saját színét, ami a grafikai megjelnítés miatt szükséges.
     * @param alive - boolean változó arra, hogy életben van-e
     */
    public BasicLifeForm(boolean alive){
        super(alive);
        if(alive)
            super.setMyGraphicColor(Color.red);
        else
            super.setMyGraphicColor(Color.yellow);
    }

    /**
     * A teszt miatt szükséges függvény, ami visszatér az elő szomszédok számával.
     * A változó az ősosztályban van definiálva.
     * @return aliveNeighborCount - élő szomszédok száma
     */
    @Override
    public int getAliveNeighborCount() {
        return super.getAliveNeighborCount();
    }

    /**
     * Megöli magát, vagy újraéled, az élő szomszédainak számától függetlenül.
     * Az alapvető játéklogikát valósítja meg.
     * Ha élő, akkor 2 vagy 3  elő szomszéd mellett marad életben.
     * Ha halott, akkor 3 élő szomszéddal újraéled.
     */
    public void AmIGoingToLive() {
        if (alive) {
            if (aliveNeighborCount != 2 && aliveNeighborCount != 3) {
                this.alive = false;
                super.setMyGraphicColor(Color.yellow);
            }
        }else{
            if (aliveNeighborCount == 3) {
                this.alive = true;
                super.setMyGraphicColor(Color.red);
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
