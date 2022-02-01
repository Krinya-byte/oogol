package GUI;

import Game.*;
import IO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;

/**
 * A játákot megjelenítú panel osztálya
 */
public class NewGamePanel extends JPanel {

    /**
     *  A négyzeteket tartalmazó 2d-s tömb
     */
    private MyRectangle[][] rectangles;
    /**
     * Az oszlopok száma, láthatóság érdekében
     */
    private int columnCount;
    /**
     * A sorok száma láthatóság érdekében
     */
    private int rowCount;
    /**
     * A játéktábla
     */
    private Board gameBoard;
    /**
     * Generációk számlálója
     */
    private int generationcounter;
    /**
     *Élő BasicLifeFormok számlálója
     */
    private int aliveBasicLifeFormcounter;
    /**
     * Élő Amoeba-k számlálója
     */
    private int aliveAmoebacounter;
    /**
     * Boolean, hogy a Start gomb át tudja változtatni magát Stop gombra
     */
    private boolean startState = false;
    /**
     * Stringtömb, hogy ki tudja választani a játékos az alakzatokat
     */
    private String[] lifeForms = {"Blinker", "Toad", "Beacon"};
    /**
     * JComboBox a különböző kezdőalakzatok kiválasztására
     */
    private JComboBox lifeFormComboBox;
    /**
     * Load Gomb
     */
    private JMenuItem load;
    /**
     * Start gomb
     */
    private JMenuItem start;
    /**
     * A Timer a folyamatos szimuláció végzéséhez
     */
    private Timer timer;
    /**
     *  JLabel a különbüző adatok megjelenítéséhez
     */
    private JLabel dataMonitoring;

    /**
     * A NewGamePanel-t megvalósító osztály.
     * Panel mérete fixen beállítva, elemek is hozzá vannak igazítva.
     * Gomboknál ActionListenerek itt is külön lettek megvalósítva, lambdát csak rövidebbeknél használtam
     *
     * @param cardLayout - CardLayout, hogy vissza lehessen lépni a menübe
     */
    public NewGamePanel(CardLayout cardLayout) {
        setPreferredSize(new Dimension(870, 600));
        setLayout(new BorderLayout());
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Game Menu");
        jMenuBar.add(jMenu);
        add(jMenuBar,BorderLayout.PAGE_START);

        gameBoard = new Board(10, 10);
        this.columnCount = gameBoard.getWidth();
        this.rowCount = gameBoard.getHeight();

        rectangles = new MyRectangle[columnCount][rowCount];
        initializeRectangles();

        this.dataMonitoring  = new JLabel();
        dataMonitoring.setText(" Generations: " + generationcounter + " Alive BasicLifeForms: " + aliveBasicLifeFormcounter + " Alive Amoebas: " + aliveAmoebacounter);
        add(dataMonitoring, BorderLayout.LINE_END);

        this.lifeFormComboBox = new JComboBox(lifeForms);
        add(lifeFormComboBox,BorderLayout.PAGE_END);
        lifeFormComboBox.addActionListener(new NewGamePanel.JComboboxListener());

        this.load = new JMenuItem("Load");
        jMenu.add(load);
        load.addActionListener(new NewGamePanel.Load());

        this.timer = new Timer(1000, new Stepping());

        this.start = new JMenuItem("Start");
        jMenu.add(start);
        start.addActionListener(new Start());

        JMenuItem goBack = new JMenuItem("Back");
        jMenu.add(goBack);
        goBack.addActionListener(e -> cardLayout.show(GUIManager.getInstance().containerPanel,"Menu"));

    }

    /**
     * A négyzeteket inicializálja rendezi, jelenlegi méretekhez igazítva
     */
    private void initializeRectangles() {
        int cellWidth = 500 / rowCount;
        int cellHeight = 500 / columnCount;
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                rectangles[x][y] = new MyRectangle((x + 1) * cellWidth - 25, (y + 1) * cellHeight - 10, 500 / columnCount - 2, 500 / rowCount - 2,Color.yellow);
            }
        }
    }

    /**
     * Graphics felület paintComponent függvénye, ez hívja meg minden egyes négyzet paint metódusát
     * @param g - grafika amit át kell vennie
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                rectangles[x][y].paint(g2d);
            }
        }
    }

    /**
     * A folyamatos szimulációt megvalósító ActionListener.
     * Két for ciklusra bontom mindig a szimulációt.
     * Először a LifeFormok megszámolják az élő szomszédaikat.
     * Utána eldöntik, hogy újraélednek, vagy meghalnak-e.
     * Eközben monitorozom folyamatosan az élő LifeFormokat és generációk számát
     */
    class Stepping implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            generationcounter = 0;

            for (int y = 0; y < gameBoard.getHeight(); y++) {
                for (int x = 0; x < gameBoard.getWidth(); x++) {
                    gameBoard.getLifeFormsonBoard()[x][y].countAliveNeighborLifeForms();
                }
            }

            for (int y = 0; y < gameBoard.getHeight(); y++) {
                for (int x = 0; x < gameBoard.getWidth(); x++) {
                    gameBoard.getLifeFormsonBoard()[x][y].AmIGoingToLive();
                }
            }
            paintAndCountDifferentLifeForms();
        }
    }

    /**
     * Különválasztott metódus mivel a bármilyen alakzat betöltésénél is el kell végezni.
     * Ez a metódus festi ki a négyzeteket és számolja meg a különböző elő lifeFormokat.
     * LifeFormokat a színúk alapján különbözteti meg
     */
    private void paintAndCountDifferentLifeForms(){
        aliveAmoebacounter = 0;
        aliveBasicLifeFormcounter = 0;
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                MyRectangle tempRec = rectangles[x][y];
                LifeForm tempLifeForm = gameBoard.getLifeFormsonBoard()[x][y];
                    tempRec.setColor(tempLifeForm.getMyGraphicColor());
            }
        }
        repaint();
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                LifeForm tempLifeForm = gameBoard.getLifeFormsonBoard()[x][y];
                    if(tempLifeForm.isAlive()) {
                        if (tempLifeForm.getMyGraphicColor().equals(Color.BLUE)) {
                            aliveAmoebacounter++;
                        } else {
                            aliveBasicLifeFormcounter++;
                        }
                    }
            }
        }
        generationcounter++;
        dataMonitoring.setText(" Generations: " + generationcounter + " Alive BasicLifeForms: " + aliveBasicLifeFormcounter + " Alive Amoebas: " + aliveAmoebacounter);
    }

    /**
     * Start gombot megvalósító ActionListener
     */
    private class Start implements ActionListener {

        /**
         * Start gomb átváltozik Stop gombbá miután elindult a timer.
         * Majd ugyanezzel a gombbal ehet megállítani a szimulációt
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(startState == true) {
                startState = false;
                timer.stop();
                start.setText("Start");
            }else{
                timer.start();
                startState = true;
                start.setText("Stop");
            }
        }
    }

    /**
     * Load gombot megvalósító ActionListener
     */
    private class Load implements ActionListener {

        /**
         * Létrehozok egy filechoosert beállítva a resources mappára.
         * Majd betöltöm a txt fileból a gameBoardba a jelenlegi Board-ot.
         * Itt hívódik meg a paintAndCountDifferentLifeForms metódus.
         * A repaint()-et is szükséges vagyok meghívni az alakzatok látszódása miatt.
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(new
                    File(System.getProperty("user.dir"),"resources"));
            int response = chooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                try {
                    FileAdapter fileadapter = new FileAdapter();
                    gameBoard = fileadapter.load(gameBoard,String.valueOf(chooser.getSelectedFile()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            generationcounter = 0;
            paintAndCountDifferentLifeForms();
        }
    }

    /**
     * JComboboxot megvalósító ActionListener
     */
    private class JComboboxListener implements ActionListener {

        /**
         * Meghívja a createBasicLifeForms metódust, aminek átadja a kiválasztott alakzat elnevezését.
         * @param e -  ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            createBasicLifeForms(Objects.requireNonNull(lifeFormComboBox.getSelectedItem()).toString());
        }
    }

    /**
     * Létrehoz különböző kezdőalakzatokat, amiket a játékos választhat ki.
     * Itt is meghívódik a paintAndCountDifferentLifeForms metódus.
     * Az elején szükséges inicializálni a Boardot, és négyzeteket.
     * Íyg a nem fognak a kezdőalakzatok egyszerre ott lenni a Boardon
     * @param LifeForms - string amit átvesz
     */
    void createBasicLifeForms(String LifeForms) {
        generationcounter = 0;
        gameBoard.initializeBoard();
        initializeRectangles();
        switch (LifeForms) {
            case "Blinker":
                for (int y = 0; y < 3; y++) {
                    gameBoard.getLifeFormsonBoard()[1][y] = new BasicLifeForm(true);
                }
                break;
            case "Toad":
                for (int y = 4;  y<= 6; y++) {
                    gameBoard.getLifeFormsonBoard()[5][y] = new BasicLifeForm(true);
                }
                for (int y = 3; y <= 5; y++) {
                    gameBoard.getLifeFormsonBoard()[6][y] = new BasicLifeForm(true);
                    }

                break;
            default:
                for (int x = 4; x <= 5; x++) {
                    gameBoard.getLifeFormsonBoard()[x][3] = new BasicLifeForm(true);
                    gameBoard.getLifeFormsonBoard()[x][4] = new BasicLifeForm(true);
                }
                for (int x = 6; x <= 7; x++) {
                    gameBoard.getLifeFormsonBoard()[x][5] = new BasicLifeForm(true);
                    gameBoard.getLifeFormsonBoard()[x][6] = new BasicLifeForm(true);
                }

        }
        for (int y = 0; y < gameBoard.getHeight(); y++) {
            for (int x = 0; x < gameBoard.getWidth(); x++) {
                gameBoard.getLifeFormsonBoard()[x][y].calculateNeighbors(gameBoard, x, y);
            }
        }
        paintAndCountDifferentLifeForms();
    }
}
