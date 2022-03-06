import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame() {
        GamePanel gPanel = new GamePanel();
        this.add(gPanel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
