package SwarmSim;

import gui.GraphicalElement;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;


public class Boids extends Element implements GraphicalElement {

    private double size;
    private Color color;

    // wall to avoid
    private int windowWidth;
    private int windowHeight;


    // Double-buffered future state
    protected double nextDirection;
    protected double nextVelocity;

    // Parameters
    private static final double WALL_MARGIN = 30.0;         // distance from walls where boid starts turning
    private static final double WALL_TURN_STRENGTH = 0.85;   // how strongly it turns away
    private static final double NEIGHBOR_RADIUS = 150;
    private static final double SEPARATION_RADIUS = 40;
    private static final double SEPARATION_STRENGTH = 1.75;
    private static final double COHESION_STRENGTH = 0.0005;
    private static final double MAX_VELOCITY = 3.5;
    private static final double MIN_VELOCITY = 0.5;
    private static final double BOIDS_SIZE = 20;


    public Boids (int x, int y , double velocity, double direction, Color color, int wallX, int wallY ) {

        if (wallX <= 0 || wallY <= 0){
            throw new IllegalArgumentException("Wall cannot be inferior to 1");
        }
        this.windowWidth = wallX;
        this.windowHeight = wallY;

        System.out.println("Screen for boid x: "+wallX+ " y: "+ wallY);
        this.x = clamp(x,0,wallX);
        this.y = clamp(y,0,wallY);
        this.velocity = clamp(velocity,MIN_VELOCITY,MAX_VELOCITY);
        this.direction = direction % Math.PI;
        this.size = BOIDS_SIZE;
        this.color = color;
    }

    public void wrapPosition(int width, int height) {
        if (x < 0) x += width;
        if (x >= width) x -= width;

        if (y < 0) y += height;
        if (y >= height) y -= height;
    }

    // Compute next orientation + next position, but DO NOT apply yet
    public void updateBoids(List<Boids> all) {

        // ---- Collect neighbors ----
        List<Boids> neighbors = new LinkedList<>();
        for (Boids b : all) {
            if (b == this) continue;
            if (this.distance(b) < NEIGHBOR_RADIUS) {
                neighbors.add(b);
            }
        }

        if (neighbors.isEmpty()) {
            nextDirection = this.direction;
            nextVelocity = this.velocity;
            return;
        }

        // Keep same speed unless alignment changes it
        nextVelocity = this.velocity;

        //  WEIGHTED ALIGNMENT (direction + velocity)
        double sumVx = this.velocity * Math.sin(this.direction);   // weight = 1 for self
        double sumVy = this.velocity * -Math.cos(this.direction);
        double totalWeight = 1.0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);
            double weight = 1.0 / (dist + 0.0001);  // closer → stronger

            double bd = b.getDirection();
            double bv = b.getVelocity();

            // Neighbor velocity vector
            double vx = bv * Math.sin(bd);
            double vy = bv * -Math.cos(bd);

            sumVx += vx * weight;
            sumVy += vy * weight;
            totalWeight += weight;
        }

        // Average velocity vector from alignment
        double avgVx = sumVx / totalWeight;
        double avgVy = sumVy / totalWeight;

        //  SEPARATION (repulsion when too close)
        double sepX = 0;
        double sepY = 0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);

            if (dist < SEPARATION_RADIUS) {
                // push away from neighbor
                double dx = this.x - b.x;
                double dy = this.y - b.y;

                double inv = 1.0 / (dist + 0.0001);
                dx *= inv;
                dy *= inv;

                // force grows when distance gets smaller
                double force = (SEPARATION_RADIUS - dist) / SEPARATION_RADIUS;

                sepX += dx * force;
                sepY += dy * force;
            }
        }

        // Apply separation influence
        avgVx += sepX * SEPARATION_STRENGTH;
        avgVy += sepY * SEPARATION_STRENGTH;


        //  COHESION (move toward center of neighbors)
        double comX = 0;
        double comY = 0;
        totalWeight = 0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);
            double w = 1.0 / (dist + 0.0001);

            comX += b.x * w;
            comY += b.y * w;
            totalWeight += w;
        }

        comX /= totalWeight;
        comY /= totalWeight;

        // vector toward center of mass
        double cohX = (comX - this.x) * COHESION_STRENGTH;
        double cohY = (comY - this.y) * COHESION_STRENGTH;

        avgVx += cohX;
        avgVy += cohY;

        // Wall avoidance
        Point2D.Double wall = wallAvoidance();
        avgVx += wall.x;
        avgVy += wall.y;

        nextVelocity = Math.sqrt(avgVx * avgVx + avgVy * avgVy);
        if (nextVelocity < MIN_VELOCITY) {
            nextVelocity = MIN_VELOCITY;
        }
        if (nextVelocity > MAX_VELOCITY) {
            nextVelocity = MAX_VELOCITY;
        }
        nextDirection = Math.atan2(avgVx, -avgVy);
    }

    // Apply buffered values
    public void applyUpdate() {
        this.direction = nextDirection;
        this.velocity = nextVelocity;
        this.x += velocity * Math.sin(direction);
        this.y -= velocity * Math.cos(direction);
    }

    @Override
    public void paint(Graphics2D g2) {
        Path2D.Double triangle = new Path2D.Double();
        double halfBase = size * 0.5;

        // Triangle pointing “up” by default, tip at origin
        triangle.moveTo(0, -size);
        triangle.lineTo( halfBase, 0);
        triangle.lineTo(-halfBase, 0);
        triangle.closePath();

        Graphics2D g = (Graphics2D) g2.create();
        try {
            g.setColor(this.color);
            AffineTransform at = new AffineTransform();
            at.translate(x, y);          // move to boid position
            at.rotate(direction);        // rotate to boid heading
            g.transform(at);
            g.fill(triangle);           // filled triangle (use draw() if you only want outline)
        } finally {
            g.dispose();
        }
    }


    private Point2D.Double wallAvoidance() {
        double fx = 0;
        double fy = 0;

        // Left wall
        if (x < WALL_MARGIN) {
            fx += (WALL_MARGIN - x) / WALL_MARGIN;
        }
        // Right wall
        if (x > windowWidth - WALL_MARGIN) {
            fx -= (x - (windowWidth - WALL_MARGIN)) / WALL_MARGIN;
        }

        // Top wall
        if (y < WALL_MARGIN) {
            fy += (WALL_MARGIN - y) / WALL_MARGIN;
        }
        // Bottom wall
        if (y > windowHeight - WALL_MARGIN) {
            fy -= (y - (windowHeight - WALL_MARGIN)) / WALL_MARGIN;
        }

        // Apply tuning strength
        fx *= WALL_TURN_STRENGTH;
        fy *= WALL_TURN_STRENGTH;

        return new Point2D.Double(fx, fy);
    }

    // Local clamp helpers for Java versions without Math.clamp
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

}
