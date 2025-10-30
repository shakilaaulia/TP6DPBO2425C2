import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    View view;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;

    int gravity = 1; // Gravitasi aktif
    final int playerSpeed = 5;

    public int pipeVelocityX = -2;

    private boolean gameOver = false;
    private int score = 0;
    private JLabel scoreLabel;

    // status untuk menunda start
    private boolean gameStarted = false;

    public Logic() {
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });
        // pipa supaya delay dulu
        pipesCooldown.stop();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

 // status untuk mengecek game mulai atau belum
    public boolean isGameStarted() {
        return gameStarted;
    }

    // status untuk mengecek game over atau tidak
    public boolean isGameOver() {
        return gameOver;
    }

    // menampilkan score
    public void setScoreLabel(JLabel label) {
        this.scoreLabel = label;
        updateScoreLabel();
    }

    // untuk update score namah  +1
    private void updateScoreLabel() {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
    }

    // untuk reset game ke awal
    public void resetGame() {
        gameOver = false;
        score = 0;
        updateScoreLabel();

        gameStarted = false;

        // Reset Player ke posisi awal
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // Hapus semua pipa
        pipes.clear();

        // Mulai kembali gameLoop
        gameLoop.start();
        pipesCooldown.stop();
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes(){
        return pipes;
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight,
                lowerPipeImage);
        pipes.add(lowerPipe);
    }

    // untuk mengatur pergerakan
    public void move() {
        if (gameOver) {
            gameLoop.stop();
            pipesCooldown.stop();
            return;
        }

        // Jika game belum dimulai, player tetap diam.
        if (gameStarted) {
            // Pergerakan Player
            player.setVelocityY(player.getVelocityY() + gravity);
            player.setPosY(player.getPosY() + player.getVelocityY());
            player.setPosY(Math.max(player.getPosY(), 0));

            // Hitbox Player
            Rectangle playerHitBox = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

            // Logika Pergerakan, Tabrakan, dan Skor Pipa
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);

                // Tabrakan
                if (playerHitBox.intersects(pipe.getBounds())) {
                    gameOver = true;
                    break;
                }

                // Skor Hanya cek Pipa Bawah
                if (i % 2 != 0 && !pipe.isPassed()) {
                    if (pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                        score++;
                        pipe.setPassed(true);
                        pipes.get(i - 1).setPassed(true); // Tandai Pipa Atas juga
                        updateScoreLabel();
                    }
                }

                // Pergerakan Pipa
                pipe.setPosX(pipe.getPosX() + pipeVelocityX);
            }

            // Penghapusan Pipa
            for (int j = pipes.size() - 1; j >= 0; j--) {
                if (j % 2 == 0) { // Cek Pipa Atas (indeks genap)
                    Pipe pipe = pipes.get(j);

                    if (pipe.getPosX() + pipe.getWidth() < 0) {
                        pipes.remove(j + 1); // Hapus Pipa Bawah
                        pipes.remove(j);     // Hapus Pipa Atas
                    }
                }
            }

            // Deteksi Jatuh ke Batas Bawah (Game Over)
            if (player.getPosY() + player.getHeight() > frameHeight) {
                gameOver = true;
                player.setPosY(frameHeight - player.getHeight());
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        if (view != null){
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        // Tombol space untuk memicu Lompatan
        if (key == KeyEvent.VK_SPACE ||
                key == KeyEvent.VK_UP) { // Tambahkan juga tombol panah atas

            if (!gameOver) {
                // Memulai Game di Lompatan Pertama
                if (!gameStarted) {
                    gameStarted = true;
                    pipesCooldown.start(); // Mulai pipa muncul dan bergerak
                }

                player.setVelocityY(-10); // Memberi kecepatan vertikal negatif (melompat ke atas)
            }
        }

        // Restart
        if (key == KeyEvent.VK_R) {
            if (gameOver) {
                resetGame();
            }
        }
    }

    public void keyReleased(KeyEvent e){
        // Tidak ada aksi saat tombol dilepas di Flappy Bird ini
    }
}