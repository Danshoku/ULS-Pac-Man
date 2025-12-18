import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Jeu extends JPanel implements ActionListener {

    private Timer timer;
    private int score = 0;
    private int maxScore = 0;
    private boolean enJeu = true;
    private boolean gagne = false;

    // --- NOUVELLES VARIABLES ---
    private int vies; // Nombre de vies restantes
    private long tempsDebutPartie; // Pour le chrono
    private String tempsAffiche = "00:00"; // Le texte du chrono

    // Gestion du Pistolet (Super Mode)
    private boolean estSuperTupac = false;
    private int timerSuperTupac = 0; // Compte à rebours du mode super

    private Carte carte;
    private Pacman pacman;
    private ArrayList<Fantome> fantomes;

    public Jeu() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new Clavier());
        initJeu();
    }

    private void initJeu() {
        carte = new Carte();
        carte.setContenu(7, 8, 3);

        pacman = new Pacman();
        fantomes = new ArrayList<>();
        fantomes.add(new Fantome(1 * Constantes.TAILLE_BLOC, 1 * Constantes.TAILLE_BLOC));
        fantomes.add(new Fantome(13 * Constantes.TAILLE_BLOC, 13 * Constantes.TAILLE_BLOC));
        fantomes.add(new Fantome(13 * Constantes.TAILLE_BLOC, 1 * Constantes.TAILLE_BLOC));

        // Initialisation des nouvelles variables
        score = 0;
        vies = Constantes.VIES_DEPART; // On commence avec 3 vies
        enJeu = true;
        gagne = false;
        estSuperTupac = false;
        timerSuperTupac = 0;

        // Calcul du score max (on compte les '1' et le '3')
        maxScore = 0;
        for(int y=0; y<Constantes.N_BLOCS; y++) {
            for(int x=0; x<Constantes.N_BLOCS; x++) {
                if(carte.getContenu(x, y) == 1 || carte.getContenu(x, y) == 3) {
                    maxScore += 10;
                }
            }
        }

        if (timer != null) timer.stop();
        timer = new Timer(40, this);
        timer.start();
        // On note l'heure de début pour le chrono
        tempsDebutPartie = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        carte.dessiner(g);
        pacman.dessiner(g);
        for (Fantome f : fantomes) f.dessiner(g);

        // --- NOUVELLE INTERFACE (HUD) ---
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        // Affichage du Score
        g.drawString("Score: " + score + "/" + maxScore, 10, Constantes.TAILLE_ECRAN + 20);

        // Affichage des Vies
        g.drawString("Vies: " + vies, 150, Constantes.TAILLE_ECRAN + 20);

        // Affichage du Chrono
        g.drawString("Temps: " + tempsAffiche, 250, Constantes.TAILLE_ECRAN + 20);

        // Indicateur visuel du Super Mode
        if (estSuperTupac) {
            g.setColor(Color.YELLOW);
            g.drawString("!!! SUPER TUPAC !!!", 120, 15);
        }

        // Écrans de fin
        if (!enJeu && vies <= 0) {
            g.setColor(Color.RED);
            String msg = "GAME OVER ! (Espace pour rejouer)";
            g.drawString(msg, 60, Constantes.TAILLE_ECRAN / 2);
        } else if (gagne) {
            g.setColor(Color.GREEN);
            String msg = "VICTOIRE ! (Espace pour rejouer)";
            g.drawString(msg, 60, Constantes.TAILLE_ECRAN / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (enJeu && !gagne) {
            pacman.bouger(carte);
            verifierMiamMiam();
            gererFantomes();
            verifierVictoire();

            // --- GESTION DU SUPER MODE ---
            if (estSuperTupac) {
                timerSuperTupac--; // On décrémente le temps restant
                if (timerSuperTupac <= 0) {
                    estSuperTupac = false; // Fini !
                }
            }

            // --- MISE A JOUR DU CHRONO ---
            mettreAJourChrono();
        }
        repaint();
    }

    // Nouvelle méthode pour calculer le temps "MM:SS"
    private void mettreAJourChrono() {
        long maintenant = System.currentTimeMillis();
        long tempsEcouleMillis = maintenant - tempsDebutPartie;
        int secondesTotal = (int) (tempsEcouleMillis / 1000);
        int minutes = secondesTotal / 60;
        int secondes = secondesTotal % 60;
        // String.format permet d'ajouter un '0' devant si le chiffre est < 10 (ex: "05")
        tempsAffiche = String.format("%02d:%02d", minutes, secondes);
    }

    private void verifierMiamMiam() {
        int centreX = pacman.getX() + 12;
        int centreY = pacman.getY() + 12;
        int caseX = centreX / Constantes.TAILLE_BLOC;
        int caseY = centreY / Constantes.TAILLE_BLOC;

        int contenu = carte.getContenu(caseX, caseY);

        if (contenu == 1) {
            // Manger un point normal
            carte.setVide(caseX, caseY);
            score += 10;
        }
        // --- MANGER LE PISTOLET (Code 3) ---
        else if (contenu == 3) {
            carte.setVide(caseX, caseY);
            score += 10;
            // Activer le super mode
            estSuperTupac = true;
            timerSuperTupac = Constantes.DUREE_SUPER; // 10 secondes
        }
    }

    // --- GESTION DES COLLISIONS MODIFIÉE ---
    private void gererFantomes() {
        Rectangle rectPacman = pacman.getRect();
        for (Fantome f : fantomes) {
            f.bouger(carte);
            if (rectPacman.intersects(f.getRect())) {

                if (estSuperTupac) {
                    // --- TUPAC EST ARMÉ ---
                    // Au lieu de resetPosition(), on l'envoie loin !
                    f.respawnLoin(pacman);

                    score += 50;
                } else {
                    // ... le reste du code de mort ne change pas ...
                    vies--;
                    if (vies > 0) {
                        pacman.resetPosition();
                        for(Fantome fantomeAReset : fantomes) {
                            fantomeAReset.resetPosition();
                        }
                    } else {
                        enJeu = false;
                    }
                }
            }
        }
    }

    private void verifierVictoire() {
        if (score >= maxScore) gagne = true;
    }

    private class Clavier extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (enJeu && !gagne) {
                if (key == KeyEvent.VK_LEFT) pacman.setDirection(-1, 0);
                if (key == KeyEvent.VK_RIGHT) pacman.setDirection(1, 0);
                if (key == KeyEvent.VK_UP) pacman.setDirection(0, -1);
                if (key == KeyEvent.VK_DOWN) pacman.setDirection(0, 1);
            } else {
                // Si Game Over ou Victoire, Espace relance
                if (key == KeyEvent.VK_SPACE) initJeu();
            }
        }
    }
}