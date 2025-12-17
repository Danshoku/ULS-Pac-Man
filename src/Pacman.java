import java.awt.Color;
import java.awt.Graphics;

public class Pacman extends Unite {

    // Variables pour mémoriser la prochaine direction demandée par le joueur
    private int reqDX, reqDY;

    public Pacman() {
        super(7 * Constantes.TAILLE_BLOC, 12 * Constantes.TAILLE_BLOC);
        reqDX = 0;
        reqDY = 0;
    }

    public void setDirection(int dx, int dy) {
        // On ne change pas dx/dy tout de suite, on note juste le souhait du joueur
        this.reqDX = dx;
        this.reqDY = dy;
    }

    @Override
    public void bouger(Carte carte) {
        // 1. Est-ce qu'on est pile aligné sur une case ?
        if (x % Constantes.TAILLE_BLOC == 0 && y % Constantes.TAILLE_BLOC == 0) {

            int caseX = x / Constantes.TAILLE_BLOC;
            int caseY = y / Constantes.TAILLE_BLOC;

            // 2. On essaie d'appliquer la NOUVELLE direction demandée (reqDX)
            // Si la direction demandée n'est pas un mur, on la valide
            if (reqDX != 0 || reqDY != 0) {
                if (carte.getContenu(caseX + reqDX, caseY + reqDY) != 0) {
                    dx = reqDX;
                    dy = reqDY;
                }
            }

            // 3. On vérifie si la direction ACTUELLE nous envoie dans un mur
            if (carte.getContenu(caseX + dx, caseY + dy) == 0) {
                // C'est un mur devant, on s'arrête net
                dx = 0;
                dy = 0;
            }
        }

        // 4. On applique le mouvement
        x += dx * Constantes.VITESSE;
        y += dy * Constantes.VITESSE;
    }

    @Override
    public void dessiner(Graphics g) {
        // On dessine simplement l'image de Tupac à sa position x, y
        g.drawImage(Constantes.IMAGE_TUPAC, x, y, null);
    }
}