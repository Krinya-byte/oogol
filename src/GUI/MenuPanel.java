package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A főmenüt megvalósító panel
 */
public class MenuPanel extends JPanel {
    JMenuItem startGame = new JMenuItem("Start Game");
    JMenuItem enterEditorMode = new JMenuItem("Enter Editor Mode");
    JMenuItem exit = new JMenuItem("Exit");
    CardLayout cardLayout;

    /**
     * Itt inicializálom a gombokat, GridBagLayoutba helyeztem őket
     * @param cardLayout Viewban megjelenő Layout továbbadása
     */
    public MenuPanel(CardLayout cardLayout){
        JMenu jMenu = new JMenu("Main menu");
        JMenuBar jMenuBar = new JMenuBar();
        setLayout(new BorderLayout());
        add(jMenuBar, BorderLayout.PAGE_START);
        jMenuBar.add(jMenu);
        jMenu.add(startGame);
        jMenu.add(enterEditorMode);
        jMenu.add(exit);
        startGame.addActionListener(new StartNewGameListener());
        enterEditorMode.addActionListener(new EditorModeListener());
        exit.addActionListener(new ExitListener());
        this.cardLayout = cardLayout;
    }

    /**
     * Start Game gombot megvalósító ActionListener
     */
    class StartNewGameListener implements ActionListener{

        /**
         * Új newgamepanelt hoz létre a az esetleges visszalépés miatt
         * @param e Actionevent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().newGamePanel = new NewGamePanel(cardLayout);
            GUIManager.getInstance().containerPanel.add(GUIManager.getInstance().newGamePanel, "NewGame");
            cardLayout.show(GUIManager.getInstance().containerPanel, "NewGame");
        }
    }

    /**
     * Enter Editor Mode gombot megvalósító ActionListener
     */
    class EditorModeListener implements ActionListener{

        /**
         * Új editorpanelt hoz létre a az esetleges visszalépés miatt
         * @param e Actionevent paraméter
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().editorPanel = new EditorPanel(cardLayout);
            GUIManager.getInstance().containerPanel.add(GUIManager.getInstance().editorPanel, "EditorMode");
            cardLayout.show(GUIManager.getInstance().containerPanel,"EditorMode");
        }
    }

    /**
     * Exit gombot megvalósító ActionListener
     */
    class ExitListener implements ActionListener{
        /**
         * Kilép a programból
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
