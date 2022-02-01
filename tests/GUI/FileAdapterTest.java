package GUI;

import Game.*;
import IO.*;
import org.junit.*;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Filekezelés tesztelése
 */
public class FileAdapterTest {

    /**
     * Fileadapter a teszteléshez
     */
    FileAdapter fa = new FileAdapter();
    /**
     * Board a teszteléshez
     */
    Board board = new Board(1,1);
    /**
     * Ugyanolyan tesztBoard
     */
    Board testerBoard = new Board(1,1);
    /**
     * Boardkomparátor a teszteléshez
     */
    Boardcomp boardcomp  = new Boardcomp();
    /**
     * Jó tesztfile az írás/beolvasás teszteléséhez
     */
    File testFile = new File(System.getProperty("user.dir"),"test.txt");
    /**
     * Rossz tesztfile az írás/beolvasás teszteléséhez
     */
    File badTestFile = new File(System.getProperty("user.dir"),"resources");

    /**
     * save metódus tesztelése, hogy kimenti a fájlba a Boardot.
     * Ugyanígy Exception-t is tesztelek
     */
    @Test
    public void save() {
        fa.save(board, testFile.getName());
        fa.save(board, badTestFile.getName());

    }
    /**
     * Load metódus tesztelése, hogy be tudja-e olvasni a Boardot
     * CompareTo metódussal
     */
    @Test
    public void load() {
        board = fa.load(board, testFile.getName());
        if(boardcomp.compare(board,testerBoard) == 0)
            fail("Nem sikerült jól beolvasni");
        board = fa.load(board, badTestFile.getName());
    }

}