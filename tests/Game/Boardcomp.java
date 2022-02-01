package Game;

import java.util.*;

/**
 * Board komparátor a Board teszteléséhez
 */
public class Boardcomp implements Comparator<Board> {

    /**
     * Összehasonlítja a Boardon lévő életformák színét
     * @param board1 egyik board
     * @param board2 másik board
     * @return int sikeres volt-e vagy sem
     */
    @Override
    public int compare(Board board1, Board board2) {
        for (int y = 0; y < board1.getHeight(); y++) {
            for (int x = 0; x < board1.getWidth(); x++) {
                if(!board1.getLifeFormsonBoard()[x][y].getMyGraphicColor().equals(board2.getLifeFormsonBoard()[x][y].getMyGraphicColor()))
                    return 0;
            }
        }
        return 1;
    }
}
