package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

class SegBoard extends Board implements Simulable {

    // Attributs
    private final GUISimulator gui;
    private final HashMap<Point2D.Double, Integer> starter;
    private final int maxState;
    private final int segSeuil;

    // Caractéristiques des cellules
    private static final int cellSize = 10;
    private static final Color minStateCellColor = Color.RED;
    private static final Color maxStateCellColor = Color.BLUE;
    private static final Color deadCellColor = Color.BLACK;
    private static final Color bgColor = Color.LIGHT_GRAY;

    // Event manager
    private final EventManager manager = new EventManager();

    /**
     * Constructeur d'une grille du jeu de la vie de Conway
     * @param gui l'interface graphique
     * @param starter l'ensemble des cellules vivantes
     * @param maxState l'état maximal des cellules
     * @param segSeuil le seuil de ségrégation
     * @param width la largeur de la grille en cellules
     * @param height la hauteur de la grille en cellules
     */
    public SegBoard(GUISimulator gui, HashMap<Point2D.Double, Integer> starter, int maxState, int segSeuil, int width, int height){
        super(width, height, starter, maxState, segSeuil);
        this.gui = gui;
        gui.setSimulable(this);
        this.starter = starter;
        this.maxState = maxState;
        this.segSeuil = segSeuil;

        manager.addEvent(new SegStep(manager.getCurrentDate() + 1));

        draw();
    }

    /**
     * Dessine la grille sur l'interface graphique
     */
    public void draw(){
        gui.reset();

        Color cellColor;
        for (int col = 0; col < getWidth(); col++){

            for (int lig = 0; lig < getHeight(); lig++){
                if (getPercent(lig, col) == 0){
                    cellColor = deadCellColor;
                } else {
                    cellColor = linearColorGradient(minStateCellColor, maxStateCellColor, (float) getPercent(lig, col) / 100);
                }

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * col,
                        20 + cellSize * lig,
                        bgColor, cellColor, cellSize));

            }
        }
    }

    /**
     * Dessine l'état suivant sur l'interface graphique
     */
    public void next(){
        manager.next();
    }

    /**
     * Dessine l'état initial sur l'interface graphique
     */
    public void restart(){
        manager.restart();
        setCellBoard(starter, maxState, segSeuil);
        draw();
    }

    private class SegStep extends Event{
        public SegStep(long date){
            super(date);
        }

        @Override
        public void execute(){
            nextGenSeg();
            draw();

            manager.addEvent(new SegStep(manager.getCurrentDate() + 1));
        }
    }
}
