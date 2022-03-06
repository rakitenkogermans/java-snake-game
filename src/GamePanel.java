import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 90;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 4;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }
    public void draw(Graphics g) {

        if (running) {
            // drawing grid
//            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }

            g.setColor(Color.ORANGE);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.CYAN);
                }
                g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
            }

            String str = "Score: " + applesEaten;
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(str, (SCREEN_WIDTH - metrics.stringWidth(str))/2, g.getFont().getSize());
        } else {
            gameOver(g);
        }

    }
    public void move() {
        for (int i = this.bodyParts; i > 0; i--) {
            x[i] = this.x[i-1];
            y[i] = this.y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] = this.y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = this.y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = this.x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = this.x[0] + UNIT_SIZE;
                break;
        }
    }
    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions() {
        // if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // touches left border
        if (x[0] < 0) {
            running = false;
        }
        // touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // touches top border
        if (y[0] < 0) {
            running = false;
        }
        // touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }

    }
    public void gameOver(Graphics g) {
        String str = "Game Over";
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 80));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(str, (SCREEN_WIDTH - metrics.stringWidth(str))/2, SCREEN_HEIGHT/2);

        // score
        str = "Score: " + applesEaten;
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString(str, (SCREEN_WIDTH - metrics2.stringWidth(str))/2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }
}
