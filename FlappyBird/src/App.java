import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Logic logika = new Logic(); // instansiasi logic
        // instansiasi sehingga view bisa berkomunikasi dgn logic
        View tampilan = new View(logika);
        // begitu pula kebalikannya
        logika.setView(tampilan);

        tampilan.requestFocus();

        frame.add(tampilan);
        frame.pack();
        new MainMenu(frame);
    }
}