import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Constantes {
    public static final int TAILLE_BLOC = 24;
    public static final int N_BLOCS = 15;
    public static final int TAILLE_ECRAN = N_BLOCS * TAILLE_BLOC;
    public static final int VITESSE = 4;

    // --- NOUVELLES CONSTANTES ---
    public static final int VIES_DEPART = 3;
    // Durée du pistolet en "tics" du jeu. Le jeu tourne à 25 images/seconde (40ms).
    // 25 * 10 = 250 tics pour 10 secondes.
    public static final int DUREE_SUPER = 250;

    public static Image IMAGE_MUR;
    public static Image IMAGE_TUPAC;
    public static Image IMAGE_POLICE;
    // Nouvelle image
    public static Image IMAGE_PISTOLET;

    static {
        try {
            System.out.println("Chargement des images...");
            // On charge les originaux
            BufferedImage murOrg = ImageIO.read(Constantes.class.getResource("/mur.png"));
            BufferedImage tupacOrg = ImageIO.read(Constantes.class.getResource("/tupac.png"));
            BufferedImage policeOrg = ImageIO.read(Constantes.class.getResource("/police.png"));
            // On charge le pistolet
            BufferedImage pistoletOrg = ImageIO.read(Constantes.class.getResource("/pistolet.png"));

            // On redimensionne tout
            IMAGE_MUR = murOrg.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);
            IMAGE_TUPAC = tupacOrg.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);
            IMAGE_POLICE = policeOrg.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);
            IMAGE_PISTOLET = pistoletOrg.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);

        } catch (Exception e) {
            System.err.println("ERREUR CRITIQUE : Problème d'images dans resources !");
            e.printStackTrace();
        }
    }
}