package SwarmSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class SwarmBoard implements Simulable {

    private GUISimulator gui;
    private int nbBoids;
    private List<Boids> boids;

    public SwarmBoard(GUISimulator gui,int nbBoids){
        this.gui = gui;
        if (nbBoids < 1) {
            throw new IllegalArgumentException("number of Boids need to be superior to 1");
        }
        this.nbBoids = nbBoids;
        this.boids = initListBoidRandom(nbBoids);

        gui.setSimulable(this);
        gui.reset();
        draw();
    }

    public void draw () {
        gui.reset();
        for (Boids b : boids) {
            gui.addGraphicalElement(b);
        }
    }

    private List<Boids> initListBoidRandom (int nbBoids) {
        Random rng = new Random();
        List<Boids> lb = new LinkedList<>();
        for (int i = 0; i < nbBoids; i++) {
            int x, y ;
            double velocity,direction ;

           x = rng.nextInt(0, gui.getPanelWidth());
           y = rng.nextInt(0, gui.getPanelHeight());

           direction = rng.nextDouble(Math.PI);
           velocity = rng.nextDouble(0.1 , 3);


           lb.add( new Boids(x,y,velocity,direction, Color.PINK,gui.getPanelWidth(),gui.getPanelHeight()));

        }
        return lb;
    }

    @Override
    public void next() {
        for (Boids b : boids){
            b.updateBoids(this.boids);
        }
        for (Boids b : boids){
            b.applyUpdate();
            b.wrapPosition(gui.getPanelWidth(), gui.getPanelHeight());
        }
        draw();

    }

    @Override
    public void restart() {
        this.boids = initListBoidRandom(this.nbBoids);
        draw();

    }
}
