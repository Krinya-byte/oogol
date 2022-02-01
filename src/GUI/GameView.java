package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Az ablakot megvalósító osztály
 */
public class GameView extends JFrame {
    /**
     * A cardlayout, amivel tudunk majd váltani a panelek között
     */
    CardLayout cardLayout = new CardLayout();

    /**
     * GameView konstruktora az alapvető beállításokat itt végzem el.
     * containerpanelhez itt adom hozzá a változókat, a GUImanager változóit is itt definiálom
     */
    public GameView(){
        setName("Gol Demo");
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());


        GUIManager manager = GUIManager.getInstance();
        manager.editorPanel = new EditorPanel(cardLayout);
        manager.newGamePanel = new NewGamePanel(cardLayout);
        manager.menupanel = new MenuPanel(cardLayout);
        manager.jMenuBar = new JMenuBar();


        manager.containerPanel = new JPanel();
        manager.containerPanel.setLayout(cardLayout);
        manager.containerPanel.add(manager.menupanel, "Menu");
        manager.containerPanel.add(manager.newGamePanel,"NewGame");
        manager.containerPanel.add(manager.editorPanel,"EditorMode");
        cardLayout.show(GUIManager.getInstance().containerPanel,"Menu");
        add(GUIManager.getInstance().containerPanel, BorderLayout.CENTER);
        pack();
    }
}
