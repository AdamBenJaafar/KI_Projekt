package Übungen;

import java.awt.*;

/**
 * Created by adam- on 12/05/2018.
 */
public interface demonstrationGrid {

    void clearMap();
    void updateLineOfSight(int x, int y, int [][] losvmap);
    void showMap();
    void color(int x, int y, Color color);

}
