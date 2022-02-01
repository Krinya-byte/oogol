package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import Game.*;
import IO.*;

/**
 * A grafikus pályaszerkeztőt megvalósító osztály
 */
public class EditorPanel extends JPanel {
    /**
     * 2 dimenziós tömb ami a pályán előforduló négyzeteket tárolja
     */
    private MyRectangle[][] rectangles;
    /**
     * A tömb oszlopainak száma
     */
    private int columnCount;
    /**
     * A tömb sorainak száma
     */
    private int rowCount;
    /**
     * Játéktábla, inicializálom egyből, mivel végig szükségem lesz rá
     */
    private Board gameBoard;
    /**
     * A csereéletforma, amivel majd kicserélődik a Board életformája
     */
    private LifeForm replacementLifeForm = new Amoeba(true);

    /**
     * String tömb ami tárolja a JComboBoxban előforduló elemeket
     */
    private String[] lifeForms =  {"Amoeba", "BasicLifeForm", "Default"};

    /**
     * JComboBox
     */
    private JComboBox lifeFormComboBox;

    /**
     * Editorpanel konstruktora.
     * Flowlayoutot használok a gombok miatt.
     * A mérete előre be van állítva.
     * Gombokat a konstruktorban hozom létre, mivel nem használom őket.
     * Lambdával csak a rövidebb ActionListenereket valósítottam meg.
     * Hosszabb funkciókat belső privát osztályban valósítottam meg
     * @param cardLayout - ezt kapja a GameView-tól
     */
    public EditorPanel(CardLayout cardLayout) {

        //setLayout(new FlowLayout());
        this.gameBoard = new Board(10, 10);
        this.columnCount = gameBoard.getWidth();
        this.rowCount = gameBoard.getHeight();
        setPreferredSize(new Dimension(550, 550));
        setLayout(new BorderLayout());

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Editor Menu");
        jMenuBar.add(jMenu);
        add(jMenuBar,BorderLayout.PAGE_START);
        jMenuBar.add(jMenu);

        rectangles = new MyRectangle[columnCount][rowCount];
        initializeRectangles();

        this.lifeFormComboBox = new JComboBox(lifeForms);
        add(lifeFormComboBox,BorderLayout.PAGE_END);
        lifeFormComboBox.addActionListener(new JComboboxListener());

        addMouseListener(new MouseActionListener());

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new Save());
        jMenu.add(save);

        JMenuItem goBack = new JMenuItem("Back");
        jMenu.add(goBack);
        goBack.addActionListener(e -> cardLayout.show(GUIManager.getInstance().containerPanel, "Menu"));
    }

    /**
     * Előre inicializája a Boardot alkotó négyzeteket.
     * Négyzetek fix elhelyezkedésűek és nagyságúak a megfelelő látvány érdekében
     */
    private void initializeRectangles() {
        int cellWidth = 500 / rowCount;
        int cellHeight = 500 / columnCount;
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                rectangles[x][y] = new MyRectangle((x + 1) * cellWidth - 25, (y + 1) * cellHeight - 10, 500 / columnCount - 2, 500 / rowCount - 2, Color.yellow);
            }
        }
    }

    /**
     * A paintComponent metódus felülírása, négyzetek rajzoló metódusát hívja meg
     * @param g - Graphics paraméter a metódusok hívásáért felel
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < columnCount; x++) {
                rectangles[x][y].paint(g2d);
            }
        }
    }

    /**
     * A MouseListener mouseClicked metódusát írja felül
     * Megkeresi a megfelelő négyzetet
     * Mivel a négyzet és a Boardon lévő LifeFormok indexe megegyezik kicseráli a LifeFormot
     * Eltárolja változókba a megfelelő pozíciókat, hogy egy lépést tudjon törölni a felhasználó
     */
    class MouseActionListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int locationX = e.getX();
            int locationY = e.getY();
            for (int y = 0; y < rowCount; y++) {
                for (int x = 0; x < columnCount; x++) {
                    MyRectangle rectangle = rectangles[x][y];
                    if (locationY > (int) rectangle.getMinY() && locationY < (int) rectangle.getMaxY()) {
                        if (locationX > (int) rectangle.getMinX() && locationX < (int) rectangle.getMaxX()) {
                            rectangle.setColor(replacementLifeForm.getMyGraphicColor());
                            gameBoard.getLifeFormsonBoard()[x][y] = replacementLifeForm;
                            repaint();
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * Mentést megvalósítő ActionListener
     * JFileChoosert hoz létre ami lementi az alkotott Boardot a resources mappába
     * Előtte újra kell kalkulálni a szomszédokat, hogy az új elemek is megkapják a megfelelő szomszédaikat
     */
    public class Save implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                for (int x = 0; x < gameBoard.getWidth(); x++) {
                    gameBoard.getLifeFormsonBoard()[x][y].calculateNeighbors(gameBoard, x, y);
                }
            }

            JFileChooser chooser = new JFileChooser(new
                    File(System.getProperty("user.dir"), "resources"));
            int response = chooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                try {
                    FileAdapter fileadapter = new FileAdapter();
                    fileadapter.save(gameBoard, chooser.getSelectedFile() + ".txt");
                } catch (Exception ex) {
                    System.out.println("Hibás mentés");
                }
            }
        }
    }

    /**
     * JComboBox ActionListenerje, egy új életformát hoz létre a kiválasztott életformának megfelelelően
     */
    private class JComboboxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (lifeFormComboBox.getSelectedItem().toString()) {
                case "BasicLifeForm":
                    replacementLifeForm = new BasicLifeForm(true);
                    break;
                case "Amoeba":
                    replacementLifeForm = new Amoeba(true);
                    break;
                default:
                    replacementLifeForm = new BasicLifeForm(false);

            }
        }
    }
}
