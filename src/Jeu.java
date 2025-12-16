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
    private int maxScore = 0; // Le score à atteindre pour gagner
    private boolean enJeu = true;
    private boolean gagne = false; // Nouvelle variable pour la victoire

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
        pacman = new Pacman();

        // Initialisation des fantômes
        fantomes = new ArrayList<>();
        fantomes.add(new Fantome(1 * Constantes.TAILLE_BLOC, 1 * Constantes.TAILLE_BLOC));
        fantomes.add(new Fantome(13 * Constantes.TAILLE_BLOC, 13 * Constantes.TAILLE_BLOC)); // Coin bas droite
        fantomes.add(new Fantome(13 * Constantes.TAILLE_BLOC, 1 * Constantes.TAILLE_BLOC)); // Coin haut droite

        // Calculer le score maximum possible (compter les '1' sur la carte)
        score = 0;
        maxScore = 0;
        for(int y=0; y<Constantes.N_BLOCS; y++) {
            for(int x=0; x<Constantes.N_BLOCS; x++) {
                if(carte.getContenu(x, y) == 1) {
                    maxScore += 10;
                }
            }
        }

        enJeu = true;
        gagne = false;

        if (timer != null) timer.stop();
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        carte.dessiner(g);
        pacman.dessiner(g);

        // Dessin des fantômes
        for (Fantome f : fantomes) {
            f.dessiner(g);
        }

        // Interface (HUD)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Score: " + score + " / " + maxScore, 10, Constantes.TAILLE_ECRAN + 15);

        // Ecran de Fin
        if (!enJeu) {
            g.setColor(Color.RED);
            String msg = "PERDU ! (Espace pour rejouer)";
            g.drawString(msg, 60, Constantes.TAILLE_ECRAN / 2);
        } else if (gagne) {
            // Si on a gagné, on arrête le jeu visuellement
            g.setColor(Color.GREEN);
            String msg = "VICTOIRE ! (Espace pour rejouer)";
            g.drawString(msg, 60, Constantes.TAILLE_ECRAN / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // On ne bouge que si le jeu est en cours et qu'on n'a pas encore gagné
        if (enJeu && !gagne) {
            pacman.bouger(carte);
            verifierMiamMiam();
            gererFantomes();
            verifierVictoire();
        }
        repaint();
    }

    private void verifierMiamMiam() {
        int caseX = pacman.getX() / Constantes.TAILLE_BLOC;
        int caseY = pacman.getY() / Constantes.TAILLE_BLOC;

        // Petite marge d'erreur pour manger le point plus facilement
        // On vérifie le centre de Pacman
        int centreX = pacman.getX() + 12;
        int centreY = pacman.getY() + 12;
        int caseCentreX = centreX / Constantes.TAILLE_BLOC;
        int caseCentreY = centreY / Constantes.TAILLE_BLOC;

        if (carte.getContenu(caseCentreX, caseCentreY) == 1) {
            carte.setVide(caseCentreX, caseCentreY);
            score += 10;
        }
    }

    private void verifierVictoire() {
        if (score >= maxScore) {
            gagne = true;
        }
    }

    private void gererFantomes() {
        Rectangle rectPacman = pacman.getRect();
        for (Fantome f : fantomes) {
            f.bouger(carte);
            if (rectPacman.intersects(f.getRect())) {
                enJeu = false;
            }
        }
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
                if (key == KeyEvent.VK_SPACE) initJeu();
            }
        }
    }
}