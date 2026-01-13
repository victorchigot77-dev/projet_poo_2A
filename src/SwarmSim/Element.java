package SwarmSim;

import java.awt.*;
import java.awt.geom.Point2D;

public class Element extends Point2D.Double {

    public double velocity;
    public double direction ;
    public double acceleration;

    public Element (double x, double y, double velocity, double acceleration, double direction) {
        this.direction = direction;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.x = x;
        this.y = y;
    }

    public Element () {
    }


    public double getVelocity() {
        return velocity;
    }

    public double getDirection() {
        return direction;
    }
}
