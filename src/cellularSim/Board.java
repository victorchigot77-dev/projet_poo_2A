package cellularSim;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Board {
    private ArrayList<ArrayList<Cell>> cellBoard;
    private int width;
    private int height;

    /**
     * Constructeur utiliser pour la variante de l'immigration
     * @param width la largeur de la grille en cellules
     * @param height la hauteur de la grille en cellules
     * @param aliveCells map des états des cellules vivantes : clés = coords, valeurs = états
     * @param maxState l'état maximal des cellules
     */
    public Board(int width, int height, HashMap<Point2D.Double, Integer> aliveCells, int maxState){
        setSize(width, height);
        setCellBoard(aliveCells, maxState);
    }

    /**
     * Constructeur utilisé par la variante de la ségrégation
     * @param width la largeur de la grille en cellules
     * @param height la hauteur de la grille en cellules
     * @param aliveCells map des états des cellules vivantes : clés = coords, valeurs = états
     * @param maxState l'état maximal des cellules
     * @param segSeuil le seuil de ségrégation
     */
    public Board(int width, int height, HashMap<Point2D.Double, Integer> aliveCells, int maxState, int segSeuil){
        setSize(width, height);
        setCellBoard(aliveCells, maxState, segSeuil);
    }

    /**
     * Constructeur utilisé par le jeu de la vie
     * @param width la largeur de la grille en cellules
     * @param height la hauteur de la grille en cellules
     * @param aliveCells ensemble des cellules vivantes
     */
    public Board(int width, int height, HashSet<Point2D.Double> aliveCells){
        setSize(width, height);
        setCellBoard(aliveCells);
    }

    /**
     * Modifieur vérifiant les paramètres de taille de la grille
     * @param width la largeur de la grille
     * @param height la hauteur de la grille
     */
    private void setSize(int width, int height){
        if (width <= 0){
            throw new IllegalArgumentException("Width has to be strictly positive !");
        }
        if (height <= 0){
            throw new IllegalArgumentException("Height has to be strictly positive !");
        }

        this.width = width;
        this.height = height;
    }

    /**
     * Donne la couleur de la cellule à partir d'un gradient linéaire entre la couleur d'une
     * cellule morte et celle d'une cellule d'état maximal en utilisant le pourcentage de l'état
     * de cette cellule
     * @param c1 la couleur d'une cellule morte
     * @param c2 la couleur d'une cellule d'état maximal
     * @param percent le pourcentage de l'état de la cellule
     * @return la couleur de la cellule
     */
    protected static Color linearColorGradient(Color c1, Color c2, float percent){
        if (percent > 100 | percent < 0){
            throw new IllegalArgumentException("percentage is out of range");
        }

        int r1 = c1.getRed(); int g1 = c1.getGreen(); int b1 = c1.getBlue();
        int r2 = c2.getRed(); int g2 = c2.getGreen(); int b2 = c2.getBlue();

        int newRed = (int)(r1 + percent * (r2 - r1));
        int newGreen = (int)(g1 + percent * (g2 - g1));
        int newBlue = (int)(b1 + percent * (b2 - b1));

        return new Color(newRed, newGreen, newBlue);
    }

    /**
     * Récupère l'état de la cellule en pourcentage entre son état minimal et maximal
     * @param lig ligne de le cellule
     * @param col colonne de la cellule
     * @return le pourcentage, entier entre 0 et 100
     */
    public int getPercent(int lig, int col){
        return cellBoard.get(lig).get(col).getPercent();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    /**
     * Initialise la grille pour la variante de l'immigration
     * @param aliveCells map des états des cellules vivantes : clés = coords, valeurs = états
     * @param maxState l'état maximal des cellule
     */
    public void setCellBoard(HashMap<Point2D.Double, Integer> aliveCells, int maxState){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(col, lig);

                cellBoard.get(lig).add(new Cell(aliveCells.getOrDefault(coords, 0), maxState));
            }
        }
    }

    /**
     * Initialise la grille pour la variante de la ségrégation
     * @param aliveCells map des états des cellules vivantes : clés = coords, valeurs = états
     * @param maxState l'état maximal des cellule
     * @param segSeuil le seuil de ségrégation
     */
    public void setCellBoard(HashMap<Point2D.Double, Integer> aliveCells, int maxState, int segSeuil){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(col, lig);

                cellBoard.get(lig).add(new Cell(aliveCells.getOrDefault(coords, 0), maxState, segSeuil));
            }
        }
    }

    /**
     * Initialise le grille pour le jeu de la vie
     * @param aliveCells ensemble des cellules vivantes
     */
    public void setCellBoard(HashSet<Point2D.Double> aliveCells){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(lig, col);

                if (aliveCells.contains(coords)){
                    cellBoard.get(lig).add(new Cell(1));
                } else {
                    cellBoard.get(lig).add(new Cell(0));
                }
            }
        }
    }

    /**
     * Donne le nombre de voisins vivants pour une cellule renseignée
     * @param lig ligne de la cellule
     * @param col colonne de la cellule
     * @return le nombre de voisins vivants
     */
    private int getNbAliveNeighbours(int lig, int col){
        int count = 0;
        boolean isCellAlive;

        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                isCellAlive = cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).isAlive();

                if ((addLig != 0 || addCol != 0) && isCellAlive){ // Warning : need to check if not counting itself
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Donne le nombre de voisins dans l'état suivant celui de la cellule renseignée
     * @param lig la ligne de la cellule
     * @param col la colonne de la cellule
     * @return le nombre de voisins dans l'état suivant
     */
    private int getNbNeighboursNextState(int lig, int col){
        int count = 0;
        int nextState = cellBoard.get(lig).get(col).nextState();
        int cellState;

        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                cellState = cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).getPercent();

                if ((addLig != 0 || addCol != 0) && cellState == nextState){ // need to check to not count itself
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Donne le nombre de voisins dans un état différent de celui de la cellule renseignée
     * @param lig la ligne de la cellule
     * @param col la colonne de la cellule
     * @return le nombre de voisins différents
     */
    private int getNbNeighboursDiff(int lig, int col){
        int count = 0;
        int state = cellBoard.get(lig).get(col).getPercent();
        int cellState;

        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                cellState = cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).getPercent();

                if ((addLig != 0 || addCol != 0) && cellState != 0 && cellState != state){ // need to check to not count itself
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Donne une liste des voisins morts (places vacantes pour un déménagement) de la cellule renseignée
     * @param lig ligne de la cellule
     * @param col colonne de la cellule
     * @return la liste des voisins morts
     */
    private ArrayList<Point2D.Double> listDeadNeighbours(int lig, int col){
        int length = 8 - getNbAliveNeighbours(lig, col);
        boolean isDead;
        ArrayList<Point2D.Double> deadNeighbours = new ArrayList<>(length);

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                Point2D.Double adjCell = new Point2D.Double(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                isDead = !cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).isAlive();

                if ((addLig != 0 || addCol != 0) && isDead){ // need to check to not count itself
                    deadNeighbours.add(adjCell);
                }
            }
        }

        return deadNeighbours;
    }

    /**
     * Assure que les coordonnées du point soit comprises dans la grille, si ce n'est pas le cas,
     * modifie les coordonées en considérant la continuité des bords
     * (cela revient à considérer la grille comme un donut)
     *
     * <p>Mmmmmmh... donut!</p>
     * @param p le point de coordonnées à vérifier
     */
    private void inBounds(Point2D.Double p){
        double x = p.getX();
        double y = p.getY();

        if (x < 0){
            x = width - 1;
        } else if (x >= width){
            x = 0;
        }

        if (y < 0){
            y = height - 1;
        } else if (y >= height){
            y = 0;
        }

        p.setLocation(x, y);
    }

    /**
     * Modifie la grille selon les règles du jeu de la vie de Conway
     */
    public void nextGenConway(){
        ArrayList<ArrayList<Integer>> listNeighbours = new ArrayList<>();
        for (int lig = 0; lig < height; lig++){ // Calculate the number of neighbours for each cell
            listNeighbours.add(new ArrayList<>());
            for (int col = 0; col < width; col++){
                listNeighbours.get(lig).add(getNbAliveNeighbours(lig, col));
            }
        }

        for (int lig = 0; lig < height; lig++){ // Change the state of the cell according to the inner laws of the cell
            for (int col = 0; col < width; col++){
                cellBoard.get(lig).get(col).newGenConway(listNeighbours.get(lig).get(col));
            }
        }
    }

    /**
     * Modifie la grille selon les règles de la variante de l'immigration
     */
    public void nextGenImmigration(){
        ArrayList<ArrayList<Integer>> listNeighbours = new ArrayList<>();
        for (int lig = 0; lig < height; lig++){ // Calculate the number of neighbours for each cell
            listNeighbours.add(new ArrayList<>());
            for (int col = 0; col < width; col++){
                listNeighbours.get(lig).add(getNbNeighboursNextState(lig, col));
            }
        }

        for (int lig = 0; lig < height; lig++){ // Change the state of the cell according to the inner laws of the cell
            for (int col = 0; col < width; col++){
                cellBoard.get(lig).get(col).newGenImmigration(listNeighbours.get(lig).get(col));
            }
        }
    }

    /**
     * Modifie les règles selon la variante de la ségrégation
     */
    public void nextGenSeg(){
        int nbNeighboursDiff;
        int state;
        Point2D newCoords;
        ArrayList<Point2D.Double> deadNeighbours;

        Random rng = new Random();

        for (int lig = 0; lig < height; lig++){
            for (int col = 0; col < width; col++){
                nbNeighboursDiff = getNbNeighboursDiff(lig, col);

                if (cellBoard.get(lig).get(col).newGenSeg(nbNeighboursDiff)){

                    deadNeighbours = listDeadNeighbours(lig, col);
                    if (!deadNeighbours.isEmpty()){
                        newCoords = deadNeighbours.get(rng.nextInt(deadNeighbours.size()));

                        state = cellBoard.get(lig).get(col).segKill();
                        cellBoard.get((int)newCoords.getY()).get((int)newCoords.getX()).segMove(state);
                    }
                }
            }
        }
    }
}
