import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Fantome extends Unite {

    public Fantome(int startX, int startY) {
        super(startX, startY);
        this.dy = 1;
    }

    // --- NOUVELLE MÉTHODE ---
    // Cette méthode téléporte le fantôme dans le coin opposé à Pacman
    public void respawnLoin(Pacman pacman) {
        int pacX = pacman.getX() / Constantes.TAILLE_BLOC;
        int pacY = pacman.getY() / Constantes.TAILLE_BLOC;

        int milieu = Constantes.N_BLOCS / 2;

        int nouveauX, nouveauY;

        // Si Pacman est à GAUCHE (plus petit que le milieu), le fantôme va à DROITE (13)
        // Sinon, le fantôme va à GAUCHE (1)
        if (pacX < milieu) {
            nouveauX = 13;
        } else {
            nouveauX = 1;
        }

        // Si Pacman est en HAUT, le fantôme va en BAS (13)
        // Sinon, le fantôme va en HAUT (1)
        if (pacY < milieu) {
            nouveauY = 13;
        } else {
            nouveauY = 1;
        }

        // On applique la téléportation
        this.x = nouveauX * Constantes.TAILLE_BLOC;
        this.y = nouveauY * Constantes.TAILLE_BLOC;

        // On réinitialise le mouvement pour qu'il reparte proprement
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void bouger(Carte carte) {
        int futurX = x + dx * Constantes.VITESSE;
        int futurY = y + dy * Constantes.VITESSE;

        if (estDansMur(futurX, futurY, carte)) {
            x = (x / Constantes.TAILLE_BLOC) * Constantes.TAILLE_BLOC;
            y = (y / Constantes.TAILLE_BLOC) * Constantes.TAILLE_BLOC;
            choisirNouvelleDirection(carte);
        } else {
            x = futurX;
            y = futurY;
            if (x % Constantes.TAILLE_BLOC == 0 && y % Constantes.TAILLE_BLOC == 0) {
                if (Math.random() < 0.1) {
                    choisirNouvelleDirection(carte);
                }
            }
        }
    }

    private boolean estDansMur(int px, int py, Carte carte) {
        int caseX1 = px / Constantes.TAILLE_BLOC;
        int caseY1 = py / Constantes.TAILLE_BLOC;
        int caseX2 = (px + Constantes.TAILLE_BLOC - 1) / Constantes.TAILLE_BLOC;
        int caseY2 = (py + Constantes.TAILLE_BLOC - 1) / Constantes.TAILLE_BLOC;

        if (carte.getContenu(caseX1, caseY1) == 0 || carte.getContenu(caseX2, caseY2) == 0 ||
                carte.getContenu(caseX2, caseY1) == 0 || carte.getContenu(caseX1, caseY2) == 0) {
            return true;
        }
        return false;
    }

    private void choisirNouvelleDirection(Carte carte) {
        ArrayList<String> directionsPossibles = new ArrayList<>();
        int caseX = x / Constantes.TAILLE_BLOC;
        int caseY = y / Constantes.TAILLE_BLOC;

        if (carte.getContenu(caseX + 1, caseY) != 0) directionsPossibles.add("DROITE");
        if (carte.getContenu(caseX - 1, caseY) != 0) directionsPossibles.add("GAUCHE");
        if (carte.getContenu(caseX, caseY + 1) != 0) directionsPossibles.add("BAS");
        if (carte.getContenu(caseX, caseY - 1) != 0) directionsPossibles.add("HAUT");

        if (directionsPossibles.isEmpty()) return;

        int rand = (int) (Math.random() * directionsPossibles.size());
        String choix = directionsPossibles.get(rand);

        dx = 0; dy = 0;
        if (choix.equals("DROITE")) dx = 1;
        if (choix.equals("GAUCHE")) dx = -1;
        if (choix.equals("BAS")) dy = 1;
        if (choix.equals("HAUT")) dy = -1;
    }

    @Override
    public void dessiner(Graphics g) {
        g.drawImage(Constantes.IMAGE_POLICE, x, y, null);
    }
}