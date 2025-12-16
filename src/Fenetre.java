import javax.swing.JFrame;

public class Fenetre extends JFrame {
    public Fenetre() {
        add(new Jeu());
        setTitle("Pac-Man Java Objets");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(380, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}