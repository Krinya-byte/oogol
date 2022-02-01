package GUI;

import java.awt.*;

/**
 * A négyzeteket megvalósító osztály
 */
public class MyRectangle extends Rectangle {
    /**
     * A négyzet színe
     */
    private Color color;

    /**
     * A részben felülírt konstruktor
     * @param x - x pozíció
     * @param y - y pozíció
     * @param width - szélesség
     * @param height - magasság
     * @param color - alapszín
     */
    public MyRectangle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    /**
     * Beállítja a színét
     * @param color - négyzet színe
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Megfesti magát
     * @param g2d - átvett grafika
     */
    public void paint(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.draw(this);
        g2d.setColor(this.color);
        g2d.fill(this);

    }
}
