import java.awt.Image;

public class Player {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;

    // Atribut Kecepatan
    private int velocityX;
    private int velocityY;

    // Konstruktor
    public Player(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;

        // Inisialisasi kecepatan
        this.velocityX = 0;
        this.velocityY = 0;
    }


    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    // Getter untuk Kecepatan
    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    // --- Setter Methods ---

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}