import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList; // Nécessaire pour faire une liste de choix

public class Fantome extends Unite {

    public Fantome(int startX, int startY) {
        super(startX, startY);
        this.dy = 1;
    }

    @Override
    public void bouger(Carte carte) {
        // 1. Calcul de la position future théorique
        int futurX = x + dx * Constantes.VITESSE;
        int futurY = y + dy * Constantes.VITESSE;

        // 2. Vérification des collisions AVANT de bouger
        // On regarde les 4 coins du futur carré du fantôme pour voir s'ils touchent un mur
        if (estDansMur(futurX, futurY, carte)) {
            // AIE ! Mur droit devant ! On s'arrête et on cherche une nouvelle voie.
            // On réaligne le fantôme parfaitement sur la grille pour éviter les bugs
            x = (x / Constantes.TAILLE_BLOC) * Constantes.TAILLE_BLOC;
            y = (y / Constantes.TAILLE_BLOC) * Constantes.TAILLE_BLOC;

            choisirNouvelleDirection(carte);
        } else {
            // La voie est libre, on avance
            x = futurX;
            y = futurY;

            // 3. À chaque intersection, petite chance de changer de direction pour être moins prévisible
            if (x % Constantes.TAILLE_BLOC == 0 && y % Constantes.TAILLE_BLOC == 0) {
                if (Math.random() < 0.1) { // 10% de chance de tourner quand c'est possible
                    choisirNouvelleDirection(carte);
                }
            }
        }
    }

    // Fonction utilitaire pour vérifier si une position touche un mur
    private boolean estDansMur(int px, int py, Carte carte) {
        // On vérifie le coin haut-gauche et le coin bas-droite du carré
        int caseX1 = px / Constantes.TAILLE_BLOC;
        int caseY1 = py / Constantes.TAILLE_BLOC;
        int caseX2 = (px + Constantes.TAILLE_BLOC - 1) / Constantes.TAILLE_BLOC;
        int caseY2 = (py + Constantes.TAILLE_BLOC - 1) / Constantes.TAILLE_BLOC;

        // Si l'un des coins touche un mur (0), c'est une collision
        if (carte.getContenu(caseX1, caseY1) == 0 || carte.getContenu(caseX2, caseY2) == 0 ||
                carte.getContenu(caseX2, caseY1) == 0 || carte.getContenu(caseX1, caseY2) == 0) {
            return true;
        }
        return false;
    }

    private void choisirNouvelleDirection(Carte carte) {
        // On liste toutes les directions possibles (Haut, Bas, Gauche, Droite)
        // qui ne sont PAS des murs
        ArrayList<String> directionsPossibles = new ArrayList<>();

        int caseX = x / Constantes.TAILLE_BLOC;
        int caseY = y / Constantes.TAILLE_BLOC;

        if (carte.getContenu(caseX + 1, caseY) != 0) directionsPossibles.add("DROITE");
        if (carte.getContenu(caseX - 1, caseY) != 0) directionsPossibles.add("GAUCHE");
        if (carte.getContenu(caseX, caseY + 1) != 0) directionsPossibles.add("BAS");
        if (carte.getContenu(caseX, caseY - 1) != 0) directionsPossibles.add("HAUT");

        // Si on est coincé (pas de direction), on ne fait rien
        if (directionsPossibles.isEmpty()) return;

        // On en choisit une au hasard
        int rand = (int) (Math.random() * directionsPossibles.size());
        String choix = directionsPossibles.get(rand);

        // On applique
        dx = 0; dy = 0;
        if (choix.equals("DROITE")) dx = 1;
        if (choix.equals("GAUCHE")) dx = -1;
        if (choix.equals("BAS")) dy = 1;
        if (choix.equals("HAUT")) dy = -1;
    }

    @Override
    public void dessiner(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, Constantes.TAILLE_BLOC, Constantes.TAILLE_BLOC);
    }
}