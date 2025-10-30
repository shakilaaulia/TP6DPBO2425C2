import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    int width = 360;
    int height = 640;

    private Logic logic; // tambahkan atribut logic
    private JLabel scoreLabel; // Atribut untuk JLabel skor
    private Image backgroundImage; // Gambar background

    // constructor
    public View(Logic logic) {
        this.logic = logic; // memasukkan instance ke atribut
        setPreferredSize(new Dimension(width, height));
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();

        // Setup Score Label
        this.scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.white);

        // Gunakan BorderLayout untuk menempatkan label di atas
        setLayout(new BorderLayout());
        add(scoreLabel, BorderLayout.NORTH);
        logic.setScoreLabel(scoreLabel); // Kirim reference label ke Logic

        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (backgroundImage != null) {
            // Gambar background mengisi seluruh ukuran panel
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        }

        // Logika gambar Player
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null); // imageObserver: null
        }

        // Logika gambar Pipa
        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null); // imageObserver: null
            }
        }

        if (!logic.isGameStarted() && !logic.isGameOver()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            String text = "Tekan SPACE untuk Melompat";

            // Hitung posisi tengah
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int x = (width - textWidth) / 2;
            int y = (height / 2) + 60;

            g.drawString(text, x, y);
        }

        // Menampilkan pesan game over
        if (logic.isGameOver()) {
            g.setColor(new Color(255, 0, 0, 200)); // set warna merah
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String text = "GAME OVER";
            int textWidth = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (width - textWidth) / 2, height / 2 - 30);

            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String restartText = "Tekan 'R' untuk Restart";
            int restartWidth = g.getFontMetrics().stringWidth(restartText);
            g.drawString(restartText, (width - restartWidth) / 2, height / 2 + 30);
        }
    }
}