package GUI;

import javax.swing.*;

/**
 * Singleton manager class, hogy könyebben el tudjam érni a paneleket
 */
public class GUIManager {
    private static GUIManager guimanager = null;

    /**
     * MenuPanel változó
     */
    MenuPanel menupanel;
    /**
     * NewGamePanel változó
     */
    NewGamePanel newGamePanel;
    /**
     * EditorPanel változó
     */
    EditorPanel editorPanel;
    /**
     * GameView változó
     */
    GameView view;
    /**
     * Ezeknek a paneleknek a containere
     */
    JPanel containerPanel;
    /**
     * JMenuBar
     */
    JMenuBar jMenuBar;

    private GUIManager() { }

    /**
     * Singleton konstruktor
     * <a>https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#lazy-initialization</a>
     * @return guimanager - visszaadja a guimanagert
     */
    public static GUIManager getInstance(){
        if(guimanager == null)
            guimanager = new GUIManager();

        return guimanager;
    }

}
