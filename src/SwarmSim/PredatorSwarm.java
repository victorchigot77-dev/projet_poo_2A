package SwarmSim;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Swarm variant that initializes PredatorBoid instances instead of standard Boids.
 */
class PredatorSwarm extends Swarm {

    public PredatorSwarm(int nbBoids) {
        super(nbBoids);
    }

    @Override
    protected List<Boids> initListBoidRandom(int n, int width, int height) {
        Random rng = new Random();
        List<Boids> lb = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int x = rng.nextInt(0, Math.max(1, width));
            int y = rng.nextInt(0, Math.max(1, height));

            double direction = rng.nextDouble(Math.PI);
            double velocity = rng.nextDouble(0.6, 3);

            lb.add(new PredatorBoid(x, y, velocity, direction, Color.ORANGE, width, height));
        }
        return lb;
    }
}

