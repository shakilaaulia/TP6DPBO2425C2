import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {

    // Ukuran tombol
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;

    JButton startButton;
    JButton exitButton;
    JFrame gameFrame;

    // Atribut untuk gambar background
    private Image backgroundImage;

    // Inner Class untuk Background
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Gambar background mengisi seluruh panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Opsi fallback jika gambar gagal dimuat
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public MainMenu(JFrame gameFrame) {
        this.gameFrame = gameFrame;
        setTitle("Flappy Bird Menu");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Muat Gambar Background
        try {
            backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        } catch (Exception e) {
            System.err.println("Gagal memuat gambar background menu: " + e.getMessage());
        }

        // Buat Panel Khusus Background
        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(null); // Menggunakan Layout manager null untuk penempatan absolut

        // Setup Tombol menggunakan Gambar yang Diskalakan
        Icon startIcon = null;
        Icon exitIcon = null;

        try {
            // Muat Gambar tomol
            Image startImage = new ImageIcon(getClass().getResource("assets/play.png")).getImage();
            Image exitImage = new ImageIcon(getClass().getResource("assets/exit.png")).getImage();

            // Skalakan Gambar ke ukuran yang diinginkan (150x50)
            Image scaledStartImage = startImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
            Image scaledExitImage = exitImage.getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);

            // Buat ImageIcon dari gambar yang sudah diskalakan
            startIcon = new ImageIcon(scaledStartImage);
            exitIcon = new ImageIcon(scaledExitImage);

        } catch (Exception e) {
            System.err.println("Gagal memuat atau menskalakan ikon tombol: " + e.getMessage());
        }

        // Setup Tombol Start
        if (startIcon != null) {
            startButton = new JButton(startIcon);
        } else {
            // tombol teks jika ikon gagal dimuat
            startButton = new JButton("Start Game");
        }
        // Penempatan tombol di bawah frame
        setupImageButton(startButton, 105, 400, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Setup Tombol Exit
        if (exitIcon != null) {
            exitButton = new JButton(exitIcon);
        } else {
            // tombol teks jika ikon gagal dimuat
            exitButton = new JButton("Exit Program");
        }
        setupImageButton(exitButton, 105, 450, BUTTON_WIDTH, BUTTON_HEIGHT); // Posisi Y sedikit di bawah start

        // Tambahkan Tombol ke Panel
        mainPanel.add(startButton);
        mainPanel.add(exitButton);

        // Tambahkan Panel ke Frame
        setContentPane(mainPanel);
        setVisible(true);
    }

    // Method Helper untuk Setup Tombol Gambar
    private void setupImageButton(JButton button, int x, int y, int w, int h) {
        // Membuat tombol terlihat seperti gambar (transparan)
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setBounds(x, y, w, h); // Mengatur posisi dan ukuran
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            // Tampilkan frame game dan sembunyikan menu
            gameFrame.setVisible(true);
            this.dispose(); // Menutup frame menu
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}