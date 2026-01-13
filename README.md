Pour lancer les jeu faire make run....
exemple make runGameOfLife




# Description des différents fichiers et dossiers

Le projet contenait initialement un fichier nous permettant de nous donner un exemple d'animation pour tout le projet : 


   **TestInvader**

Contient le programme principal lançant une petite animation de type Space Invader. Le code crée une fenêtre graphique GUISimulator et y associe une instance de Invader. L’Invader est ensuite animé automatiquement via les commandes next et restart de l’interface graphique.

   **Invader**

Contient la classe qui représente et anime un mini-“Space Invader” pixelisé dans une fenêtre graphique.
La classe Invader :
-gère la position de l’invader (x, y) ;
-planifie un chemin prédéfini (droite → bas → gauche) grâce à deux itérateurs ;
-redessine entièrement la figure à chaque pas via de multiples petits rectangles (pixels) ;
-implémente Simulable pour permettre l’évolution (next) et le redémarrage (restart) dans la GUI.
L’ensemble forme une animation simple et pédagogique pour illustrer le fonctionnement d’un simulateur graphique.





## Animation d'une balle

La première partie portant sur l'animation d'une balle différents fichiers ont été créés pour cette anilation. Ainsi : 

   **TestGravityBall/ GravityBall**

Contient une simulation d’une balle unique soumise à la gravité et aux rebonds. Le code définit la classe GravityBall avec ses attributs (couleur, rayon, vitesse, force de gravité, coefficient de rebond) et gère le mouvement, les collisions avec les bords et le dessin dans la fenêtre graphique via GUISimulator. Le programme principal crée et lance une instance de balle.

   **TestBalls / BallsSimulator**

Contient une simulation de plusieurs balles se déplaçant simultanément. La classe Balls stocke les positions et vitesses de chaque balle, et BallsSimulator gère la translation, les collisions avec les bords et le dessin graphique de toutes les balles dans la fenêtre GUISimulator. Le programme principal initialise le simulateur avec un ensemble de balles.

## Automate cellulaire 

Pour cette partie des automates une partie graphique a du être créée pour les différents jeux codés après. 

   **Board (cellularSim)**

Contient la gestion d’un plateau pour simulation cellulaire. La classe Board crée une grille de cellules (Cell) et gère le calcul des voisins, les états des cellules, et les évolutions selon différents types de règles (Conway, Immigration, Seg). Le code inclut aussi la logique de bordures “wrap-around” et de propagation d’états pour chaque génération.

   **Cell (cellularSim)**

Contient la définition d’une cellule pour simulation cellulaire. La classe Cell gère l’état de la cellule, son état maximum, et un seuil spécifique (segSeuil). Elle inclut les règles d’évolution pour différents types de simulations (Conway, Immigration, Seg), la vérification si la cellule est vivante, et la gestion de transitions d’état.


### Le jeu de la vie de Conway


   **ConwayBoard (cellularSim)**

Contient la simulation graphique du jeu de la vie de Conway. La classe ConwayBoard hérite de Board et utilise GUISimulator pour dessiner chaque cellule avec un dégradé de couleur selon son état. Elle gère l’évolution des générations, le rafraîchissement graphique et la réinitialisation du plateau à son état de départ.


   **GameOfLife (cellularSim)**

Contient le programme principal pour lancer une simulation graphique du jeu de la vie de Conway. Le code initialise une grille de cellules vivantes (aléatoire ou prédéfinie) et crée une instance de ConwayBoard qui gère l’évolution des générations et le rendu graphique via GUISimulator.


### Le jeu de l'immigration

   **ImmigrationBoard (cellularSim)**

Contient la simulation graphique du jeu de l’Immigration. La classe ImmigrationBoard hérite de Board et utilise GUISimulator pour dessiner les cellules avec un dégradé de couleur selon leur état. Elle gère l’évolution des générations selon la règle d’Immigration, le rafraîchissement graphique et la réinitialisation du plateau à son état initial.

   **GameOfImmigration (cellularSim)**

Contient le programme principal pour lancer une simulation graphique du jeu de l’Immigration. Le code initialise une grille de cellules avec des états aléatoires (ou définis), puis crée une instance d’ImmigrationBoard qui gère l’évolution des générations selon la règle d’Immigration et le dessin graphique via GUISimulator.


### Le modèle de Schelling

   **SegBoard (cellularSim)**

Contient la simulation graphique d’un plateau cellulaire avec règles “Seg”. La classe SegBoard hérite de Board et utilise GUISimulator pour dessiner les cellules avec un dégradé de couleur selon leur état et seuil spécifique (segSeuil). Elle gère l’évolution des générations selon la règle Seg, le rafraîchissement graphique et la réinitialisation du plateau à son état initial.

   **SegregationSim (cellularSim)**

Contient le programme principal pour lancer une simulation graphique du modèle de ségrégation. Le code initialise une grille de cellules avec des états aléatoires selon un pourcentage de cellules mortes, puis crée une instance de SegBoard qui gère l’évolution des générations selon la règle de ségrégation et le rendu graphique via GUISimulator.

## Un modèle d’essaims : les boids



   **Boids (SwarmSim)**

Contient la représentation graphique d’un boid dans une simulation d’essaim. La classe gère la position, la direction et la vitesse de chaque boid, calcule le comportement collectif selon les règles de cohésion, alignement, séparation et évitement des murs, et dessine chaque boid sous forme de triangle orienté.

   **Element (SwarmSim)**

Contient les propriétés physiques de base d’un agent de simulation, telles que la position, la vitesse, l’accélération et la direction. Sert de classe de base pour les boids.

   **Event (SwarmSim)**

Contient la structure d’un événement discret planifié dans le temps. Chaque événement possède une date d’exécution et doit implémenter la méthode execute(). Comparable par date pour être utilisé dans un gestionnaire d’événements.

   **EventManager (SwarmSim)**

Contient le gestionnaire d’événements discrets pour la simulation. Il maintient la date courante, exécute les événements programmés à chaque tick, et permet de réinitialiser ou d’ajouter de nouveaux événements.

   **PredatorBoid (SwarmSim)**

Contient un boid prédateur qui hérite de Boids et ajoute la capacité de chasser des cibles. Il ajuste sa direction et sa vitesse pour poursuivre le boid le plus proche parmi ses cibles, tout en appliquant les règles classiques d’un boid pour le mouvement.

   **PredatorSwarm (SwarmSim)**

Contient un groupe de prédateurs. Hérite de Swarm et initialise une liste de PredatorBoid avec des positions et vitesses aléatoires pour la simulation.

   **Swarm (SwarmSim)**

Contient un groupe de boids et gère leur évolution collective. Calcule et applique la prochaine génération pour tous les boids, permet la réinitialisation du groupe, et fournit une méthode pour créer des boids aléatoires.

   **SwarmBoard (SwarmSim)**

Contient le wrapper graphique pour GUISimulator d’un groupe de boids. Initialise et dessine les boids, met à jour leur position et leur orientation à chaque tick, et permet la réinitialisation complète du groupe.

   **SwarmSim (SwarmSim)**

Contient le point d’entrée de la simulation Swarm. Crée la fenêtre graphique GUISimulator et initialise le simulateur SwarmSimulator avec un nombre défini de boids.

   **SwarmSimulator (SwarmSim)**

Contient la gestion complète de la simulation de groupes de boids avec événements discrets. Coordonne un groupe de sardines et un groupe de prédateurs, planifie leurs mises à jour via EventManager, met à jour la position de tous les boids, et gère le dessin et la réinitialisation de la simulation.












# TPL 2A POO 

Les ressources distribuées contiennent:

- une librairie d'affichage graphique d'un simulateur (lib/gui.jar) et sa documentation (doc/index.html)
- un fichier de démonstration du simulateur (src/TestInvader.java)


## Compilation & exécution
### Avec un makefile?
Un fichier Makefile est distribué pour facilement compiler et exécuter le fichier TestInvader.java

Mais vu la taille de ce projet, il est ***très fortement recommandé d'utiliser un IDE*** pour compiler, exécuter et déboguer votre code!

### IDE Idea Intellij
- créer un nouveau projet:
    - menu *File/New Project*
    - si le répertoire distribué est dans "~/Ensimag/2A/POO/TPL_2A_POO", alors paramétrer les champs *Name* avec "TPL_2A_POO" et *Location* avec "~/Ensimag/2A/POO/"
    - configurer l'utilisation de la librairie
    - menu *File/Project Structure* puis *Projet setting/Modules*
    - clicker sur(*Add* puis "JARs & Directories" et sélectionner ~/Ensimag/2A/POO/TPL_2A_POO/lib
    - vous pouvez bien sûr utiliser git via l'interface d'idea Intellij

### IDE VS Code
- dans "~/Ensimag/2A/POO/TPL_2A_POO", lancer *code ."
- si vous avez installé les bonnes extensions java (exécution, debogage...) il est possible que tout fonctionne sans rien faire de spécial.
- s'il ne trouve pas la librairie, vous devez alors créer un vrai "projet" et configurer l'import du .jar.
- pas vraiment d'aide pour ça, vous trouverez
- vous pouvez bien sûr utiliser git via l'interface de VS code
