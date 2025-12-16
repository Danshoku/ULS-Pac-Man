import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Fenetre());
    }
}

// Faire le jeu de base Pac-Man
// Faire un Pac-Man battle royal en réseau (2 machine (Serveur & Client))