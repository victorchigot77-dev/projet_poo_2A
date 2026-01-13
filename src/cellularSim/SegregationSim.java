package cellularSim;

import gui.GUISimulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Random;

public class SegregationSim {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        int width = 50;
        int height = 50;
        int maxState = 3;
        int segSeuil = 2;

        HashMap<Point2D.Double, Integer> aliveCells = new HashMap<>(width * height);
        Random rng = new Random();

        int deadCellPercent = 30;
        // Generates a grid of random cell values
        for (int col = 0; col < width; col++){
            for (int lig = 0; lig < height; lig++){
                if (rng.nextInt(100) > deadCellPercent){
                    aliveCells.put(new Point2D.Double(col, lig), rng.nextInt(maxState) + 1);
                }
            }
        }

        SegBoard game = new SegBoard(gui, aliveCells, maxState, segSeuil, width, height);
    }
}
