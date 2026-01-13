package cellularSim;

import gui.GUISimulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Random;

public class GameOfImmigration {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        int width = 50;
        int height = 50;
        int maxState = 3;

        HashMap<Point2D.Double, Integer> aliveCells = new HashMap<>(width * height);
        Random rng = new Random();

        // Generates an example grid from the pdf given
        /*aliveCells.put(new Point2D.Double(10, 10), 3);
        aliveCells.put(new Point2D.Double(12, 10), 1);
        aliveCells.put(new Point2D.Double(13, 10), 1);

        aliveCells.put(new Point2D.Double(10, 11), 3);
        aliveCells.put(new Point2D.Double(11, 11), 1);
        aliveCells.put(new Point2D.Double(12, 11), 1);
        aliveCells.put(new Point2D.Double(13, 11), 1);
        aliveCells.put(new Point2D.Double(14, 11), 2);

        aliveCells.put(new Point2D.Double(10, 12), 1);
        aliveCells.put(new Point2D.Double(11, 12), 1);
        aliveCells.put(new Point2D.Double(12, 12), 3);
        aliveCells.put(new Point2D.Double(13, 12), 2);
        aliveCells.put(new Point2D.Double(14, 12), 2);

        aliveCells.put(new Point2D.Double(11, 13), 1);
        aliveCells.put(new Point2D.Double(12, 13), 2);
        aliveCells.put(new Point2D.Double(13, 13), 2);
        aliveCells.put(new Point2D.Double(14, 13), 2);

        aliveCells.put(new Point2D.Double(11, 14), 3);
        aliveCells.put(new Point2D.Double(12, 14), 2);
        aliveCells.put(new Point2D.Double(13, 14), 2);
        aliveCells.put(new Point2D.Double(14, 14), 1);*/

        // Generates a grid of random cell values
        for (int col = 0; col < width; col++){
            for (int lig = 0; lig < height; lig++){
                aliveCells.put(new Point2D.Double(col, lig), rng.nextInt(maxState + 1));
            }
        }

        ImmigrationBoard game = new ImmigrationBoard(gui, aliveCells, maxState, width, height);
    }
}
