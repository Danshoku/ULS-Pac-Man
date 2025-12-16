import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Unite {
    // protected permet aux enfants (Pacman, Fantome) d'utiliser ces variables directement
    protected int x, y;
    protected int dx, dy;

    public Unite(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
    }

    // Méthode abstraite : chaque enfant DOIT écrire sa propre version de cette méthode
    public abstract void dessiner(Graphics g);

    // Méthode abstraite : chacun bouge différemment (Clavier vs Hasard)
    public abstract void bouger(Carte carte);

    // Méthode commune : tout le monde a besoin de sa "boîte" pour les collisions
    public Rectangle getRect() {
        return new Rectangle(x, y, Constantes.TAILLE_BLOC - 4, Constantes.TAILLE_BLOC - 4);
    }

    // Getters pour que JeuPanel puisse lire la position
    public int getX() { return x; }
    public int getY() { return y; }
}