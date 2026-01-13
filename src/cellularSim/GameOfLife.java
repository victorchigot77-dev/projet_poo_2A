package cellularSim;

import gui.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;

public class GameOfLife {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        int height = 50;
        int width = 50;

        HashSet<Point2D.Double> aliveCells = new HashSet<>();
        Random rng = new Random();

        // This is a glider, let it fly accros the map
        // It's like a little nomad :D
        /*aliveCells.add(new Point2D.Double(1, 0));
        aliveCells.add(new Point2D.Double(2, 1));
        aliveCells.add(new Point2D.Double(0, 2));
        aliveCells.add(new Point2D.Double(1, 2));
        aliveCells.add(new Point2D.Double(2, 2));*/

        // This is a square with 11 eleven generations before being stable
        // It even settle into 4 colonies :O
        /*for (int i = 10; i < 15; i++){
            for (int j = 10; j < 15; j++){
                aliveCells.add(new Point2D.Double(i, j));
            }
        }*/

        // Generate a random grid of alive cells
        for (int col = 0; col < width; col++){
            for (int lig = 0; lig < height; lig++){
                if (rng.nextBoolean() /*&& rng.nextBoolean()*/){ // You can uncomment the second test to make alive cells less likely
                    aliveCells.add(new Point2D.Double(col, lig));
                }
            }
        }

        ConwayBoard game = new ConwayBoard(gui, aliveCells, width, height);
    }
}
