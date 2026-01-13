import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;

import gui.*;

public class TestBalls {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        BallsSimulator balls = new BallsSimulator(gui, Color.GREEN, 10);
    }
}

class Balls{
    private final int ballsRadius;
    private ArrayList<Point> points;
    private ArrayList<Integer> dx;
    private ArrayList<Integer> dy;

    public Balls(int ballsRadius){
        this.ballsRadius = ballsRadius;
        reInit();
    }
    //Accesseurs
    public int getRadius(){
        return this.ballsRadius;
    }
    public ArrayList<Point> getPoints(){
        return this.points;
    }
    public ArrayList<Integer> getdx(){
        return this.dx;
    }
    public ArrayList<Integer> getdy(){
        return this.dy;
    }

    public void reInit(){
        points = new ArrayList<>();
        dx = new ArrayList<>();
        dy = new ArrayList<>();
        for (int x = 10; x < 80; x += 50){
            for (int y = 10; y < 60; y += 50){
                points.add(new Point(x, y));
                dx.add(1);
                dy.add(1);
            }
        }
    }

    public void translate(){
        for(int i=0; i<points.size(); i++){
            points.get(i).translate(dx.get(i), dy.get(i));
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("position des balles : \n");
        for (Point p : points){
            str.append(p.toString()).append("\n");
        }
        return str.toString();
    }
}

class BallsSimulator extends Balls implements Simulable{
    private final GUISimulator gui;
    private final Color ballsColor;

    public BallsSimulator(GUISimulator gui, Color ballsColor, int ballsRadius){
        super(ballsRadius);
        this.gui = gui;
        gui.setSimulable(this);
        this.ballsColor = ballsColor;
        System.out.println(this); //Q.2 Affiche simplement l'état des balles sans graphismes
        draw();
    }

    private void draw(){
        gui.reset();
        for (Point p : getPoints()){
            gui.addGraphicalElement(new Oval(p.x, p.y, ballsColor, ballsColor, this.getRadius()));
        }
    }

    public void verifTranslation(){
        for(int i=0; i<getPoints().size(); i++){
            if(getPoints().get(i).getX() <= getRadius() || getPoints().get(i).getX() >= gui.getWidth()-getRadius()){
                getdx().set(i, -getdx().get(i));
            }
            if(getPoints().get(i).getY() <= getRadius()-5 || getPoints().get(i).getY() >= gui.getHeight()-getRadius()-84){
                getdy().set(i, -getdy().get(i));
            }
        }
    }

    public void next(){
        this.translate();
        verifTranslation();
        System.out.println(this); //Q.2 Affiche simplement l'état des balles sans graphismes
        draw(); //Q.3 Affiche graphiquement
    }

    public void restart(){
        this.reInit();
        System.out.println(this); //Q.2 Affiche simplement l'état des balles sans graphismes
        draw(); //Q.3 Affiche graphiquement
    }
}
