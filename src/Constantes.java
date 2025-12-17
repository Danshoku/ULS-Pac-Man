import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
// Import nécessaire pour accéder aux options de redimensionnement
import java.awt.image.BufferedImage;

public class Constantes {
    public static final int TAILLE_BLOC = 24;
    public static final int N_BLOCS = 15;
    public static final int TAILLE_ECRAN = N_BLOCS * TAILLE_BLOC;
    public static final int VITESSE = 4;

    public static Image IMAGE_MUR;
    public static Image IMAGE_TUPAC;
    public static Image IMAGE_POLICE;

    static {
        try {
            System.out.println("Chargement et redimensionnement des images...");

            // 1. On charge l'image originale (peut être grande)
            BufferedImage murOriginal = ImageIO.read(Constantes.class.getResource("/mur.png"));
            // 2. On crée une version redimensionnée à TAILLE_BLOC (24x24)
            // SCALE_SMOOTH permet d'avoir un résultat propre même en réduisant beaucoup
            IMAGE_MUR = murOriginal.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);

            // On fait pareil pour Tupac
            BufferedImage tupacOriginal = ImageIO.read(Constantes.class.getResource("/tupac.png"));
            IMAGE_TUPAC = tupacOriginal.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);

            // Et pour la police
            BufferedImage policeOriginal = ImageIO.read(Constantes.class.getResource("/police.png"));
            IMAGE_POLICE = policeOriginal.getScaledInstance(TAILLE_BLOC, TAILLE_BLOC, Image.SCALE_SMOOTH);

            System.out.println("Succès ! Images prêtes.");

        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            // Le NullPointerException attrape le cas où l'image n'est pas trouvée
            System.err.println("ERREUR : Impossible de trouver ou charger une image dans resources !");
            e.printStackTrace();
        }
    }
}