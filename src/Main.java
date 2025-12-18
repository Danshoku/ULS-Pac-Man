import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Fenetre());
    }
}

// OK Faire le jeu de base Pac-Man
// OK Avoir une partie graphique Tupac
// Ajouter un système de vie pour pacman
// Ajouter un chrono au différent mode de jeu
// Ajouter un objet qui permet de manger les fantome avec image de pistolet
// Ajouter une intelligence aux fantomes et ainsi ajouter des mode de jeu plus ou moins compliqué selon leurs intelligence
// Changer le graphisme des points par des billets ou des pièces
// Avoir un menu pour les différents modes de jeu et options
// Faire un mode de jeu Pac-Man battle royal en réseau (2 machine (Serveur & Client))
// Faire un mode de jeu avec une génération aléatoire du labyrinthe
// Faire un mode duel avec jouabilité d'un fantome par un joueur et de pacman par un autre joueur
// Faire un tableau des scores rangé par mode et ou les meilleurs ont 3 vies, tout les points de la map, un chrono faible
