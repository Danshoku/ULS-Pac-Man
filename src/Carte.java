import java.awt.Color;
import java.awt.Graphics;

public class Carte {
    // 0 = mur, 1 = point, 2 = vide
    private int[][] grille = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public int getContenu(int x, int y) {
        // Sécurité pour ne pas sortir du tableau
        if (x < 0 || x >= Constantes.N_BLOCS || y < 0 || y >= Constantes.N_BLOCS) return 0;
        return grille[y][x];
    }

    public void setVide(int x, int y) {
        grille[y][x] = 2; // On met 2 pour dire que c'est vide (point mangé)
    }

    public void dessiner(Graphics g) {
        for (int y = 0; y < Constantes.N_BLOCS; y++) {
            for (int x = 0; x < Constantes.N_BLOCS; x++) {
                if (grille[y][x] == 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * Constantes.TAILLE_BLOC, y * Constantes.TAILLE_BLOC, Constantes.TAILLE_BLOC, Constantes.TAILLE_BLOC);
                    g.setColor(Color.BLACK);
                    g.drawRect(x * Constantes.TAILLE_BLOC, y * Constantes.TAILLE_BLOC, Constantes.TAILLE_BLOC, Constantes.TAILLE_BLOC);
                } else if (grille[y][x] == 1) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * Constantes.TAILLE_BLOC + 10, y * Constantes.TAILLE_BLOC + 10, 4, 4);
                }
            }
        }
    }
}