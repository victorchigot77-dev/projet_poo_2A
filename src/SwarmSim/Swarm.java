package SwarmSim;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Swarm model holding and updating a list of boids.
 * Mirrors the structure used in TestBalls (model + Simulable wrapper).
 */
class Swarm {
    private final int nbBoids;
    private List<Boids> boids;

    public Swarm(int nbBoids) {
        if (nbBoids < 1) {
            throw new IllegalArgumentException("number of Boids need to be superior to 1");
        }
        this.nbBoids = nbBoids;
        this.boids = new LinkedList<>();
    }

    public int getNbBoids() {
        return nbBoids;
    }

    public List<Boids> getBoids() {
        return boids;
    }

    public void reInit(int width, int height) {
        this.boids = initListBoidRandom(nbBoids, width, height);
    }

    /**
     * Compute next state for all boids, then apply it.
     */
    public void update() {
        for (Boids b : boids) {
            b.updateBoids(this.boids);
        }
        for (Boids b : boids) {
            b.applyUpdate();
        }
    }

    protected List<Boids> initListBoidRandom(int n, int width, int height) {
        Random rng = new Random();
        List<Boids> lb = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int x = rng.nextInt(0, Math.max(1, width));
            int y = rng.nextInt(0, Math.max(1, height));

            double direction = rng.nextDouble(Math.PI);
            double velocity = rng.nextDouble(0.1, 3);

            lb.add(new Boids(x, y, velocity, direction, Color.PINK, width, height));
        }
        return lb;
    }
}
