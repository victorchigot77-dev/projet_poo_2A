package SwarmSim;

import gui.GUISimulator;

import java.awt.*;

public class SwarmSim {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        int NbBoids = 30;
        // Use the dedicated simulator wrapper (implements Simulable.next/restart)
        SwarmSimulator sim = new SwarmSimulator(gui, NbBoids);
    }
}
