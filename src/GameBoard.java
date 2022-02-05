import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 400;
    private final int BOARD_HEIGHT = 400;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private int fruitXPosition;
    private int fruitYPosition;

    private int points = 0;
    private boolean inGame = true;
    private boolean gameIsPaused = false;
    private Timer gameLoop;
    private Image fruit;
    private Snake snake;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        doDrawing(graphics);
    }

    public GameBoard() {
        initGameBoard();
    }

    private void initGameBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.white);
        setFocusable(true);

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        snake = new Snake(x, y, DOT_SIZE);
        initImageResources();
        initGame(snake);
    }

    void fruitResource() {
        ImageIcon fruitIcon = new ImageIcon(Strings.APPLE_ICON);
        fruit = fruitIcon.getImage();
    }

    private void initImageResources() {
        snake.getSnakeJointResource();
        fruitResource();
        snake.getSnakeHeadResource();
    }

    private void initGame(Snake snake) {
        snake.initSnake();
        placeNewApple();

        int gameLoopDelayInMs = 140;
        gameLoop = new Timer(gameLoopDelayInMs, this);
        gameLoop.start();
    }

    private void doDrawing(Graphics graphics) {
        if (inGame) {
            drawPoints(graphics);
            graphics.drawImage(fruit, fruitXPosition, fruitYPosition, this);
            snake.drawSnake(graphics);
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(graphics);
        }
    }

    private void gameOver(Graphics graphics) {
        Font font = new Font("Calibri", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(font);

        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(Strings.GAME_OVER_TEXT, (BOARD_WIDTH - fontMetrics.stringWidth(Strings.GAME_OVER_TEXT)) / 2, BOARD_HEIGHT / 2);

        String pointsResultText = (points <= 1) ?
                (points == 0) ?
                        Strings.NO_APPLES_TEXT :
                        Strings.GAME_OVER_POINTS_RESULT_1_APPLE :
                Strings.GAME_OVER_POINTS_RESULT;

        int textOffsetYPosition = 20;
        graphics.drawString(String.format(pointsResultText, points), (BOARD_WIDTH - fontMetrics.stringWidth(pointsResultText)) / 2, BOARD_HEIGHT / 2 + textOffsetYPosition);
    }

    private void drawPoints(Graphics graphics) {
        Font font = new Font("Calibri", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(font);

        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(String.format(Strings.POINTS_TEXT, points), (BOARD_WIDTH - fontMetrics.stringWidth(Strings.POINTS_TEXT)) / 2, (BOARD_HEIGHT / 2) + 10);
    }

    private void checkApple() {
        if ((x[0] == fruitXPosition) && (y[0] == fruitYPosition)) {
            snake.eatApple();
            points++;
            placeNewApple();
        }
    }

    private void collisionDetection() {
        for (int i = snake.getSnakeJointsOnStart(); i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
                break;
            }
        }

        if (y[0] >= BOARD_HEIGHT) {
            inGame = false;
        }
        if (x[0] >= BOARD_WIDTH) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (!inGame) {
            gameLoop.stop();
        }
    }

    private void placeNewApple() {
        int RAND_POSITION = 29;
        int random = (int) (Math.random() * RAND_POSITION);
        fruitXPosition = ((random * DOT_SIZE));
        random = (int) (Math.random() * RAND_POSITION);
        fruitYPosition = ((random * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (inGame) {
            checkApple();
            collisionDetection();
            snake.moveSnake();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();

            if ((keyCode == KeyEvent.VK_LEFT) && (!snake.isRightDirection())) {
                snake.setDirection(Direction.LEFT);
            }
            if ((keyCode == KeyEvent.VK_RIGHT) && (!snake.isLeftDirection())) {
                snake.setDirection(Direction.RIGHT);
            }
            if ((keyCode == KeyEvent.VK_UP) && (!snake.isDownDirection())) {
                snake.setDirection(Direction.UP);
            }
            if ((keyCode == KeyEvent.VK_DOWN) && (!snake.isUpDirection())) {
                snake.setDirection(Direction.DOWN);
            }
            if (keyCode == KeyEvent.VK_P) {
                if (!gameIsPaused) {
                    gameIsPaused = true;
                    gameLoop.stop();
                } else {
                    gameIsPaused = false;
                    gameLoop.start();
                }
            }
        }
    }
}
