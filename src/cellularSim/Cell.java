package cellularSim;

public class Cell {
    private int maxState;
    private int state;
    private final int segSeuil; // Le seuil de ségrégation pour la variante de la ségrégation

    /**
     * Constructuer pour une situation vierge
     */
    public Cell(){
        setMaxState(1);
        setState(0);
        segSeuil = 0;
    }

    /**
     * Constructuer pour le jeu de la vie classique
     * @param state l'état initial de la cellule
     */
    public Cell(int state){
        setMaxState(1);
        setState(state);
        segSeuil = 0;
    }

    /**
     * Constructeur pour la variante de l'immigration
     * @param state l'état initial de la cellule
     * @param maxState l'état maximal de la cellule --FINAL--
     */
    public Cell(int state, int maxState){
        setMaxState(maxState);
        setState(state);
        segSeuil = 0;
    }

    /**
     * Constructeur pour la variante de la ségrégation
     * @param state l'état initial de la cellule
     * @param maxState l'état maximal de la cellule
     * @param segSeuil le seuil au-dela duquel le cellule cherche à déménager
     */
    public Cell(int state, int maxState, int segSeuil){
        setMaxState(maxState);
        setState(state);
        this.segSeuil = segSeuil;
    }

    /**
     * Change l'état de la cellule modulo son état maximal
     * @param state le nouvel état de la cellule
     */
    private void setState(int state){
        this.state = state % (maxState + 1);
    }

    private void setMaxState(int maxState){
        if (maxState <= 0){
            throw new IllegalArgumentException("maxState has to be strictly positive !");
        }

        this.maxState = maxState;
    }

    /**
     * Sécurise l'accès à state en revoyant un pourcentage de l'état actuel entre son état minimal et son état maximal
     * @return une approximation du pourcentage, entier entre 0 et 100
     */
    public int getPercent(){
        return 100 * state / maxState;
    }

    /**
     * Permet de savoir dans quel état la cellule peut changer en donnant un pourcentage entre l'état minimal
     * et l'état maximal
     * @return une approximation du pourcentage, entier entre 0 et 100
     */
    public int nextState(){
        int newState = (state + 1) % (maxState + 1);
        return 100 * newState / maxState;
    }

    /**
     * Vérifie si la cellule est vivante
     * @return true si vivant, false is mort
     */
    public boolean isAlive(){
        return state != 0;
    }

    /**
     * Règles du jeu de la vie (Conway):
     *      <p>1) Cellule morte avec 3 voisins => naissance</p>
     *      <p>2) Cellule vivante avec < 2 voisins => mort (isolation)</p>
     *      <p>3) Cellule vivante avec > 3 voisins => mort (surpopulation)</p>
     * @param nbAliveNeighbours le nombre de voisins vivants
     */
    public void newGenConway(int nbAliveNeighbours){
        if (nbAliveNeighbours != 2 && nbAliveNeighbours != 3){
            state = 0;
        } else if (nbAliveNeighbours == 3){
            state = 1;
        }
    }

    /**
     * Règle de la variante de l'immigration:
     *      <p>Cellule état n avec > 2 voisins d'états n+1</p>
     *      <p>=> état n -> état n+1</p>
     * @param nbNeighboursNextState le nombre de voisins d'état différent de celui de la cellule
     */
    public void newGenImmigration(int nbNeighboursNextState){
        if (nbNeighboursNextState > 2){
            setState(state + 1);
        }
    }

    /**
     * Règle de la variante de la ségrégation:
     *      <p>Cellule état n avec > segSeuil voisins d'états k != n</p>
     *      <p>=> Cellule meurt et une cellule adjacente naît d'état n</p>
     * @param nbNeighboursDiff nombre de voisins d'états k != n
     * @return true si déménagement, false sinon
     */
    public boolean newGenSeg(int nbNeighboursDiff){
        return state > 0 && nbNeighboursDiff > segSeuil;
    }

    /**
     * Tue la cellule
     * @return son état avant sa mort
     */
    public int segKill(){
        int state = this.state;
        setState(0);

        return state;
    }

    /**
     * Change la cellule suite à un déménagement
     * @param state l'état de la cellule qui déménage
     */
    public void segMove(int state){
        setState(state);
    }

    @Override
    public String toString(){
        return Integer.toString(state);
    }
}