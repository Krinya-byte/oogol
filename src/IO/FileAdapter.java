package IO;

import Game.*;

import java.io.*;

/**
 * FileAdapter osztály, ami a ki és beolvasást kezeli
 */
public class FileAdapter {
    /**
     * Kiírást kezelő metódus
     * @param gameBoard a Board amit megkap kiíírásra
     * @param pathname fjlútvonal amit a filechooser ad meg neki
     */
    public void save(Board gameBoard, String pathname){
        try {
            File f = new File(pathname);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameBoard);
            oos.close();
            fos.close();
        }catch(IOException e){
            System.out.println("Hibás mentés");
        }
    }

    /**
     * Beolvasást végző metódus
     * @param gameBoard Board amibe beolvasunk
     * @param pathname file útja amiből beolvasunk
     * @return visszaadja a boardot amit beolvastunk
     */
    public Board load(Board gameBoard, String pathname){
        try{
            FileInputStream fis = new FileInputStream(pathname);
            ObjectInputStream oos = new ObjectInputStream(fis);
           gameBoard = (Board) oos.readObject();
            oos.close();
            fis.close();
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Hibás beolvasás");
        }
        return gameBoard;
    }
}
