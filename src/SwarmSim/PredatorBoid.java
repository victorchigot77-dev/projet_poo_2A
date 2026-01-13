package SwarmSim;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class PredatorBoid extends Boids {

    private List<Boids> targets = Collections.emptyList();

    public PredatorBoid(int x, int y, double velocity, double direction, Color color, int wallX, int wallY) {
        super(x, y, velocity, direction, color, wallX, wallY);
    }

    public void setTargets(List<Boids> targets) {
        this.targets = (targets == null) ? Collections.emptyList() : targets;
    }

    @Override
    public void updateBoids(List<Boids> all) {
        super.updateBoids(all);
        if (targets.isEmpty()) return;

        Boids nearest = null;
        double best = java.lang.Double.POSITIVE_INFINITY;
        for (Boids t : targets) {
            double d = this.distance(t);
            if (d < best) {
                best = d;
                nearest = t;
            }
        }
        if (nearest == null) return;

        double dx = nearest.x - this.x;
        double dy = nearest.y - this.y;
        double desired = Math.atan2(dy, dx);

        double alpha = 0.35;
        this.nextDirection = blendAngles(this.nextDirection, desired, alpha);

        double maxSpeed = 3.5;
        double accel = 0.3;
        this.nextVelocity = Math.min(this.nextVelocity + accel, maxSpeed);

        if (best < 25) this.nextVelocity *= 0.9;
    }

    private static double blendAngles(double a, double b, double t) {
        double x = (1 - t) * Math.cos(a) + t * Math.cos(b);
        double y = (1 - t) * Math.sin(a) + t * Math.sin(b);
        return Math.atan2(y, x);
    }
}
